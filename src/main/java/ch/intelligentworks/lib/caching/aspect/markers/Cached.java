package ch.intelligentworks.lib.caching.aspect.markers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 28.11.2019 17:36
 *
 * @author taadome3
 * @version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

  public String cache();

  public String keyDef() default "";

  public OperationType operation() default OperationType.GET;

  public String filterBy() default "";

  public enum OperationType {
    GET, ADD, REMOVE, EVICT, UPDATE
  }
}