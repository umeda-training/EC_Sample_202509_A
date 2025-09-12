package jp.ken.interiorshop.presentation.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

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
	
	//ログイン画面表示
	@GetMapping("/stafflogin")
	public String getLoginForm(StaffLoginForm staffLoginForm) {
		staffLoginForm = new StaffLoginForm();
		return "staffLogin";
	}
	
	//ログイン処理
	@PostMapping(value = "/stafflogin")
	 public String doLogin(@Valid StaffLoginForm staffLoginForm,
			 BindingResult result, Model model) {
		
		//エラー時にログイン画面に戻る
		if(result.hasErrors()) {
			return "staffLogin";
		}

		try {
			//全メンバー情報を取得し、リストに保存
			List<StaffLoginForm> login = staffLoginService.getStaffList(staffLoginForm);
			
			boolean match = false;
		
			//従業員IDとパスワードが一致していれば、matchをtrueに
			StaffLoginForm matchedStaff = null;
			for(StaffLoginForm sta : login) {
				if(Integer.valueOf(sta.getStaffId()).equals(Integer.valueOf(staffLoginForm.getStaffId()))
				&& sta.getPassword().equals(staffLoginForm.getPassword())){
					match = true;
					
					matchedStaff = sta;
					break;
					
				}
			}
			
			if(match) {
				matchedStaff.getStaffId();
				//ログイン情報をsessionに保存
				staffLoginForm = new StaffLoginForm();
				staffLoginForm.setStaffId(matchedStaff.getStaffId());
				staffLoginForm.setStaffName(matchedStaff.getStaffName());
				staffLoginForm.setPassword(matchedStaff.getPassword());
				staffLoginForm.setAdministrator(matchedStaff.getAdministrator());
				model.addAttribute("loginStaff", staffLoginForm);
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

