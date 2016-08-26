package com.street.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.core.Controller;
import com.street.modle.NewsShow;

public class NewsShowController extends Controller {
	private JSONObject jsonOb = null;

	/**
	 * topshow 图片展览
	 */
	public void topShow() {

		try {
			List<NewsShow> ListnewsShows = NewsShow.me
					.find("select * from g_goodsinfo");
			ArrayList<Map<String, String>> Arraymaps = new ArrayList<Map<String, String>>();
			for (NewsShow newsdata : ListnewsShows) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("pictitle", (String) newsdata.getStr("Pictitle"));
				map.put("picurl", (String) newsdata.getStr("Picurl"));
				Arraymaps.add(map);
			}
			jsonOb = new JSONObject();
			jsonOb.put("ret", 200);
			jsonOb.put("info", Arraymaps);
			
			System.out.println(jsonOb.toString());
		} catch (JSONException e) {
			try {
				jsonOb.put("ret", 500);
				jsonOb.put("msg", "服务器遇到错误，无法完成请求！");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		renderText(jsonOb.toString());
	}
}
