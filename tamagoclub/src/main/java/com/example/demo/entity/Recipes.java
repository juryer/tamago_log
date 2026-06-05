package com.example.demo.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipes {
	private Integer id;
	private Integer userId;
	private String title;
	private Integer eggs;
	private String description;
	private String imageUrl;
	private Integer accessCount;
	private Date cereatedAt;
	private Date updateAt;
	private String category;
	private String ingredients;
}
