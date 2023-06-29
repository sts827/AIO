package com.wayplus.waytraveler.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class LoginUser implements UserDetails {
    private int rownum;
    private String user_email;
    private String user_pass;
    private String user_name;
    private String user_tel;
    private String user_mobile;
    private String user_role;
    private String user_zipcode;
    private String user_addr;
    private String user_addr_detail;
    private String user_class_name;
    private String user_class_code;
    private String user_group_code;
    private String user_token_id;
    private String user_join_date;
    private String user_modify_date;
    private String last_login_date;
    private String last_password_date;
    private String naver_email;
    private String naver_token;
    private String naver_join_date;
    private String kakao_email;
    private String kakao_token;
    private String kakao_join_date;
    private String secondary_email;
    private int privacy_retention_days;
    private String mailing_yn;

    private List<Role> roles;
    private List<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user_pass;
    }

    @Override
    public String getUsername() {
        return user_email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
