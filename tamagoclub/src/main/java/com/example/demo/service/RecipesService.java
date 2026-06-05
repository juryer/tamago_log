package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Recipes;


public interface RecipesService {
    List<Recipes> findAllRecipes();
    Recipes findByIdRecipes(Integer id);
    //void insertToRecipes(RecipesForm recipesForm);
    void updateToRecipes(Recipes recipes);
    void deleteRecipes(Integer id);
    void incrementAccessCount(Integer id);
    List<Recipes> searchByKeywordAndCategory(String keyword, String category);
    //List<Recipes> findByCategoryOrderByAccessCountDesc(String category);
    }   
