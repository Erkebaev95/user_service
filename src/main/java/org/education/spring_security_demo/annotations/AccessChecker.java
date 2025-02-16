package org.education.spring_security_demo.annotations;

import org.education.spring_security_demo.enums.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessChecker {
    Role[] acceptRoles() default {};
    Role[] rejectRoles() default {};
    boolean anonymous() default false;
    boolean admin() default false;
}
