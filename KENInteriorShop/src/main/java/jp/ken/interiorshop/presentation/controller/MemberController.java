package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.ken.interiorshop.application.service.LoginService;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	private LoginService loginService;
	
	public MemberController(LoginService loginService) {
		this.loginService = loginService;
	}
	
    @ModelAttribute("userLoggedIn")
    public boolean userLoggedIn() {
    	return false;
    }
	
	//ログイン画面表示
	@GetMapping("/login")
	public String getLoginForm(@ModelAttribute MemberLoginForm form) {
		return "memberLogin";
	}
	
	//ログイン処理
	@PostMapping(value = "/login", params = "doLogin")
	 public String doLogin(@Valid @ModelAttribute("loginUser")MemberLoginForm form,
			 BindingResult result,Model model, HttpServletRequest request, HttpSession session) {
		
		//エラー時にログイン画面に戻る
		if(result.hasErrors()) {
			return "memberLogin";
		}
		
		
		try {
			//全メンバー情報を取得し、リストに保存
			List<MemberLoginForm> login = loginService.getMemberList(form);
			
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
				//メールアドレスとパスワードが一致していれば、ログイン情報をsessionに保存
				model.addAttribute("loginUser", login);
				session.setAttribute("userLoggedIn", true);
				return "redirect:/item";
			}else {
				model.addAttribute("loginError", "メールアドレスまたはパスワードが正しくありません");
				return "memberLogin";
			}
			
		}catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("loginError", "システムエラーが発生しました");
			return "memberLogin";
		}
					
	}
	
	//新規登録画面へ遷移
	@GetMapping(value = "/login", params = "regist")
	public String regist() {
		return "/regist";
	}
	
	//「戻る」を押したら元の画面へ
	@GetMapping(value="/back")
	public String back(HttpSession session) {
		Object backUrl = session.getAttribute("currentUrl");
		return "redirect:" + backUrl;
	}
	
	//ログアウト処理
	@GetMapping(value = "/logout")
	public String doLogout(@ModelAttribute("loginUser") MemberLoginForm form, SessionStatus status, Model model,
			HttpSession session) {
		status.setComplete();
		model.addAttribute("loginUser", null);
		session.setAttribute("userLoggedIn", false);
		return "redirect:/item";
	}
	
}

