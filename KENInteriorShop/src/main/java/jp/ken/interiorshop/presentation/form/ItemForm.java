package jp.ken.interiorshop.presentation.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemForm implements Serializable {

	private String itemid;
	
	private String itemname;
	
	private String categoryid;
	
	private String itemprice;
	
	private String rsdate;
	
	private String image;
	
	private String explanation;
	
	private String stock;
}
