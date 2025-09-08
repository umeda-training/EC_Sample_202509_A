package jp.ken.interiorshop.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import jp.ken.interiorshop.domain.entity.ItemEntity;

public class ItemMapper implements RowMapper<ItemEntity> {

	//DBから取得したアイテムテーブルの値をアイテムEntityにセット
	@Override
	public ItemEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
		ItemEntity itemEntity = new ItemEntity();
		
		itemEntity.setItemid(rs.getInt("item_id"));
		itemEntity.setItemname(rs.getString("item_name"));
		itemEntity.setCategoryid(rs.getInt("category_id"));
		itemEntity.setItemprice(rs.getInt("item_price"));
		itemEntity.setRsdate(rs.getDate("rs_date"));
		itemEntity.setImage(rs.getString("image"));
		itemEntity.setExplanation(rs.getString("explanation"));
		itemEntity.setStock(rs.getInt("stock"));
		
		return itemEntity;
	}
}
