package com.zdc.spscan.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//注意用这个注解才能在运行时使用反射
@Target({ElementType.TYPE})
@Documented
public @interface MyService {
	String name() default "";
}
