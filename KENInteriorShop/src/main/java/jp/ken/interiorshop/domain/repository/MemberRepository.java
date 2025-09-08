package jp.ken.interiorshop.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.interiorshop.domain.entity.MemberEntity;
import jp.ken.interiorshop.infrastructure.mapper.LoginMapper;

@Repository
public class MemberRepository {

	//LoginMapperクラスのオブジェクトを生成して格納
	private RowMapper<MemberEntity> loginMapper = new LoginMapper();
	//データベースと接続し、情報を保持
	private JdbcTemplate jdbcTemplate;
	
	//MemberRepositoryオブジェクト生成と同時にJdbcTemplateオブジェクトを
	//DIコンテナから取得
	public MemberRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public List<MemberEntity> getMemberAllList() throws Exception {
		
		StringBuilder sb = createCommonSQL();
		sb.append(" ORDER BY");
		sb.append(" member_id");
		String sql = sb.toString();
		
		List<MemberEntity> memberList = jdbcTemplate.query(sql, loginMapper);
		
		return memberList;
	}
	
	private StringBuilder createCommonSQL() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" member_id");
		sb.append(", member_name");
		sb.append(", member_kana");
		sb.append(", mail");
		sb.append(", password");
		sb.append(", phone_number");
		sb.append(", postal_code");
		sb.append(", address1");
		sb.append(", address2");
		sb.append(", address3");
		sb.append(", credit_no");
		sb.append(", cancel");
		sb.append(" FROM");
		sb.append(" members");
		
		return sb;
	}
}
