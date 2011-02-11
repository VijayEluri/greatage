/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This annotation marks methods inside the IoC module as bind methods that binds service interfaces to their
 * automatically built implementations with specified binding options. Such bind method must be static and have one
 * argument of type {@link org.greatage.ioc.ServiceBinder}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Bind {
}