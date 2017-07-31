package com.suncj.web;

import io.swagger.annotations.ApiOperation;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suncj.constant.ResultConstant;
import com.suncj.constant.SystemResult;
import com.suncj.entity.UserSession;
import com.suncj.session.UserSessionDao;
import com.suncj.util.RedisUtil;

@Controller
public class UserController {
	private final static Logger _log = LoggerFactory.getLogger(PageController.class);

	@Autowired
	private UserSessionDao userSessionDao;

	// 全局会话key
	private final static String SERVER_SESSION_ID = "server-session-id";

	// 全局会话key列表
	private final static String SERVER_SESSION_IDS = "server-session-ids";

	@ApiOperation(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String serverSessionId = session.getId().toString();
		// 判断是否已登录，如果已登录，则回跳
		String code = RedisUtil.get(SERVER_SESSION_ID + "_" + serverSessionId);
		// code校验值
		if (StringUtils.isNotBlank(code)) {
			// 回跳
			String backurl = request.getParameter("backurl");
			String username = (String) subject.getPrincipal();
			if (StringUtils.isBlank(backurl)) {
				backurl = "/";
			} else {
				if (backurl.contains("?")) {
					backurl += "&upms_code=" + code + "&upms_username=" + username;
				} else {
					backurl += "?upms_code=" + code + "&upms_username=" + username;
				}
			}
			_log.debug("认证中心帐号通过，带code回跳：{}", backurl);
			return "redirect:" + backurl;
		}
		return "/user/login";
	}

	@ApiOperation(value = "登录")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rememberMe = request.getParameter("rememberMe");
		if (StringUtils.isBlank(username)) {
			return new SystemResult(ResultConstant.EMPTY_USERNAME, "帐号不能为空！");
		}
		if (StringUtils.isBlank(password)) {
			return new SystemResult(ResultConstant.EMPTY_PASSWORD, "密码不能为空！");
		}
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		String sessionId = session.getId().toString();
		// 判断是否已登录，如果已登录，则回跳，防止重复登录
		String hasCode = RedisUtil.get(SERVER_SESSION_ID + "_" + sessionId);
		// code校验值
		if (StringUtils.isBlank(hasCode)) {
			// 使用shiro认证
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
			try {
				if (BooleanUtils.toBoolean(rememberMe)) {
					usernamePasswordToken.setRememberMe(true);
				} else {
					usernamePasswordToken.setRememberMe(false);
				}
				subject.login(usernamePasswordToken);
			} catch (UnknownAccountException e) {
				return new SystemResult(ResultConstant.INVALID_USERNAME, "帐号不存在！");
			} catch (IncorrectCredentialsException e) {
				return new SystemResult(ResultConstant.INVALID_PASSWORD, "密码错误！");
			} catch (LockedAccountException e) {
				return new SystemResult(ResultConstant.INVALID_ACCOUNT, "帐号已锁定！");
			}
			// 更新session状态
			userSessionDao.updateStatus(sessionId, UserSession.OnlineStatus.on_line);
			// 全局会话sessionId列表，供会话管理
			RedisUtil.lpush(SERVER_SESSION_IDS, sessionId.toString());
			// 默认验证帐号密码正确，创建code
			String code = UUID.randomUUID().toString();
			// 全局会话的code
			RedisUtil.set(SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
		}
		// 回跳登录前地址
		String backurl = request.getParameter("backurl");
		if (StringUtils.isBlank(backurl)) {
			return new SystemResult(ResultConstant.SUCCESS, "/");
		} else {
			return new SystemResult(ResultConstant.SUCCESS, backurl);
		}
	}

	@ApiOperation(value = "退出登录")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		// shiro退出登录
		SecurityUtils.getSubject().logout();
		// 跳回原地址
		String redirectUrl = request.getHeader("Referer");
		if (null == redirectUrl) {
			redirectUrl = "/";
		}
		return "redirect:" + redirectUrl;
	}

	@RequiresPermissions("user:manage")
	@RequestMapping(value = "/user/manage", method = RequestMethod.GET)
	public String manage() {
		return "user/manage";
	}
}
