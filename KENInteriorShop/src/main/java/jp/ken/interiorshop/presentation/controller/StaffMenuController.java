package jp.ken.interiorshop.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.interiorshop.presentation.form.StaffLoginForm;

@Controller
@SessionAttributes("loginStaff")
public class StaffMenuController {
	
	  @ModelAttribute("loginStaff")
		public StaffLoginForm staffLoginForm() {
	    	return new StaffLoginForm();
	    	}
	
	//メニュー画面へ遷移
	@GetMapping(value = "/staffmenu")
	 public String showStaffMenu() {
		return "staffMenu";
	}
}
