package com.suncj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.suncj.entity.Permission;
import com.suncj.entity.UserPermission;

@Component
public class UserPermissionDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PermissionDao permissionDao;

	private RowMapper<UserPermission> userPermissionMapper = new RowMapper<UserPermission>() {
		@Override
		public UserPermission mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserPermission userPermission = new UserPermission();
			userPermission.setId(rs.getInt("id"));
			userPermission.setUserId(rs.getInt("user_id"));
			userPermission.setPermissionId(rs.getInt("permission_id"));
			return userPermission;
		}
	};

	public List<Permission> findPermissionsByUserId(int userId) {
		String sql = "SELECT * FROM user_permission WHERE user_id=?";
		List<UserPermission> userPermissionList = jdbcTemplate.query(sql, new Object[] { userId }, userPermissionMapper);
		return permissionDao.findPermissionsByList(userPermissionList);
	}

}
