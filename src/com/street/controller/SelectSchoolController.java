package com.street.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.street.modle.Blog;
import com.street.modle.SelectSchool;

public class SelectSchoolController extends Controller {

	/**
	 * 解析获取学校
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
			renderText(jsonOb.toString());
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

	}
}
