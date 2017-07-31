package com.suncj.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 会话监听器
 * 
 * @author scj
 *
 */
@Component
public class SystemSessionListener implements SessionListener {

	private static Logger _log = LoggerFactory.getLogger(SystemSessionListener.class);

	@Override
	public void onStart(Session session) {
		_log.debug("会话创建：" + session.getId());
	}

	@Override
	public void onStop(Session session) {
		_log.debug("会话停止：" + session.getId());
	}

	@Override
	public void onExpiration(Session session) {
		_log.debug("会话过期：" + session.getId());
	}

}
