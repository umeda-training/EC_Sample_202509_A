package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryForm implements Serializable {
	private String categoryId;
	
	private String categoryName;
}
