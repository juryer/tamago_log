package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.Users;

public class CustomUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private Users user;
	public CustomUserDetails(Users user) {
        this.user = user;
    }

    public int getUserId() {
        return user.getId(); 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

	@Override
	public String getPassword() {
		// TODO 自動生成されたメソッド・スタブ
		return "{noop}" +user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO 自動生成されたメソッド・スタブ
		return user.getUsername();
	}
}
