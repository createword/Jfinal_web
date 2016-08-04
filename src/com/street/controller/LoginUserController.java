package com.street.controller;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.street.modle.LoginUser;

public class LoginUserController extends Controller {

	/**
	 * 验证登陆用户名和密码
	 * 
	 * @return 张恒铭by
	 */
	@SuppressWarnings("null")
	public boolean VerifyCode() {
		boolean result = false;
		JSONObject jonObject = new JSONObject();
		JSONObject sendObject = new JSONObject();
		String username = getPara("username");
		String password = getPara("password");
		String sql = "select count(userid) from u_user where username=? and password=?";
		List<LoginUser> loginUserslist = LoginUser.L_USER.find(sql, username,
				password);
		if (loginUserslist != null && loginUserslist.size() > 0) {
			try {
				for (LoginUser loginUser : loginUserslist) {
					// 注解注:java.lang.Number是Integer,Long的父类. 若果直接用Integer 会直接报错
					Number number = loginUser.get("count(userid)");

					if (number.intValue() > 0) {
						jonObject.put("isflag", true);
						jonObject.put("msg", "");
						sendObject.put("data", jonObject);
						System.out.println(sendObject.toString());
						System.out.println("登陆成功"
								+ loginUser.get("count(userid)")
								+ loginUserslist.size());
						renderText(sendObject.toString());
						return true;
					} else {
						
						jonObject.put("isflag", false);
						jonObject.put("msg", "用户名或密码验证失败");
						sendObject.put("data", jonObject);
						System.out.println(sendObject.toString());
						System.out.println("登录失败！");
						renderText(sendObject.toString());
						return false;
						// TODO Auto-generated catch block
					}

				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		} else {
			return false;
		}
		return result;

	}
}
