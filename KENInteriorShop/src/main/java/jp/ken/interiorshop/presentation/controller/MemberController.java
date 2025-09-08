package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import jp.ken.interiorshop.application.service.LoginService;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

@Controller
public class MemberController {
	
	private LoginService loginService;
	
	public MemberController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	
	//ログイン画面表示
	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") MemberLoginForm form) {
		return "loginForm";
	}
	
	//ログイン処理
	@PostMapping("/login")
	 public String doLogin(@Valid @ModelAttribute("loginForm") MemberLoginForm form,
			 BindingResult result,Model model) {
		
		//エラー時はLoginFormに戻る
		if(result.hasErrors()) {
			return "loginForm";
		}
		
		try {
			MemberLoginForm loginForm = new MemberLoginForm();
			loginForm.setLoginId(form.getLoginId());
			loginForm.setPassword(form.getPassword());
			
			List<MemberLoginForm> list = loginService.getEmployeesList(loginForm);
			
			boolean match = false;
			
			for(MemberLoginForm login : list) {
				if(login.getLoginId().equals(form.getLoginId())
				&& login.getPassword().equals(form.getPassword())){
					match = true;
					break;
					
				}
			}
			
			if(match) {
				return "redirect:/item"; 
			}else {
				model.addAttribute("loginError", "従業員IDまたは氏名が正しくありません");
				return "loginForm";
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("loginError", "システムエラーが発生しました");
			return "loginForm";
		}
					
	}
		

}
