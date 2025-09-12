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
import jp.ken.interiorshop.application.service.RegistService;
import jp.ken.interiorshop.presentation.form.MemberLoginForm;
import jp.ken.interiorshop.presentation.form.MemberRegistForm;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	private LoginService loginService;
	private RegistService registService;
	
	public MemberController(LoginService loginService, RegistService registService) {
		this.loginService = loginService;
		this.registService = registService;
	}
	
	@ModelAttribute("userLoggedIn")
	public boolean userLoggedIn(HttpSession session) {
	    Object loginFrag = session.getAttribute("loginFrag");
	    return loginFrag != null && loginFrag.equals("true");
	}
	
	//ログイン画面表示
	@GetMapping("/login")
	public String getLoginForm(@ModelAttribute MemberLoginForm form, Model model) {
		MemberLoginForm memberLoginForm = new MemberLoginForm();
		model.addAttribute("loginUser", memberLoginForm);
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
			MemberLoginForm matchedMember = null;
			for(MemberLoginForm mem : login) {
				if(mem.getMail().equals(form.getMail())
				&& mem.getPassword().equals(form.getPassword())){
					match = true;
					
					matchedMember = mem;
					break;
					
				}
			}
			
			if(match) {
				//メールアドレスとパスワードが一致していれば、ログイン情報をsessionに保存
				form = new MemberLoginForm();
				form.setMemberId(matchedMember.getMemberId());
				form.setMemberName(matchedMember.getMemberName());
				form.setMemberKana(matchedMember.getMemberKana());
				form.setMail(matchedMember.getMail());
				form.setPhoneNumber(matchedMember.getPhoneNumber());
				form.setPostalCode(matchedMember.getPostalCode());
				form.setAddress1(matchedMember.getAddress1());
				form.setAddress2(matchedMember.getAddress2());
				form.setAddress3(matchedMember.getAddress3());
				form.setCreditNo(matchedMember.getCreditNo());
				model.addAttribute("loginUser", form);
				session.setAttribute("loginFrag", "true");
				Object backUrl = session.getAttribute("currentUrl");
				return "redirect:" + backUrl;
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
		session.setAttribute("loginFrag", false);
		Object backUrl = session.getAttribute("currentUrl");
		return "redirect:" + backUrl;
	}
	
    //個人情報登録
	//個人情報登録画面へ遷移
    @GetMapping("/registration")
  public String toRegist(Model model) throws Exception{
 	model.addAttribute("memberRegistForm", new MemberRegistForm());
 	
 	return "regist";
  }
  
    @PostMapping("/registration")
    public String showRegist(@Valid @ModelAttribute MemberRegistForm memberRegistForm, BindingResult rs, Model model)throws Exception{
 	model.addAttribute("memberRegistForm", memberRegistForm);
 	
  	if(rs.hasErrors()) {
 		return "regist";
  	}
 	
  	return "registConfirm";
  }
  
  
  @PostMapping("/registDB")
  public String registMembers(@ModelAttribute MemberRegistForm memberRegistForm,  Model model) throws Exception{
  	int numberOfRow = registService.registMembers(memberRegistForm);
  	if(numberOfRow == 0) {
  		 model.addAttribute("error", "このメールアドレスはすでに登録されています");
  		return "/error";
  	}
  	
  	return "registComplete";
  }

  //マイページアクセス時の挙動
  @GetMapping("/mypage")
  public String showMyPage(@ModelAttribute("loginUser") MemberLoginForm form, HttpSession session, Model model) {
      Object loginFrag = session.getAttribute("loginFrag");
      if (form == null || loginFrag == null || !"true".equals(loginFrag)) {
          return "redirect:/login";
      }
      model.addAttribute("member", form);
      return "mypage";
  }
  
//退会確認画面へ遷移
@GetMapping("/withdraw")
public String showWithdraw() {
   return "withdraw"; // withdraw.html を表示
}

//退会処理（実行後に完了画面へ遷移）
@PostMapping("/withdraw")
public String doWithdraw(HttpSession session, SessionStatus status, Model model) {
   // セッション破棄（ログアウトと同様の扱い）
   status.setComplete();
   session.invalidate();
   return "withdrawComplete"; // withdrawComplete.html を表示
}


//会員情報編集するときの挙動
@GetMapping("/edit")
public String editMemberInfo(@ModelAttribute("loginUser") MemberLoginForm form, Model model) {
  model.addAttribute("editForm", form); // 編集用フォームに現在の情報を渡す
  return "edit";
}

@PostMapping("/edit")
public String updateMemberInfo(@Valid @ModelAttribute("editForm") MemberLoginForm form,
                             BindingResult result,
                             Model model,
                             HttpSession session) {
  if (result.hasErrors()) {
      return "edit";
  }

  // 更新処理（サービス層でDB更新）
  //registService.updateMember(form); // updateMemberは仮のメソッド名

  // セッション情報も更新
  session.setAttribute("loginUser", form);
  model.addAttribute("member", form);
  return "redirect:/mypage";
}


}

