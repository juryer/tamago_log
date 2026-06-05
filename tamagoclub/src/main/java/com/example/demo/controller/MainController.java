package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Favorites;
import com.example.demo.entity.Recipes;
import com.example.demo.entity.Users;
import com.example.demo.form.RecipesForm;
import com.example.demo.mapper.UsersRepository;
import com.example.demo.service.CustomUserDetails;
import com.example.demo.service.FavoritesServiceImpl;
import com.example.demo.service.RecipesServiceImpl;

/*ユーザーからhtmlリクエストを受け取ってビュー名を返す
 * 返すビュー名は"/register"、"/list"、"/ranking"
 */

@Controller
@RequestMapping("/main")
public class MainController {

	@Autowired
	private RecipesServiceImpl recipesServiceImpl;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private FavoritesServiceImpl favoritesService;

	@GetMapping("")        // /main
	public String main() {
		return "main";
	}

	@GetMapping("/register")//URL名
	public String register(Model model) {
		model.addAttribute("recipesForm", new RecipesForm());;
		//System.out.println("Controller入った");
		return "main/register";//htmlファイル名
	}

	//	@PostMapping("/new")
	//	public String registerPost(@ModelAttribute RecipesForm recipesForm) {
	//	    // TODO: DB保存処理
	//		recipesServiceImpl.insertToRecipes(recipesForm);
	//	    return "redirect:/main";
	//	}

//	@PostMapping("/new")
//	public String registerPost(@ModelAttribute RecipesForm recipesForm,
//			@RequestParam(required = false) MultipartFile imageFile,
//			@AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
//		
//		 System.out.println("ログインユーザーID: " + userDetails.getUserId()); // ← 追加
//		    System.out.println("ログインユーザー名: " + userDetails.getUsername()); // ← 追加
//		recipesServiceImpl.insertToRecipes(recipesForm, imageFile, userDetails);
//		return "redirect:/main";
//	}
	
//	@PostMapping("/new")
//	public String registerPost(@ModelAttribute RecipesForm recipesForm,
//	        @RequestParam(required = false) MultipartFile imageFile,
//	        @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
//	    
//	    System.out.println("imageFile: " + imageFile);
//	    System.out.println("isEmpty: " + (imageFile == null ? "null" : imageFile.isEmpty()));
//	    System.out.println("ファイル名: " + (imageFile == null ? "null" : imageFile.getOriginalFilename()));
//	    
//	    recipesServiceImpl.insertToRecipes(recipesForm, imageFile, userDetails);
//	    return "redirect:/main";
//	}
	
	@PostMapping("/new")
	public String registerPost(@Validated @ModelAttribute RecipesForm recipesForm,
	        BindingResult result,
	        @RequestParam(required = false) MultipartFile imageFile,
	        @AuthenticationPrincipal CustomUserDetails userDetails,
	        Model model) throws IOException {

	    if (result.hasErrors()) {
	        model.addAttribute("recipesForm", recipesForm);
	        return "main/register";
	    }

	    recipesServiceImpl.insertToRecipes(recipesForm, imageFile, userDetails);
	    return "redirect:/main";
	}

	@GetMapping("/ranking")
	public String ranking(@RequestParam(required = false) String category,
	                      Model model) {

	    List<Recipes> rankingList;

	    if (category == null || category.isEmpty()) {
	        rankingList = recipesServiceImpl.findRankingRecipes(); // 全体
	    } else {
	        rankingList = recipesServiceImpl.findRankingByCategory(category); // カテゴリ別
	    }

	    model.addAttribute("rankingList", rankingList);
	    model.addAttribute("currentCategory", category);

	    return "main/ranking";
	}

	@GetMapping("/list")
	public String showList(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
		List<Recipes> itemList = recipesServiceImpl.findAllRecipes();

		model.addAttribute("recipesList", itemList);
		model.addAttribute("recipeCount", itemList.size());


		// お気に入り済みのrecipeIdをSetで渡す
		List<Favorites> favorites = favoritesService.findByUserId(userDetails.getUserId());
		Set<Integer> favoriteIds = favorites.stream()
				.map(Favorites::getRecipeId)
				.collect(Collectors.toSet());
		model.addAttribute("favoriteIds", favoriteIds);
		model.addAttribute("favoriteCount", favorites.size());


		return "main/list";
	}

	@GetMapping("/")
	public String root() {
		return "redirect:/main";
	}

	@GetMapping("/search")
	public String search(@RequestParam(required = false) String keyword,
	                     @RequestParam(required = false) String category,
	                     Model model,
	                     @AuthenticationPrincipal CustomUserDetails userDetails) {
	    List<Recipes> itemList = recipesServiceImpl.searchByKeywordAndCategory(keyword, category);
	    model.addAttribute("recipesList", itemList);
	    
	    Set<Integer> favoriteIds = favoritesService.findByUserId(userDetails.getUserId())
	            .stream().map(Favorites::getRecipeId).collect(Collectors.toSet());
	    model.addAttribute("favoriteIds", favoriteIds);
	    
	    return "main/list";
	}

	@GetMapping("/detail/{id}")
	public String detail(@PathVariable Integer id, Model model) {
	    recipesServiceImpl.incrementViewCount(id);
	    Recipes recipes = recipesServiceImpl.findByIdRecipes(id);
	    model.addAttribute("item", recipes);
	    
	    // 投稿者名を取得して渡す
	    Users user = usersRepository.findById(recipes.getUserId());
	    model.addAttribute("authorName", user != null ? user.getUsername() : "投稿者");
	    
	    return "main/detail";
	}
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Integer id, Model model) {
	    Recipes item = recipesServiceImpl.findByIdRecipes(id);
	    model.addAttribute("item", item);

	    RecipesForm form = new RecipesForm();
	    form.setTitle(item.getTitle());
	    form.setEggs(item.getEggs());
	    form.setDescription(item.getDescription());
	    form.setIngredients(item.getIngredients());
	    form.setId(item.getId());
	    String[] steps = item.getDescription().split("\n");
	    model.addAttribute("steps", steps);
	    model.addAttribute("itemForm", form);
	    

	    return "main/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String updateDetail(@PathVariable Integer id,
	                           @ModelAttribute RecipesForm itemForm,
	                           @RequestParam(required = false) MultipartFile imageFile,
	                           @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {

	    Recipes recipes = recipesServiceImpl.findByIdRecipes(id);
	    recipes.setTitle(itemForm.getTitle());
	    recipes.setEggs(itemForm.getEggs());
	    recipes.setDescription(itemForm.getDescription());
	    recipes.setCategory(itemForm.getCategory());
	    recipes.setId(recipes.getId());
	    recipes.setIngredients(itemForm.getIngredients());

	    // 新しい画像が選択された場合のみ更新
	    if (imageFile != null && !imageFile.isEmpty()) {
	        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
	        Path savePath = Paths.get("src/main/resources/static/images/" + fileName);
	        Files.copy(imageFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);
	        recipes.setImageUrl("/images/" + fileName);
	    }
	    // 画像が選択されていない場合は既存のimageUrlをそのまま維持

	    recipesServiceImpl.updateToRecipes(recipes);
	    return "redirect:/main/list";
	}
	
	
	@PostMapping("/delete/{id}")
	public String deletePost(@PathVariable Integer id,@AuthenticationPrincipal CustomUserDetails userDetails) {
		//Users loginUser = (Users)session.getAttribute("loginUser");
		if(userDetails == null) {
			return "redirect:/main";
		}
		
		Recipes recipes = recipesServiceImpl.findByIdRecipes(id);
		if(recipes != null && recipes.getUserId() != null && userDetails.getUserId() == recipes.getUserId()) {
	    recipesServiceImpl.deleteRecipes(id);
	    return "redirect:/main/list";
		}else {
			return "redirect:/main/list";
		}
	}

}

