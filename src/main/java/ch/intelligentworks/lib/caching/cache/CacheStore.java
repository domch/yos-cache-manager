package ch.intelligentworks.lib.caching.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * PromptContainer objects are cached and managed by this container
 * Created on 28.11.2019 17:43
 *
 * @author mdogan
 * @version 1.0
 */
public class CacheStore<T> {
  private ConcurrentHashMap<String, T> store = new ConcurrentHashMap<>();

  public void add(String key, T t) {
    store.put(key, t);
  }

  public void remove(String key){
    store.remove(key);
  }

  public void empty(){
    store.clear();
  }

  public T get(String key){return store.get(key);}

  public Stream stream(){
    return store.values().stream();
  }

  public boolean isEmpty(){
    return store.isEmpty();
  }
}
