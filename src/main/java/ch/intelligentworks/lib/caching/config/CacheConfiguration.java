package ch.intelligentworks.lib.caching.config;

import ch.intelligentworks.lib.caching.cache.CacheStore;
import ch.intelligentworks.lib.caching.cache.YOSCacheManager;
import ch.intelligentworks.lib.caching.model.DomainObject;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
public class CacheConfiguration {

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    List<Cache> caches = new ArrayList<Cache>();
    cacheManager.setCaches(caches);
    return cacheManager;
  }

  @Bean
  public YOSCacheManager sccpCacheManager() {
    YOSCacheManager YOSCacheManager = new YOSCacheManager();
    YOSCacheManager.addCache("DomainObjects", new CacheStore<DomainObject>());
    return YOSCacheManager;
  }
}