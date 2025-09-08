package jp.ken.interiorshop.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.interiorshop.domain.entity.ItemEntity;
import jp.ken.interiorshop.infrastructure.mapper.ItemMapper;

@Repository
public class ItemSearchRepository {

	private RowMapper<ItemEntity> itemMapper = new ItemMapper();
	private JdbcTemplate jdbcTemplate;
	
	public ItemSearchRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//createCommonSQL()で取得した値を商品ID順にし、Mapperクラスへ渡す
	public List<ItemEntity> getItemAllList() throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" ORDER BY");
		sb.append(" item_id");
		String sql = sb.toString();
		
		List<ItemEntity> itemList = jdbcTemplate.query(sql, itemMapper);
		
		return itemList;
	}
	
	//DBから商品テーブル（ID、商品名、カテゴリーID、価格、画像データ）の値を取得
	private StringBuilder createCommonSQL() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" item_id");
		sb.append(" item_name");
		sb.append(" category_id");
		sb.append(" item_price");
		sb.append(" image");
		sb.append(" FROM");
		sb.append(" m_items");
		
		return sb;
	}
}
