package utils.json;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @description: json 数据处理工具
 * @author: Fearon
 * @create: 2018/8/4 15:59
 **/
public class JsonTool {
	/**
	 * json转map
	 * @param json
	 * @return
	 */
	public static Map jsonToMap(String json){
		return JSONObject.parseObject(json, Map.class);
	}
}