package jp.ken.interiorshop.application.controlleradvice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;

@ControllerAdvice
public class GlobalControllerAdvice {

	
	@ModelAttribute
    public void addUserAttributes(HttpSession session, Model model) {
        // セッションからログインユーザーを取得
        MemberLoginForm loginUser = (MemberLoginForm) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser != null ? loginUser : new MemberLoginForm());

        // ログイン状態を追加
        model.addAttribute("isLogin", loginUser != null);

        // loginFrag に基づくフラグを追加
        Object loginFrag = session.getAttribute("loginFrag");
        model.addAttribute("userLoggedIn", "true".equals(loginFrag));
    }
	
	/* // ログインしているユーザー名
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
	
	@ModelAttribute("isLogin")
	public Boolean addIsLogin(HttpSession session) {
	   return session.getAttribute("loginUser") != null;
	}*/
   
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