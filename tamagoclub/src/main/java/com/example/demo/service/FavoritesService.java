package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Favorites;

public interface FavoritesService {
    // お気に入り追加
    void addFavorite(int userId, int recipeId);
    // お気に入り削除
    void removeFavorite(int userId, int recipeId);
    // お気に入り一覧取得
    List<Favorites> findByUserId(int userId);
    // お気に入り確認（登録済みか）
    boolean isFavorite(int userId, int recipeId);
}
