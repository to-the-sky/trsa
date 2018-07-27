package com.ybg.tags.service;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.ybg.api.domain.WeixinJson;
import com.ybg.api.network.WeixinNetWork;
import com.ybg.api.service.WeixinApiService;
import com.ybg.tags.domain.WeixinTagsVO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/*** @author https://gitee.com/YYDeament/88ybg
 * 
 * @date 2016/10/1 */
@Repository
public class WeixinTagsServiceImpl implements WeixinTagsService {
	
	@Autowired
	WeixinNetWork			weixinNW;
	@Autowired
	WeixinApiService	weixinApiService;
	
	private String getAccessToken() {
		return weixinApiService.getAccessToken();
	}
	
	@Override
	public void create(WeixinTagsVO bean) {
		weixinNW.tags_create(getAccessToken(), bean.getName());
	}
	
	@Override
	public List<WeixinTagsVO> list() {
		WeixinJson wj = weixinNW.tags_get(getAccessToken());
		if (wj.isSuccess()) {
			String json = wj.getJson();
			;
			JSONArray jsonArray = JSONArray.fromObject(JSONObject.fromObject(json).get("tags"));
			return (List<WeixinTagsVO>) JSONArray.toCollection(jsonArray, WeixinTagsVO.class);
		}
		return new ArrayList<>();
	}
	
	@Override
	public void update(Integer id, String name) {
		weixinNW.tags_update(getAccessToken(), id, name);
	}
	
	@Override
	public void remove(Integer id) {
		weixinNW.tags_delete(getAccessToken(), id);
	}
	
	@Override
	public void batchuntagging(List<String> openids, Integer tagid) {
		weixinNW.tag_members_batchuntagging(getAccessToken(), tagid, openids);
	}
	
	@Override
	public void batchtagging(List<String> openids, Integer tagid) {
		weixinNW.tag_members_batchtagging(getAccessToken(), tagid, openids);
	}
}
