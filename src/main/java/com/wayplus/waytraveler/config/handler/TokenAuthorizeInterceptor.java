package com.wayplus.waytraveler.config.handler;

import com.wayplus.waytraveler.model.LoginUser;
import com.wayplus.waytraveler.model.UserWebLog;
import com.wayplus.waytraveler.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 사용자 정보 기록을 위해 쿠키 기반으로 접속자 고유 아이디를 확인한다.
 */
@Component
public class TokenAuthorizeInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(TokenAuthorizeInterceptor.class);

    private final UserService userService;

    private final int TrackerMaxAge = 60*60*24*181;

    @Value("${cookie-set.domain}")
    private String cookieDomain;
    @Value("${cookie-set.prefix}")
    private String cookieName;
    @Value("${cookie-set.tracking}")
    private boolean cookieTracking;

    @Autowired
    public TokenAuthorizeInterceptor(UserService userService){this.userService=userService;}

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler)
            throws Exception {
        logger.debug("================== Start Token Authorize Interceptor ==================");

        if(cookieTracking){
            //웹사이트 트래킹 로그 작성
            logger.debug(writeWebTrackingLog(request, response));
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        logger.debug("================== End Token Authorize Interceptor ==================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }

    private Cookie createTrackerCookie(String value){
        Cookie cookie = new Cookie(cookieName+"tracker.id", value);
        cookie.setDomain(cookieDomain);
        cookie.setPath("/");
        cookie.setMaxAge(TrackerMaxAge);
        return cookie;
    }

    private String writeWebTrackingLog(HttpServletRequest request, HttpServletResponse response){
        String message = "";
        try {
            HttpSession session = request.getSession();
            LoginUser user = null;
            if (session.getAttribute("login") != null) {
                user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            }

            //트래커 확인 및 생성
            Cookie trackerCookie = null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName + "tracker.id")) {
                    trackerCookie = cookie;
                    logger.debug("find cookie... " + cookie.getDomain() + " / " + cookie.getName() + " / " + cookie.getValue() + " / " + cookie.getMaxAge());
                }
            }

            if (trackerCookie == null) {
                //트래커 쿠키가 없을 경우 생성
                if (user != null) {
                    if (user.getUser_token_id() == null) {
                        user.setUser_token_id(String.valueOf(UUID.randomUUID()));
                        userService.updateUserNewTokenId(user);
                    }
                    trackerCookie = createTrackerCookie(user.getUser_token_id());
                } else {
                    trackerCookie = createTrackerCookie(String.valueOf(UUID.randomUUID()));
                }
                logger.debug("Create Tracker ID... " + trackerCookie.getName() + ":" + trackerCookie.getValue());
            } else {
                logger.debug("Exist Tracker ID... " + trackerCookie.getName() + ":" + trackerCookie.getValue());
                //cookie 업데이트 처리
                if (user != null) {
                    if (user.getUser_token_id() != null && !user.getUser_token_id().equals(trackerCookie.getValue())) {
                        trackerCookie.setValue(user.getUser_token_id());
                    } else {
                        user.setUser_token_id(trackerCookie.getValue());
                        userService.updateUserNewTokenId(user);
                    }
                }
                trackerCookie.setMaxAge(TrackerMaxAge);
            }
            response.addCookie(trackerCookie);

            //타 페이지 호출을 위한 세션 트래킹 설정용
            if(session.getAttribute("tracker-id") == null){
                logger.debug("Create Session Tracker ID... ");
                session.setAttribute("tracker-id", trackerCookie.getValue());
            }

            //웹 로그 작성
            UserWebLog webLog = new UserWebLog();
            webLog.setSession_id(session.getId());
            if (user != null) {
                webLog.setUser_token(user.getUser_token_id());
                webLog.setUser_email(user.getUser_email());
                webLog.setTracking("Y");
            } else {
                webLog.setUser_token(trackerCookie.getValue());
                webLog.setTracking("N");
            }
            if(request.getHeader("Referer").length() > 200){
                webLog.setReferer(request.getHeader("Referer").substring(0, 200));
            }
            else{
                webLog.setReferer(request.getHeader("Referer"));
            }
            webLog.setRequest_host(request.getRemoteHost());
            webLog.setRequest_uri(request.getRequestURI());
            userService.writeUserWebLog(webLog);

            message = "Tracking Log Write... Token ID : " + trackerCookie.getValue();
        }
        catch (Exception e){
            message = e.getMessage();
            logger.error(e.getMessage());
        }

        return message;
    }


}
