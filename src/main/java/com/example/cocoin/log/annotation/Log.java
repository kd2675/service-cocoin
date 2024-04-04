package com.example.cocoin.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME) // 런타임에도 어노테이션 정보가 유지되어야 함
@Target(ElementType.METHOD) // 메소드에만 적용되는 어노테이션
public @interface Log {
}
