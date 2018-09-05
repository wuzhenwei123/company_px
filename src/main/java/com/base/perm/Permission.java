package com.base.perm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 权限控制
 * @author zhanglibo
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
	/**
	 * 权限控制唯一标识
	 * @return
	 */
	String value();
}
