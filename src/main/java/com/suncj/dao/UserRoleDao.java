package com.suncj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.suncj.entity.Role;
import com.suncj.entity.UserRole;

@Component
public class UserRoleDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RoleDao roleDao;

	private RowMapper<UserRole> userRoleMapper = new RowMapper<UserRole>() {
		@Override
		public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserRole userRole = new UserRole();
			userRole.setId(rs.getInt("id"));
			userRole.setRoleId(rs.getInt("role_id"));
			userRole.setUserId(rs.getInt("user_id"));
			return userRole;
		}
	};

	public List<Role> findRolesByUserId(int userId) {
		String sql = "SELECT * FROM user_role WHERE user_id=?";
		List<UserRole> userRoleList = jdbcTemplate.query(sql, new Object[] { userId }, userRoleMapper);
		return roleDao.findRolesByRoleList(userRoleList);
	}
}
