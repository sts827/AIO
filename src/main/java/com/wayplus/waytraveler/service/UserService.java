package com.wayplus.waytraveler.service;

import com.wayplus.waytraveler.mapper.UserMapper;
import com.wayplus.waytraveler.model.LoginAttemptLog;
import com.wayplus.waytraveler.model.LoginUser;
import com.wayplus.waytraveler.model.UserWebLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService  implements UserDetailsService {

    private final UserMapper userMapper;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserMapper userMapper){this.userMapper=userMapper;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Call loadUserByUserName. Username : " + username);
        LoginUser user = userMapper.selectUserByUserid(username);

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority( "ROLE_"+user.getUser_role() ));
        user.setAuthorities(roles);

        if(user == null) throw new UsernameNotFoundException(username);

        logger.debug("User Find. User Has Role : " + user.getAuthorities().toString());
        return user;
    }

    public void writeUserLoginLog(HashMap<String, String> parameterMap) {
        userMapper.insertUserLoginLog(parameterMap);
    }

    public void updateUserLastLoginDate(LoginUser user) {
        userMapper.updateUserLastLoginDate(user);
    }

    public void updateUserWebLog(HashMap<String, String> param) {
        userMapper.updateUserEstimateTokenId(param);
        userMapper.updateUserWebLog(param);
    }

    public void writeUserLoginAttemptLog(LoginAttemptLog attemptLog) {
        userMapper.insertUserLoginAttemptLog(attemptLog);
    }

    public void updateUserNewTokenId(LoginUser user) {
        userMapper.updateUserNewTokenId(user);
    }

    public void writeUserWebLog(UserWebLog webLog) {
        userMapper.insertUserWebLog(webLog);
    }

}
