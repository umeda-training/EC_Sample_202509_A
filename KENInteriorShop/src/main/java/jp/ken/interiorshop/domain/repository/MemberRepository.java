package jp.ken.interiorshop.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.interiorshop.domain.entity.MemberEntity;
import jp.ken.interiorshop.infrastructure.mapper.LoginMapper;

@Repository
public class MemberRepository {

	private RowMapper<MemberEntity> loginMapper = new LoginMapper();
	private JdbcTemplate jdbcTemplate;
	
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
	
	//個人情報登録処理
	public int regist(MemberEntity memberEntity) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO members");
		sb.append(" (");
		sb.append("member_name");
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
		sb.append(")");
		sb.append(" VALUES");
		sb.append(" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		String sql = sb.toString();
		
		Object[] parameters = {
			        memberEntity.getMemberName(),
			        memberEntity.getMemberKana(),
			        memberEntity.getMail(),
			        memberEntity.getPassword(),
			        memberEntity.getPhoneNumber(),
			        memberEntity.getPostalCode(),
			        memberEntity.getAddress1(),
			        memberEntity.getAddress2(),
			        memberEntity.getAddress3(),
			        memberEntity.getCreditNo(),
			        memberEntity.getCancel()
			        
			    };
		
		int numberOfRow = jdbcTemplate.update(sql, parameters);
		return numberOfRow;
			}
}

