package ch.intelligentworks.lib.caching.aspect;

import ch.intelligentworks.lib.caching.aspect.markers.Cached;
import ch.intelligentworks.lib.caching.cache.CacheStore;
import ch.intelligentworks.lib.caching.cache.YOSCacheManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Aspect
@Configuration
@Slf4j
public class CacheManagementAspect {

  @Autowired
  YOSCacheManager cm;

  @Around("@annotation(ch.intelligentworks.lib.caching.aspect.markers.Cached)" )
  public Object use(ProceedingJoinPoint joinPoint) throws Throwable {

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Cached annotation = signature.getMethod().getAnnotation(Cached.class);
    String keyDef = annotation.keyDef();
    PropertyFilter propertyFilter = new PropertyFilter(annotation.filterBy()).parse(joinPoint);
    log.debug("Cache layer has been accessed! {}", annotation.operation());
    CacheStore store = cm.getCache(annotation.cache());
    Object result = null;

    switch(annotation.operation()){
      case GET:
        if(store.isEmpty()){
          result = joinPoint.proceed();
          if(result instanceof List){
            ((List) result).forEach(p -> store.add(findKeyByKeyDef(keyDef, p), p));
          }else{
            return result; // todo: may be from the list?
          }
        }

        return store.stream()
                .filter( p -> propertyFilter.evaluate(p))
                .collect(Collectors.toList());
      case ADD:
      case UPDATE:
        result = joinPoint.proceed();
        store.add(findKeyByKeyDef(keyDef, result), result);
        break;
      case REMOVE:
        result = joinPoint.proceed();
        store.remove(findKeyByKeyDef(keyDef, result));
        break;
      case EVICT:
        joinPoint.proceed();
        store.empty();
        break;
    }

    log.debug("Cache layer just has been used! {}", annotation.operation());
    return result;
  }

  private String findKeyByKeyDef(String keyDef, Object target){
    try {
      return String.valueOf(PropertyFilter.findFieldValue(target, keyDef));
    } catch (NoSuchFieldException | IllegalAccessException e) {
      log.error("can not read property '{}' form the class!", keyDef);
    }
    return null;
  }


  @Getter
  static class PropertyFilter {
    final static Pattern filterDefPattern = Pattern.compile("(.*)\\s*=\\s*(.*)");

    String filter;
    String leftSidePropertyName;
    String rightSidePropertyName;
    String rightSideArgumentName;
    Object rightSideArgumentValue;

    PropertyFilter(String filter){
      this.filter = filter;
    }

    public PropertyFilter parse(JoinPoint joinPoint) throws NoSuchFieldException, IllegalAccessException {
      Matcher m = filterDefPattern.matcher(filter);
      if (m.find()) {
        this.leftSidePropertyName = m.group(1);
        String rightSide = m.group(2);
        if (rightSide.contains(".")) {
          this.rightSideArgumentName = rightSide.split(".")[0];
          this.rightSidePropertyName = rightSide.split(".")[1];
        } else {
          this.rightSideArgumentName = rightSide;
        }
      }

      this.rightSideArgumentValue = findArgumentValue(joinPoint, this.rightSideArgumentName);
      if (this.rightSidePropertyName!=null) {
        this.rightSideArgumentValue = findFieldValue(this.rightSideArgumentValue, this.rightSidePropertyName);
      }

      return this;
    }

    private Object findArgumentValue(JoinPoint joinPoint, String argName) {
      String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
      Object[] values = joinPoint.getArgs();

      if (argNames.length != 0) {
        for (int i = 0; i < argNames.length; i++) {
          if (argNames[i].equalsIgnoreCase(argName))
            return values[i];
        }
      }
      return null;
    }

    public Boolean evaluate(Object target) {
      if(StringUtils.isEmpty(this.filter)){
        return true;
      }

      try {
        Object targetValue = findFieldValue(target, this.leftSidePropertyName);
        return targetValue.getClass().isPrimitive() ?
                targetValue == rightSideArgumentValue
                  : targetValue.equals(rightSideArgumentValue);
      } catch (NullPointerException | NoSuchFieldException | IllegalAccessException e) {
        log.error("can not read property form the class!");
      }
      return false;
    }

    private static Object findFieldValue(Object target, String FieldName) throws NoSuchFieldException, IllegalAccessException {
      Field field = target.getClass().getDeclaredField(FieldName);
      field.setAccessible(true);
      return field.get(target);
    }
  }

}
