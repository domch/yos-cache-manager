package ch.intelligentworks.lib.caching.service;

import ch.intelligentworks.lib.caching.aspect.markers.Cached;
import ch.intelligentworks.lib.caching.model.DomainObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


@Service
@Slf4j
public class DomainObjectService {

  final static Function repo = (p) -> Arrays.asList(new DomainObject(), new DomainObject());

  @Cached(cache = "DomainObjects", keyDef = "id")
  public List<DomainObject> getDomainObjects() {
    return (List<DomainObject>) repo.apply(null);
  }

  @Cached(cache = "DomainObjects", keyDef = "id", filterBy = "name=domainName")
  public DomainObject getDomainObjectsByName(String domainName) {
    return new DomainObject();
  }

  @Cached(cache = "DomainObjects", keyDef = "id", operation = Cached.OperationType.ADD)
  public DomainObject addDomainObject(DomainObject DomainObject) {
   return new DomainObject();
  }

  @Cached(cache = "DomainObjects", keyDef = "id", operation = Cached.OperationType.UPDATE)
  public DomainObject updateDomainObject(DomainObject DomainObject) {
    return new DomainObject();
  }

  @Cached(cache = "DomainObjects", keyDef = "id", operation = Cached.OperationType.REMOVE)
  public void deleteDomainObject(DomainObject DomainObject) { }

  @Cached(cache = "DomainObjects", keyDef = "id", operation = Cached.OperationType.EVICT)
  public void clearCache() {
  }

}
