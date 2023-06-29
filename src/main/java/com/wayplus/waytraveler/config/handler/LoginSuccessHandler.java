package com.wayplus.waytraveler.config.handler;

import com.wayplus.waytraveler.model.LoginUser;
import com.wayplus.waytraveler.service.UserService;
import com.wayplus.waytraveler.util.IPAddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Value("${cookie-set.domain}")
    private String cookieDomain;
    @Value("${cookie-set.prefix}")
    private String cookieName;

    @Autowired
    public LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.debug("Login Success. User id : " + user.getUser_email() + ", Session Id : " + session.getId());
        //세션 및 DB에 로그인 정보 저장
        user.setUser_pass("");
        session.setAttribute("login", user);
        if(session.getAttribute("guest") != null) session.removeAttribute("guest");

        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        HashMap<String, String> parameterMap = new HashMap<>();
        parameterMap.put("UserEmail", user.getUser_email());
        parameterMap.put("SessionId", session.getId());
        parameterMap.put("LoginIp", IPAddrUtil.getClientIpAddr(request));
        parameterMap.put("UserAgent", request.getHeader("User-Agent"));
        if(savedRequest != null) parameterMap.put("Referer", savedRequest.getRedirectUrl());
        else parameterMap.put("Referer", request.getHeader("Referer"));

        logger.debug("Write Login Log... Param: " + parameterMap);

        //로그인 로그 저장
        userService.writeUserLoginLog(parameterMap);
        //마지막 로그인 일자 업데이트
        userService.updateUserLastLoginDate(user);
        //추적용 쿠키 아이디 업데이트
        for(Cookie cookie : request.getCookies()){
            if(cookie.getName().equals(cookieName+"tracker.id")){
                if(!cookie.getValue().equals(user.getUser_token_id())){
                    HashMap<String, String> param = new HashMap<>();
                    param.put("before", cookie.getValue());
                    param.put("after", user.getUser_token_id());
                    userService.updateUserWebLog(param);
                }
            }
        }

        //아이디 저장 체크 확인
        if(request.getParameter("remember") != null
                && request.getParameter("remember").equals("Y")){
            Cookie loginCookie = new Cookie(cookieName+"login.id", user.getUser_email());
            loginCookie.setMaxAge(60*60*24*90);
            loginCookie.setDomain(cookieDomain);
            loginCookie.setPath("/");
            response.addCookie(loginCookie);
        }else{
            Cookie loginCookie = new Cookie(cookieName+"login.id", "");
            loginCookie.setMaxAge(-1);
            loginCookie.setDomain(cookieDomain);
            loginCookie.setPath("/");
            response.addCookie(loginCookie);
        }

        //로그인 전 요청페이지로 리다이렉션
        if(savedRequest != null && !savedRequest.getRedirectUrl().contains("/user")){
            response.sendRedirect(savedRequest.getRedirectUrl());
        }else{
            response.sendRedirect("/");
        }

    }
}
