package com.kalman03.easypay.web;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

public class ThreadLocalContext {

	private final static String SESSION_USER_KEY = "session_user";
	
	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

	public static void setSessionUser(SessionUser sessionUser) {
		Map<String, Object> map = threadLocal.get();
		if(map == null) {
			map = newHashMap();
		}
		map.put(SESSION_USER_KEY, sessionUser);
		threadLocal.set(map);
	}
	
	public static void remove() {
		threadLocal.remove();
	}

	@SuppressWarnings("unchecked")
	private static <T> T getObject(String key, Class<T> clazz) {
		Map<String, Object> map = threadLocal.get();
		if(map == null) {
			return null;
		}
		Object object = map.get(key);
		return object != null ? (T) object : null;
	}
	public static SessionUser getSessionUser() {
		return getObject(SESSION_USER_KEY, SessionUser.class);
	}
	public static boolean isLogin() {
		return getSessionUser() != null;
	}

	public static String getOpenId() {
		if (!isLogin()) {
			return null;
		}
		return getSessionUser().getOpenId();
	}

}