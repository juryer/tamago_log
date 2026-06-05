package com.example.demo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.entity.Favorites;
import com.example.demo.mapper.FavoritesMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {

    private final FavoritesMapper favoritesMapper;

    @Override
    public void addFavorite(int userId, int recipeId) {
        Favorites favorites = new Favorites();
        favorites.setUserId(userId);
        favorites.setRecipeId(recipeId);
        favoritesMapper.insert(favorites);
    }

    @Override
    public void removeFavorite(int userId, int recipeId) {
        favoritesMapper.delete(userId, recipeId);
    }

    @Override
    public List<Favorites> findByUserId(int userId) {
        return favoritesMapper.findByUserId(userId);
    }

    @Override
    public boolean isFavorite(int userId, int recipeId) {
        return favoritesMapper.findByUserIdAndRecipeId(userId, recipeId) != null;
    }
}
