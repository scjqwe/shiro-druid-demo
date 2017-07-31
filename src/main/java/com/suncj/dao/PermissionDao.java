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

import com.suncj.entity.Permission;
import com.suncj.entity.UserPermission;

@Component
public class PermissionDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Permission> permissionMapper = new RowMapper<Permission>() {
		@Override
		public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
			Permission permission = new Permission();
			permission.setId(rs.getInt("id"));
			permission.setName(rs.getString("name"));
			permission.setType(rs.getInt("type"));
			permission.setPermissionValue(rs.getString("permission_value"));
			permission.setUri(rs.getString("uri"));
			permission.setStatus(rs.getInt("status"));
			return permission;
		}
	};

	public List<Permission> findPermissionsById(int id) {
		String sql = "SELECT * FROM permission WHERE id=?";
		return jdbcTemplate.query(sql, new Object[] { id }, permissionMapper);
	}

	public List<Permission> findPermissionsByList(List<UserPermission> list) {
		String sql = "SELECT * FROM permission WHERE id in (?)";
		Set<Integer> idSet = new HashSet<Integer>();
		for (UserPermission userPermission : list) {
			idSet.add(userPermission.getPermissionId());
		}
		return jdbcTemplate.query(sql, new Object[] { StringUtils.join(idSet, ",") }, permissionMapper);
	}

}
