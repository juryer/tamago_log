package com.example.demo.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RecipesForm {
    private String title;
    
    @NotNull
    @Min(value = 1, message = "卵は1個以上入力してください")
    private Integer eggs;
    private Integer id; 
    private String description;
    private String imageUrl;
    private Integer userId;
    private String category;
    private String ingredients;
}
