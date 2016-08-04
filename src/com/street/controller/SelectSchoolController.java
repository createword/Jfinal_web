package com.street.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.street.modle.Blog;
import com.street.modle.SelectSchool;

public class SelectSchoolController extends Controller {

	/**
	 * 解析获取学校接口
	 */
	public void SelectProvinceJson() {
		JSONObject jsonOb = null;
		try {

			Integer id = getParaToInt("pid");
			ArrayList<HashMap<String, Object>> Maplist = new ArrayList<HashMap<String, Object>>();
			List<SelectSchool> list = SelectSchool.me.find(
					"select *from s_school where pid=?", id);
			HashMap<String, Object> map;
			for (SelectSchool category : list) {
				map = new HashMap<String, Object>();
				map.put("title", category.get("schoolname"));
				Maplist.add(map);

			}

			jsonOb = new JSONObject();

			jsonOb.put("info", Maplist);
			jsonOb.put("ret", 0);

		} catch (JSONException e) {
			try {
				jsonOb.put("ret", 1);
				jsonOb.put("msg", "服务器访问失败");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		renderText(jsonOb.toString());
		System.out.println(jsonOb.toString());
	}

	/**
	 * 获取学校的信息通过学校名称进行查询 （内连接）
	 */
	public void getinfomation() {
		JSONObject jsonOb = null;
		String scname = getPara("schoolname");
		ArrayList<Map<String, Object>> mapParams = new ArrayList<Map<String, Object>>();
		Map<String, Object> params;
		List<SelectSchool> list = SelectSchool.me
				.find("select i.iid,i.infotitle,i.imgsrc,i.price,i.titme,i.infobody from i_infoschool AS i join s_school AS s ON i.depid=s.sid where s.schoolname=?",
						scname);

		for (SelectSchool selectSchool : list) {
			params = new HashMap<String, Object>();
			params.put("iid", selectSchool.get("iid"));
			params.put("infotitle", selectSchool.get("infotitle"));
			params.put("price", selectSchool.get("price"));
			params.put("time", selectSchool.get("titme"));
			params.put("infobody", selectSchool.get("infobody"));
			mapParams.add(params);

		}

		try {
			jsonOb = new JSONObject();
			jsonOb.put("ret", 0);
			jsonOb.put("info", mapParams);

		} catch (JSONException e) {
			try {
				jsonOb.put("msg", "服务器访问失败");
				jsonOb.put("ret", 1);
			} catch (Exception e2) {
				// TODO: handle exception
			}
			e.printStackTrace();
		}

		renderText(jsonOb.toString());
		System.out.println(jsonOb.toString());

	}

}
