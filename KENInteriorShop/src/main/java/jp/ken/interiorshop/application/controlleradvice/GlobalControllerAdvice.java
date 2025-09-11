package jp.ken.interiorshop.application.controlleradvice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

@ControllerAdvice
public class GlobalControllerAdvice {

	 // ログインしているユーザー名
    @ModelAttribute("loginUser")
	public MemberLoginForm memberLoginForm(HttpSession session) {
    	return new MemberLoginForm();
    	}

    // ログインしているかどうか (true/false)
   @ModelAttribute("userLoggedIn")
   public boolean addUserLoggedIn(HttpSession session) {
	    if(session.getAttribute("loginFrag") == "true") {
	    	return true;
	    } else {
	    	return false;
	    }
    }
   
   // IllegalArgumentException を捕捉
   @ExceptionHandler(IllegalArgumentException.class)
   public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
       model.addAttribute("errorMessage", ex.getMessage());
       return "error"; // error.html に遷移
   }

   // その他の例外を捕捉
   @ExceptionHandler(Exception.class)
   public String handleException(Exception ex, Model model) {
       model.addAttribute("errorMessage", "予期しないエラーが発生しました");
       // ログ出力もしておくとデバッグに便利
       ex.printStackTrace();
       return "error";
   }   
   
}