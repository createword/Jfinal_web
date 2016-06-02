package com.street.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.street.controller.BlogController;
import com.street.controller.HelloController;
import com.street.controller.SelectSchoolController;
import com.street.modle.Blog;
import com.street.modle.SelectSchool;

public class CustomConfig extends JFinalConfig {
	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		me.setBaseViewPath("/WEB-INF/view");
		me.setViewType(ViewType.JSP);
	}

	@Override
	public void configRoute(Routes me) {
		// me.add("/hello", HelloController.class);
		me.add("/blog", BlogController.class);
		me.add("/yitiaojie", SelectSchoolController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件

		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
				getProperty("user"), getProperty("password").trim());

		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
	//	arp.addMapping("blog", Blog.class); // 映射blog 表到 Blog模型
			arp.addMapping("s_school", SelectSchool.class); 
			
	}

	@Override
	public void configInterceptor(Interceptors me) {

	}

	@Override
	public void configHandler(Handlers me) {

	}

	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}

}
