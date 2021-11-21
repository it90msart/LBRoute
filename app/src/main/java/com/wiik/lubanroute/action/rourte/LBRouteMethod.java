package com.wiik.lubanroute.action.rourte;

import android.os.Bundle;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})

@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LBRouteMethod {
    Class cls();
    String routeMethodName();
}
