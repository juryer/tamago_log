package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Recipes;

@Mapper
public interface RecipesMapper {
	
	/*すべてのすることを取得*/
	List<Recipes> selectAll();
	/*指定されたIDに対応する「すること」を取得します*/
	Recipes  selectById(@Param("id") Integer id);

	/*登録します*/
	void insertToRecipes(Recipes recipes);

	/*更新します*/
//	@Update("""
//			UPDATE recipes
//			SET title = #{title},
//			eggs = #{eggs},
//			description = #{description},
//			image_url = #{imageUrl}
//			
//			WHERE id = #{id} 
//			""")
	void update(Recipes recipes);

	/*削除します*/
	void delete(@Param("id") Integer id);
	
	/*ランキングを表示します*/
	List<Recipes> selectRanking();
	
	/*検索欄を使えます*/
	List<Recipes> searchByTitle(@Param("keyword") String keyword);
	
	void incrementAccessCount(@Param("id") Integer id);
	
	void incrementViewCount(@Param("id") Integer id);
	
	List<Recipes> searchByKeywordAndCategory(@Param("keyword") String keyword, @Param("category") String category);
	
	List<Recipes> findByCategoryOrderByAccessCountDesc(String category);
	}

