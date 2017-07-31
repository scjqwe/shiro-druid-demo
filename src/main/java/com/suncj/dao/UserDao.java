package com.suncj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.suncj.entity.User;

@Component
public class UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setSalt(rs.getString("salt"));
			user.setLocked(rs.getInt("locked"));
			return user;
		}
	};

	public User findUserByName(String username) {
		String sql = "SELECT * FROM user WHERE username=?;";
		return jdbcTemplate.queryForObject(sql, new Object[] { username }, userMapper);
	}

}
