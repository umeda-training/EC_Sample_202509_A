package jp.ken.interiorshop.domain.repository;

import java.util.ArrayList;
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
		sb.append(" ORDER BY ");
		sb.append(" item_id");
		String sql = sb.toString();
		
		List<ItemEntity> itemList = jdbcTemplate.query(sql, itemMapper);
		
		return itemList;
	}

	//キーワードとカテゴリの両方で検索
	public List<ItemEntity> getItemByKeywordAndCategoryList(String keyword, Integer categoryId) throws Exception {
	    StringBuilder sb = createCommonSQL();
	    sb.append(" WHERE 1=1"); // 条件を柔軟に追加するためのベース

	    List<Object> params = new ArrayList<>();

	    if (keyword != null && !keyword.isEmpty()) {
	        sb.append(" AND item_name LIKE ?");
	        keyword = keyword.replace("%", "\\%").replace("_", "\\_");
	        keyword = "%" + keyword + "%";
	        params.add(keyword);
	    }

	    if (categoryId != null) {
	        sb.append(" AND category_id = ?");
	        params.add(categoryId);
	    }

	    sb.append(" ORDER BY category_id");

	    String sql = sb.toString();

	    return jdbcTemplate.query(sql, itemMapper, params.toArray());
	}
	
	
	//キーワードで検索
	public List<ItemEntity> getItemByKeywordList(String name) throws Exception{
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE");
		sb.append(" item_name LIKE ?");
		sb.append(" ORDER BY");
		sb.append(" category_id");
		String sql = sb.toString();
		
		name = name.replace("%", "\\%").replace("_", "\\_");
		name = "%" + name + "%";
		
		List<ItemEntity> itemList = jdbcTemplate.query(sql, itemMapper, name);
		
		return itemList;	
	}

	//カテゴリで検索
	public ItemEntity getItemByCategoryList(int categoryId) throws Exception{
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE");
		sb.append(" category_id = ?");
		String sql = sb.toString();
		
		ItemEntity itemEntity = jdbcTemplate.queryForObject(sql, itemMapper, categoryId);
		
		return itemEntity;	
	}
	
	//DBから商品テーブル（ID、商品名、カテゴリーID、価格、画像データ）の値を取得
	private StringBuilder createCommonSQL() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" item_id");
		sb.append(", item_name");
		sb.append(", category_id");
		sb.append(", item_price");
		sb.append(", rs_date");
		sb.append(", image");
		sb.append(", explanation");
		sb.append(", stock");
		sb.append(" FROM");
		sb.append(" m_items");
		
		return sb;
	}
}
