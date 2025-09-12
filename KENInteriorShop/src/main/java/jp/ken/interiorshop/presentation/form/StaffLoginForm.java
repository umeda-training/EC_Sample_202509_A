package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class StaffLoginForm implements Serializable {
	
	private int staffId;
	
	private String password;
}

