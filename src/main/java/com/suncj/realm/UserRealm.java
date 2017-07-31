package com.suncj.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suncj.entity.Permission;
import com.suncj.entity.Role;
import com.suncj.entity.User;
import com.suncj.service.UserService;
import com.suncj.util.MD5Util;

/**
 * 用户认证和授权
 * 
 * @author scj
 *
 */
@Component
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 授权：验证权限时调用
	 * 
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) principalCollection.getPrimaryPrincipal();
		User user = userService.findUserByName(username);

		// 当前用户所有角色
		List<Role> userRoles = userService.selectRolesByUserId(user.getId());
		Set<String> roles = new HashSet<>();
		for (Role role : userRoles) {
			if (StringUtils.isNotBlank(role.getName())) {
				roles.add(role.getName());
			}
		}

		// 当前用户所有权限
		List<Permission> userPermissions = userService.selectPermissionsByUserId(user.getId());
		Set<String> permissions = new HashSet<>();
		for (Permission permission : userPermissions) {
			if (StringUtils.isNotBlank(permission.getPermissionValue())) {
				permissions.add(permission.getPermissionValue());
			}
		}

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.setStringPermissions(permissions);
		simpleAuthorizationInfo.setRoles(roles);
		return simpleAuthorizationInfo;
	}

	/**
	 * 认证：登录时调用
	 * 
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();
		String password = new String((char[]) authenticationToken.getCredentials());

		// 查询用户信息
		User user = userService.findUserByName(username);

		if (null == user) {
			throw new UnknownAccountException();
		}
		if (!user.getPassword().equals(MD5Util.MD5(password + user.getSalt()))) {
			throw new IncorrectCredentialsException();
		}
		if (user.getLocked() == 1) {
			throw new LockedAccountException();
		}

		return new SimpleAuthenticationInfo(username, password, getName());
	}

}
