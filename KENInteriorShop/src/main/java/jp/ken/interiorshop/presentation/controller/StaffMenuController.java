package jp.ken.interiorshop.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import jp.ken.interiorshop.presentation.form.StaffLoginForm;

@Controller
public class StaffMenuController {
	
	//メニュー画面へ遷移
	@GetMapping(value = "/staffmenu")
	 public String showStaffMenu(@SessionAttribute("loginStaff") StaffLoginForm staffLoginForm, Model model) {
		model.addAttribute("loginStaff", staffLoginForm);
		return "staffMenu";
	}
}
