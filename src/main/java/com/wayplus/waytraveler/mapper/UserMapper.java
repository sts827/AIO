package com.wayplus.waytraveler.mapper;

import com.wayplus.waytraveler.model.LoginAttemptLog;
import com.wayplus.waytraveler.model.LoginUser;
import com.wayplus.waytraveler.model.UserWebLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Mapper
@Repository
public interface UserMapper {

    LoginUser selectUserByUserid(String username);

    void insertUserLoginLog(HashMap<String, String> parameterMap);

    String selectUserLastLoginTime(HashMap<String, String> userEmail);

    void insertUserWebLog(UserWebLog webLog);

    void insertUserLoginAttemptLog(LoginAttemptLog attemptLog);

    void updateUserLastLoginDate(LoginUser user);

    void updateUserEstimateTokenId(HashMap<String, String> user);

    void updateUserWebLog(HashMap<String, String> param);

    void updateUserNewTokenId(LoginUser user);


}
