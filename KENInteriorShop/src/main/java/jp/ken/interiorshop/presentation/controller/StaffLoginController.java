package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.ken.interiorshop.application.service.StaffLoginService;
import jp.ken.interiorshop.presentation.form.StaffLoginForm;

@Controller
@SessionAttributes("loginStaff")
public class StaffLoginController {
	
	private StaffLoginService staffLoginService;
	
	public StaffLoginController(StaffLoginService staffLoginService) {
		this.staffLoginService = staffLoginService;
	}
	
   @ModelAttribute("loginStaff")
	public StaffLoginForm staffLoginForm() {
    	return new StaffLoginForm();
    	}
	
	//ログイン画面表示
	@GetMapping("/stafflogin")
	public String getLoginForm(@ModelAttribute StaffLoginForm form, Model model) {
		StaffLoginForm staffLoginForm = new StaffLoginForm();
		model.addAttribute("loginStaff", staffLoginForm);
		return "staffLogin";
	}
	
	//ログイン処理
	@PostMapping(value = "/stafflogin")
	 public String doLogin(@Valid @ModelAttribute("loginStaff")StaffLoginForm loginForm,
			 BindingResult result,Model model, HttpSession session) {
		
		//エラー時にログイン画面に戻る
		if(result.hasErrors()) {
			return "staffLogin";
		}

		try {
			//全メンバー情報を取得し、リストに保存
			List<StaffLoginForm> login = staffLoginService.getStaffList(loginForm);
			
			boolean match = false;
		
			//従業員IDとパスワードが一致していれば、matchをtrueに
			StaffLoginForm matchedStaff = null;
			for(StaffLoginForm sta : login) {
				if(sta.getStaffId() == loginForm.getStaffId()
				&& sta.getPassword().equals(loginForm.getPassword())){
					match = true;
					
					matchedStaff = sta;
					break;
					
				}
			}
			
			if(match) {
				matchedStaff.getStaffId();
				//ログイン情報をsessionに保存
				loginForm = new StaffLoginForm();
				loginForm.setStaffId(matchedStaff.getStaffId());
				loginForm.setStaffName(matchedStaff.getStaffName());
				loginForm.setPassword(matchedStaff.getPassword());
				loginForm.setAdministrator(matchedStaff.getAdministrator());
				model.addAttribute("loginStaff", loginForm);
				session.setAttribute("staffLoginFrag", "true");
				return "/staffmenu";
			}else {
				model.addAttribute("staffLoginError", "従業員IDまたはパスワードが正しくありません");
				return "staffLogin";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("staffLoginError", "システムエラーが発生しました");
			return "staffLogin";
		}
	}
}

