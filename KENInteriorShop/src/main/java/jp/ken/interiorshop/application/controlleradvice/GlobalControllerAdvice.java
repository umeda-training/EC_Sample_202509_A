package jp.ken.interiorshop.application.controlleradvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

@ControllerAdvice
@SessionAttributes("userLoggedIn")
public class GlobalControllerAdvice {

	 // ログインしているユーザー名
    @ModelAttribute("loginUser")
	public MemberLoginForm memberLoginForm(HttpSession session) {
    	return new MemberLoginForm();
    	}

    // ログインしているかどうか (true/false)
   @ModelAttribute("userLoggedIn")
   public boolean addUserLoggedIn(@ModelAttribute("loginUser")MemberLoginForm form) {
	    if(form.getMail() != null && form.getPassword() != null) {
	    	return true;
	    } else {
	    	return false;
	    }
    }
}