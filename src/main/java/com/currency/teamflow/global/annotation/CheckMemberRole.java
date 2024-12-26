package com.currency.teamflow.global.annotation;

import com.currency.teamflow.global.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckMemberRole {
    Role[] requiredRoles() default {}; // 필요한 멤버 역할
}

