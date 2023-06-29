package com.wayplus.waytraveler.config.handler;

import com.wayplus.waytraveler.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler{
    private final Logger logger = LoggerFactory.getLogger(SecurityAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, javax.servlet.ServletException {
        LoginUser user = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user != null){
            logger.debug("Access Denied. Path : " + request.getRequestURI() + ", User : " + user.getUser_email() + ", Authorities : " + user.getAuthorities());
        }else{
            logger.debug("Access Denied. Path : " + request.getRequestURI());
        }
        request.getRequestDispatcher("/error/denied").forward(request, response);
    }
}
