package jp.ken.interiorshop.domain.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.interiorshop.domain.entity.CategoryEntity;
import jp.ken.interiorshop.domain.entity.ItemEntity;
import jp.ken.interiorshop.infrastructure.mapper.CategoryMapper;
import jp.ken.interiorshop.infrastructure.mapper.ItemMapper;

@Repository
public class ItemSearchRepository {

	private RowMapper<ItemEntity> itemMapper = new ItemMapper();
	private RowMapper<CategoryEntity> categoryMapper = new CategoryMapper();
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
	
	//createCommonCategorySQL()で取得した値をカテゴリーID順にし、Mapperクラスへ渡す
	public List<CategoryEntity> getCategoryAllList() throws Exception {
		
		StringBuilder sb = createCommonCategorySQL();
		sb.append(" ORDER BY ");
		sb.append(" category_id");
		String sql = sb.toString();
		
		List<CategoryEntity> categoryList = jdbcTemplate.query(sql, categoryMapper);
		
		return categoryList;
	}

	//キーワードとカテゴリの両方で検索
	public List<ItemEntity> getItemByKeywordAndCategoryList(String keyword, Integer categoryId) throws Exception {
	    StringBuilder sb = createCommonSQL();
	    List<Object> params = new ArrayList<>();

	    sb.append(" WHERE");
	    sb.append(" item_name LIKE ?");
	    keyword = keyword.replace("%", "\\%").replace("_", "\\_");
	    keyword = "%" + keyword + "%";
	    params.add(keyword);

	    sb.append(" AND category_id = ?");
	    params.add(categoryId);

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
	public List<ItemEntity> getItemByCategoryList(int categoryId) throws Exception{
		StringBuilder sb = createCommonSQL();
		sb.append(" WHERE");
		sb.append(" category_id = ?");
		String sql = sb.toString();
		
		List<ItemEntity> itemList = jdbcTemplate.query(sql, itemMapper, categoryId);
		
		return itemList;	
	}
	
	//DBからカテゴリーテーブル（ID、カテゴリー名）の値を取得
	private StringBuilder createCommonCategorySQL() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" category_id");
		sb.append(", category_name");
		sb.append(" FROM");
		sb.append(" category");
		
		return sb;
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
