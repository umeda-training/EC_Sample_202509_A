package jp.ken.interiorshop.application.controlleradvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

@ControllerAdvice
public class GlobalControllerAdvice {

	 // ログインしているユーザー名
    @ModelAttribute("loginUser")
//    public String addLoginUser(HttpSession session) {
//        return (String) session.getAttribute("loginUser");
//    }
	public MemberLoginForm memberLoginForm(HttpSession session) {
    	return new MemberLoginForm();
    	}

    // ログインしているかどうか (true/false)
    @ModelAttribute("userLoggedIn")
    public boolean addUserLoggedIn(HttpSession session) {
        return session.getAttribute("loginUser") != null;
    }
}