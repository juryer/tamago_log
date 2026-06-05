package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Users;

@Mapper
public interface UsersRepository {
	
	void save(Users users) ;
	void insert(Users users);
	Users findByUsername(String username);
	Users findById(@Param("id") int id);
}
