package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Users;
import com.example.demo.mapper.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	 @Autowired
	    private UsersRepository usersRepository;
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Users user = usersRepository.findByUsername(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
//        }
//
//        return User.withUsername(user.getUsername())
//                .password("{noop}" + user.getPassword())
//                .roles("USER")
//                .build();
//    }
	 
	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     Users user = usersRepository.findByUsername(username);

	     if (user == null) {
	         throw new UsernameNotFoundException("ユーザーが見つかりません: " + username);
	     }

	     return new CustomUserDetails(user); // ← CustomUserDetailsを返すように変更
	 }

}
