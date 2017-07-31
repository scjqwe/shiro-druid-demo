package com.suncj.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;

import com.suncj.entity.UserSession;

/**
 * session工厂
 * 
 * @author scj
 *
 */
@Component
public class UserSessionFactory implements SessionFactory {

	@Override
	public Session createSession(SessionContext sessionContext) {
		UserSession session = new UserSession();
		if (null != sessionContext && sessionContext instanceof WebSessionContext) {
			WebSessionContext webSessionContext = (WebSessionContext) sessionContext;
			HttpServletRequest request = (HttpServletRequest) webSessionContext.getServletRequest();
			if (null != request) {
				session.setHost(request.getRemoteAddr());
				session.setUserAgent(request.getHeader("User-Agent"));
			}
		}
		return session;
	}

}
