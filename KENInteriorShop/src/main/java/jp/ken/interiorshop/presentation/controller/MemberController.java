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
	public String memberLoginForm(@ModelAttribute MemberLoginForm form) {
		return "memberLogin";
	}
	
	//ログイン処理
	@PostMapping("/login")
	 public String doLogin(@Valid @ModelAttribute MemberLoginForm form,
			 BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			return "memberLogin";
		}
		
		try {
			MemberLoginForm searchForm = new MemberLoginForm();
			searchForm.setMail(form.getMail());
			searchForm.setPassword(form.getPassword());
			
			List<MemberLoginForm> list = loginService.getMemberList(searchForm);
			
			boolean match = false;
			
			for(MemberLoginForm mem : list) {
				if(mem.getMail().equals(form.getMail())
				&& mem.getPassword().equals(form.getPassword())){
					match = true;
					break;
					
				}
			}
			
			if(match) {
				return "item"; //本来は検索画面に遷移
			}else {
				model.addAttribute("loginError", "従業員IDまたは氏名が正しくありません");
				return "memberLogin";
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("loginError", "システムエラーが発生しました");
			return "memberLogin";
		}
					
	}
	
}

