package com.street.controller;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
	public void test() {
		JSONObject jsonOb = null;
		JSONObject jsonO1 = null;
		Integer id = getParaToInt("id");
		List<Blog> list = Blog.me.find("select *from blog where id=?", id);
		for (Blog blog : list) {
			try {
				jsonOb = new JSONObject();
				jsonO1 = new JSONObject();
				jsonOb.put("title", blog.getStr("title"));
				jsonOb.put("content", blog.getStr("content"));

				jsonO1.put("ret", 1);
				jsonO1.put("msg", "ok");
				jsonO1.put("ob", jsonOb);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					jsonO1.put("ret", 0);
					jsonO1.put("msg", "数据返回异常");
					jsonO1.put("ob", "");

				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			renderJson(jsonO1.toString());

			// renderText(jsonO1.toString());
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
