package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Users;
import com.example.demo.form.UsersForm;
import com.example.demo.mapper.UsersRepository;

@Controller
public class SignupController {
	@Autowired  // ← 追加
    private UsersRepository usersRepository; 
	@PostMapping("/signup")
	public String signup(@Validated @ModelAttribute UsersForm form,
	                     BindingResult result, Model model,RedirectAttributes redirectAttributes) {
		
//		System.out.println("エラー数: " + result.getErrorCount()); // ← 追加
//	    System.out.println("エラー内容: " + result.getAllErrors()); // ← 追加
//		Users existing = usersRepository.findByUsername(form.getUsername());
//		System.out.println("既存ユーザー: " + existing); // ← 追加
		//名前重複チェック
		if (usersRepository.findByUsername(form.getUsername()) != null) {
	        result.rejectValue("username", "error.duplicate", "このユーザー名はすでに使われています");
	    }
	    // パスワード一致チェック
	    if (!form.getPassword().equals(form.getPasswordConfirm())) {
	        result.rejectValue("passwordConfirm", "error.match", "パスワードが一致しません");
	    }
	    if (result.hasErrors()) {
	        model.addAttribute("usersForm", form); // ← エラー時もformを渡す
	        return "signup";
	    }

	    Users user = new Users();
	    user.setUsername(form.getUsername());
	    user.setPassword(form.getPassword());
	    usersRepository.insert(user);
	    redirectAttributes.addFlashAttribute("message", "登録が完了しました！");
	    return "redirect:/login";
	}
	
	@GetMapping("/signup")
	public String signupForm(Model model) {
	    model.addAttribute("usersForm", new UsersForm());
	    return "signup";
	}
}
