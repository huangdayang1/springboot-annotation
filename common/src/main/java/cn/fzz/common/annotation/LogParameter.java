package cn.fzz.common.annotation;

import java.lang.annotation.*;

/**
 * Created by Andy on 2019/1/14.
 * Desc:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogParameter {
}
