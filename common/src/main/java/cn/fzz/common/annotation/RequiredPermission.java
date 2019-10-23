package cn.fzz.common.annotation;

import java.lang.annotation.*;

/**
 * Created by Andy on 2019/1/14.
 * Desc:
 *      ElementType.TYPE、ElementType.METHOD表示注解可以标记类和方法
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    String value();
}
