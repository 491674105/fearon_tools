package utils.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @description: 数据缓存工具
 * @author: Fearon
 * @create: 2018-04-14 13:46
 **/
public class CurrentDataCache {
    private static final ConcurrentMap<String, HashMap<String, Object>> CURRENT_CACHE_SINGLE = new ConcurrentHashMap<>();

    private static class SingleTonHeader{
        private final static CurrentDataCache SINGLE_TON_HEADER = new CurrentDataCache();
    }

    private CurrentDataCache(){}

    /**
     * 使用单例模式节省资源，以静态内部类进行登记保证线程安全
     */
    public static CurrentDataCache getDataCache(){
        return SingleTonHeader.SINGLE_TON_HEADER;
    }

    /**
     * 将数据装入缓存
     * @param cacheKey 缓存数据的KEY（类型：String）
     * @param cacheData 缓存数据
     * @return 当前缓存数据的KEY（类型：String）
     */
    public static boolean setCacheData(String cacheKey, Object cacheData) throws NullPointerException {
        if(cacheKey == null || cacheKey.trim().length() == 0)
            throw new NullPointerException("不可识别的键名 : cacheKey");
        if(cacheData == null)
            throw new NullPointerException("缓存数据不可为空！");

        // 删除旧缓存
        if(CurrentDataCache.CURRENT_CACHE_SINGLE.get(cacheKey) != null)
            CurrentDataCache.CURRENT_CACHE_SINGLE.remove(cacheKey);

        HashMap<String, Object> params = new HashMap<>();
        params.put("data", cacheData);
        params.put("time", new Date());
        CurrentDataCache.CURRENT_CACHE_SINGLE.put(cacheKey, params);

        // 缓存不为空则认为保存成功
        return !CurrentDataCache.CURRENT_CACHE_SINGLE.get(cacheKey).isEmpty();
    }

    /**
     * 获取对应缓存数据内容
     * @param cacheKey 缓存数据的KEY（类型：String）
     * @return 缓存数据内容
     */
    public static HashMap<String, Object> getCacheData(String cacheKey) throws NullPointerException {
        if(cacheKey == null || cacheKey.trim().length() == 0)
            throw new NullPointerException("不可识别的键名 : cacheKey");

        return CurrentDataCache.CURRENT_CACHE_SINGLE.get(cacheKey);
    }
}
