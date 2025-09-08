package jp.ken.interiorshop.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.interiorshop.domain.entity.CategoryEntity;
import jp.ken.interiorshop.domain.entity.ItemEntity;

public class CategoryMapper implements RowMapper<CategoryEntity> {
	//DBから取得したcategoryテーブルの値をカテゴリEntityにセット
	@Override
	public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException{
		CategoryEntity categoryEntity = new CategoryEntity();
		ItemEntity itemEntity = new ItemEntity();
		
		categoryEntity.setCategoryid(rs.getInt("category_id"));
		categoryEntity.setCategoryid(rs.getInt("category_name"));
		
		itemEntity.setItemid(rs.getInt("item_id"));
		itemEntity.setItemname(rs.getString("item_name"));
		itemEntity.setCategoryid(rs.getInt("category_id"));
		itemEntity.setItemprice(rs.getInt("item_price"));
		itemEntity.setRsdate(rs.getDate("rs_date"));
		itemEntity.setExplanation(rs.getString("explanation"));
		itemEntity.setStock(rs.getInt("stock"));
		itemEntity.setCategory(categoryEntity);
		
		return categoryEntity;		
	}
}
