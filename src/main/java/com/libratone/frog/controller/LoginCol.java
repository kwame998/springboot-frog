package com.libratone.frog.controller;

import com.libratone.frog.base.ResponseEntity;
import com.libratone.frog.entity.SysUser;
import com.libratone.frog.entity.ins.SysUserInfo;
import com.libratone.frog.security.TokenManager;
import com.libratone.frog.security.UserToken;
import com.libratone.frog.service.SysUserService;
import com.libratone.frog.util.LoggerUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 */
@Controller
public class LoginCol {
	@Autowired
	private SysUserService sysUserService;

	// 登录
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SysUserInfo> doLogin(@RequestBody SysUser user, Boolean rememberMe, String captcha,
											   HttpServletRequest request, RedirectAttributes redirect) {
		ResponseEntity<SysUserInfo> res = new ResponseEntity<SysUserInfo>();
		try {
			TokenManager.login(user, rememberMe);
			UserToken token = TokenManager.getToken();
			SysUserInfo su = new SysUserInfo(token);
			try {
				sysUserService.updateLoginTime(token.getId());
			} catch (Exception e) {
				LoggerUtils.error(getClass(), "更新 系统用户登录时间失败:" + e.getMessage());
			}
			res.setData(su).success("登录成功");
		} catch (DisabledAccountException e) {
			res.failure("账号被禁用");
		} catch (Exception e) {
			e.printStackTrace();
			res.failure("用户名或密码错误");
		}
		return res;
	}

	@RequestMapping(value = "/api/logout", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> logout() {
		ResponseEntity<String> res = new ResponseEntity<String>();
		try {
			TokenManager.logout();
		}catch (Exception ignore){

		}
		res.success("登出成功");
		return res;
	}

}
