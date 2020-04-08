package utils.aoptools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 切面相关参数处理
 * @author: Fearon
 * @create: 2018/9/4 9:43
 **/
public class ParamsHandle {
    /**
     * 从切面所有map中获取指定参数
     * @param target_params
     * @param key
     * @return
     */
    public static Object getSingleInParamsMap(Object[] target_params, String key){
        Map params;
        Object result = null;
        for (Object target_param : target_params) {
            if(!(target_param instanceof Map))
                continue;

            params = (HashMap) target_param;
            result = params.get(key);
        }
        return result;
    }

    /**
     * 获取切面方法参数中所有的map
     * @param target_params
     * @return
     */
    public static List<Map> getMapInParams(Object[] target_params){
        List<Map> result = new ArrayList<>();
        Map params;
        for (Object target_param : target_params) {
            if(!(target_param instanceof Map))
                continue;

            params = (HashMap) target_param;
            result.add(params);
        }
        return result;
    }
}
