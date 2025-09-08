package jp.ken.interiorshop.domain.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class ItemEntity {

	private int itemid;
	
	private String itemname;
	
	private int categoryid;
	
	private int itemprice;
	
	private Date rsdate;
	
	private String image;
	
	private String explanation;
	
	private int stock;
}
