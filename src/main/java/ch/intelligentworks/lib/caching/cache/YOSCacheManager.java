package ch.intelligentworks.lib.caching.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Description...
 * Created on 29.11.2019 09:23
 *
 * @author mdogan
 * @version 1.0
 */
public class YOSCacheManager {
  private ConcurrentHashMap<String, CacheStore> cacheStores = new ConcurrentHashMap<>();

  public void addCache(String name, CacheStore cacheStore){
    cacheStores.putIfAbsent(name, cacheStore);
  }

  public CacheStore getCache(String name){
    return cacheStores.get(name);
  }
}
