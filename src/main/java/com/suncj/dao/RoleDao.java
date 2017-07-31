package com.suncj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.suncj.entity.Role;
import com.suncj.entity.UserRole;

@Component
public class RoleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Role> roleMapper = new RowMapper<Role>() {
		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setId(rs.getInt("id"));
			role.setName(rs.getString("name"));
			role.setTitle(rs.getString("title"));
			role.setDesc(rs.getString("desc"));
			return role;
		}
	};

	public List<Role> findRolesById(int id) {
		String sql = "SELECT * FROM role WHERE id=?";
		return jdbcTemplate.query(sql, new Object[] { id }, roleMapper);
	}

	public List<Role> findRolesByRoleList(List<UserRole> list) {
		String sql = "SELECT * FROM role WHERE id in (?)";
		Set<Integer> idSet = new HashSet<Integer>();
		for (UserRole userRole : list) {
			idSet.add(userRole.getRoleId());
		}
		return jdbcTemplate.query(sql, new Object[] { StringUtils.join(idSet, ",") }, roleMapper);
	}

}
