package com.street.controller;

import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.street.modle.Blog;

public class BlogController extends Controller {

	public void index() {
		setAttr("blogs", Blog.me.find("select * from blog"));	
		render("index.jsp");
	}
	/*
	 * 传参并获取参数
	 */
	public void test(){
		Integer id=getParaToInt("id");
		List<Blog> list=Blog.me.find("select *from blog where id=?", id);
		for (Blog blog : list) {

			renderText(blog.getStr("title")+blog.getStr("content"));
		}
		
	}

	public void form() {
		Integer id = getParaToInt("id");
		if (id != null && id > 0) {
			setAttr("blog", Blog.me.findById(id));
		}
		render("form.jsp");
	}

	public void delete() {
		Integer id = getParaToInt("id");
	
		if (id != null && id > 0) {
			boolean flag = Blog.me.deleteById(id);
			if (!flag) {
				renderText("删除失败");
				return;
			}
		} else {
			renderText("删除失败");
			return;
		}
		index();
	}

	public void save() {

		Blog blog = getModel(Blog.class, "blog");
		if (blog.get("id") == null) {
			blog.set("publishTime", new Date());
			blog.save();// blog 是jsp里的blog save保存到数据库
		} else {
			blog.update();
		}

		index();
	}

}
