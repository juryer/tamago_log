package com.example.demo.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Recipes;
import com.example.demo.form.RecipesForm;
import com.example.demo.mapper.RecipesMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipesServiceImpl implements RecipesService {

    private final RecipesMapper recipesMapper;

    @Override
    public List<Recipes> findAllRecipes() {
        return recipesMapper.selectAll();
    }

    @Override
    public Recipes findByIdRecipes(Integer id) {
        return recipesMapper.selectById(id);
    }

    public void insertToRecipes(RecipesForm recipesForm,
                                 MultipartFile imageFile,
                                 CustomUserDetails userDetails) throws IOException {
    	System.out.println("ingredients = " + recipesForm.getIngredients());
        Recipes recipes = new Recipes();
        recipes.setTitle(recipesForm.getTitle());
        recipes.setEggs(recipesForm.getEggs());
        recipes.setDescription(recipesForm.getDescription());
        recipes.setCategory(recipesForm.getCategory());
        recipes.setUserId(userDetails.getUserId());
        recipes.setIngredients(recipesForm.getIngredients()); 

        // 画像
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path savePath = Paths.get("src/main/resources/static/images/" + fileName);
            Files.copy(imageFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
            recipes.setImageUrl("/images/" + fileName);
        }

        recipesMapper.insertToRecipes(recipes);
    }

    @Override
    public void updateToRecipes(Recipes recipes) {
        recipesMapper.update(recipes);
    }

    @Override
    public void deleteRecipes(Integer id) {
        recipesMapper.delete(id);
    }

    public List<Recipes> findRankingRecipes() {
        return recipesMapper.selectRanking();
    }

    public List<Recipes> searchByKeywordAndCategory(String keyword, String category) {
        String kw = (keyword != null && !keyword.isEmpty()) ? "%" + keyword + "%" : null;
        return recipesMapper.searchByKeywordAndCategory(kw, category);
    }

    public List<Recipes> findRankingByCategory(String category) {
        return recipesMapper.findByCategoryOrderByAccessCountDesc(category);
    }

    @Override
    public void incrementAccessCount(Integer id) {
        recipesMapper.incrementAccessCount(id);
    }

    @Transactional
    public void incrementViewCount(Integer id) {
        recipesMapper.incrementViewCount(id);
    }

}