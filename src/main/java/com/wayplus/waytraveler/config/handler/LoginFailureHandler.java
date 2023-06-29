package com.wayplus.waytraveler.config.handler;

import com.wayplus.waytraveler.model.LoginAttemptLog;
import com.wayplus.waytraveler.service.UserService;
import com.wayplus.waytraveler.util.IPAddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);

    @Autowired
    public LoginFailureHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.debug("Login Failed. " + exception.getMessage());

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        LoginAttemptLog attemptLog = new LoginAttemptLog();
        attemptLog.setUser_email(request.getParameter("user-email"));
        attemptLog.setAttempt_ip(IPAddrUtil.getClientIpAddr(request));
        attemptLog.setAttempt_agent(request.getHeader("User-Agent"));
        if(savedRequest != null) attemptLog.setAttempt_referer(savedRequest.getRedirectUrl());
        else attemptLog.setAttempt_referer(request.getHeader("Referer"));

        String errorCode = "n";
        if(exception instanceof AuthenticationServiceException){
            errorCode = "NON";
            attemptLog.setError_message("등록된 아이디가 아닙니다.");
        } else if (exception instanceof BadCredentialsException) {
            errorCode = "PAS";
            attemptLog.setError_message("비밀번호가 일치하지 않습니다.");
        } else if (exception instanceof LockedException) {
            errorCode = "LOC";
            attemptLog.setError_message("잠긴 사용자 아이디입니다.");
        } else if (exception instanceof DisabledException) {
            errorCode = "DIS";
            attemptLog.setError_message("휴면상태의 아이디입니다.");
        } else if (exception instanceof AccountExpiredException) {
            errorCode = "EXD";
            attemptLog.setError_message("만료된 아이디입니다.");
        } else if (exception instanceof CredentialsExpiredException) {
            errorCode = "EXP";
            attemptLog.setError_message("비밀번호가 만료됐습니다.");
        }
        attemptLog.setError_code(errorCode);

        try{
            HttpSession session = request.getSession();
            if(session.getAttribute("login") != null) {
                session.removeAttribute("login");
                session.removeAttribute("snsYn");
            }
            userService.writeUserLoginAttemptLog(attemptLog);
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath()+"/user/login?error=y&code="+errorCode);
    }
}
