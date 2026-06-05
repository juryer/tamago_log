package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Favorites;

@Mapper
public interface FavoritesMapper {
    // お気に入り追加
    void insert(Favorites favorites);
    // お気に入り削除
    void delete(@Param("userId") int userId, @Param("recipeId") int recipeId);
    // お気に入り一覧取得
    List<Favorites> findByUserId(@Param("userId") int userId);
    // お気に入り確認（登録済みか）
    Favorites findByUserIdAndRecipeId(@Param("userId") int userId, @Param("recipeId") int recipeId);
}