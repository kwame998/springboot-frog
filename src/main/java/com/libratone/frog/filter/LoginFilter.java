package com.libratone.frog.filter;

import com.libratone.frog.security.TokenManager;
import com.libratone.frog.security.UserToken;
import com.libratone.frog.base.ResponseEntity;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 判断登录
 */
public class LoginFilter extends AccessControlFilter {
	final static Class<LoginFilter> CLASS = LoginFilter.class;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		UserToken token = TokenManager.getToken();

		if (null != token || isLoginRequest(request, response)) {// &&
			return Boolean.TRUE;
		}
		return Boolean.FALSE;

	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		// 保存Request和Response 到登录后的链接
		// saveRequestAndRedirectToLogin(request, response);
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		ResponseEntity<String> res = new ResponseEntity<String>();
		res.failure("您还没有登录");
		ShiroFilterUtils.out401(response, res, HttpServletResponse.SC_UNAUTHORIZED);
		return Boolean.FALSE;
	}

}
