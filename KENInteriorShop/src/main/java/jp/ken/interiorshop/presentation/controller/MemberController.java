package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
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
	@PostMapping(value = "/login", params = "login")
	 public String doLogin(@Valid @ModelAttribute MemberLoginForm form,
			 BindingResult result,Model model, HttpServletRequest request) {
		
		//直前のURLを取得
		String url = request.getRequestURI();
		
		//エラー時にログイン画面に戻る
		if(result.hasErrors()) {
			return "memberLogin";
		}
		
		
		try {
			//入力されたメールアドレスとパスワードを取得
			MemberLoginForm searchForm = new MemberLoginForm();
			searchForm.setMail(form.getMail());
			searchForm.setPassword(form.getPassword());
			
			//全メンバー情報を取得し、リストに保存
			List<MemberLoginForm> login = loginService.getMemberList(searchForm);
			
			boolean match = false;
			
			//メールアドレスとパスワードが一致していれば、matchをtrueに
			for(MemberLoginForm mem : login) {
				if(mem.getMail().equals(form.getMail())
				&& mem.getPassword().equals(form.getPassword())){
					match = true;
					break;
					
				}
			}
			
			if(match) {
				//メールアドレスとパスワードが一致していれば、ログイン情報をmodelに保存
				model.addAttribute(login);
				return url; 
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
	
	//ログアウト処理
	@PostMapping(value = "/logout")
	public String doLogout(@ModelAttribute List<MemberLoginForm> login, HttpServletRequest request) {
		
		String url = request.getRequestURI();
		login = null;
		
		return url;
	}
	
}

