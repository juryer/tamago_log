package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Favorites;
import com.example.demo.entity.Recipes;
import com.example.demo.service.CustomUserDetails;
import com.example.demo.service.FavoritesServiceImpl;
import com.example.demo.service.RecipesServiceImpl;

@Controller
@RequestMapping("/main/favorites")
public class FavoritesController {

    @Autowired
    private FavoritesServiceImpl favoritesService;

    @Autowired
    private RecipesServiceImpl recipesService;

    // お気に入り一覧ページ
    @GetMapping("")
    public String favoriteList(Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

    	if(userDetails == null) {
    		return "redirect:/main";
    	}

    	
        List<Favorites> favoriteList = favoritesService.findByUserId(userDetails.getUserId());

        // お気に入りのrecipe_idからレシピ情報を取得
        List<Recipes> recipeList = favoriteList.stream()
                .map(f -> recipesService.findByIdRecipes(f.getRecipeId()))
                .toList();

        model.addAttribute("recipeList", recipeList);
        model.addAttribute("favoriteCount", favoriteList.size());
        return "main/favorites";
    }

    // お気に入り追加/解除トグル
    @PostMapping("/toggle/{recipeId}")
    public String toggleFavorite(@PathVariable int recipeId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        int userId = userDetails.getUserId();

        if (favoritesService.isFavorite(userId, recipeId)) {
            favoritesService.removeFavorite(userId, recipeId);
        } else {
            favoritesService.addFavorite(userId, recipeId);
        }

        return "redirect:/main/list";
    }
}
