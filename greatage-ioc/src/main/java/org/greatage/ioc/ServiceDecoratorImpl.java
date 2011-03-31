/*
 * Copyright (c) 2008-2011 Ivan Khalopik.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.greatage.ioc;

import org.greatage.ioc.annotations.Decorate;
import org.greatage.ioc.annotations.Order;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents default implementation of service interceptor definition that distributively configures service
 * method advices. It is based on intercepting service by invoking module method.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceDecoratorImpl<T> implements ServiceDecorator<T> {
	private final Class<?> moduleClass;
	private final Method decorateMethod;

	private final Class<T> serviceClass;
	private final String serviceId;

	private final String orderId;
	private final List<String> orderConstraints;

	private final Logger logger;

	/**
	 * Creates new instance of service interceptor definition with defined module class and method used for service
	 * interception. Interception method must have {@link org.greatage.ioc.proxy.Interceptor} return type and be annotated
	 * with {@link org.greatage.ioc.annotations.Decorate} annotation. For interceptors ordering it may also be annotated
	 * with {@link Order} annotation.
	 *
	 * @param logger		 system logger
	 * @param moduleClass	module class
	 * @param decorateMethod module method used for service interception
	 */
	ServiceDecoratorImpl(final Logger logger, final Class<?> moduleClass, final Method decorateMethod) {
		this.logger = logger;
		this.moduleClass = moduleClass;
		this.decorateMethod = decorateMethod;

		final Decorate decorate = decorateMethod.getAnnotation(Decorate.class);
		serviceId = InternalUtils.generateServiceId(decorate.value(), decorate.id());
		//noinspection unchecked
		serviceClass = decorate.service();

		final Order order = decorateMethod.getAnnotation(Order.class);
		if (order != null) {
			orderId = order.value();
			orderConstraints = Arrays.asList(order.constraints());
		} else {
			orderId = "";
			orderConstraints = CollectionUtils.newList();
		}
	}

	/**
	 * {@inheritDoc} This decorator supports service if its service identifier or service class correspond to configured
	 * ones.
	 */
	public boolean supports(final Service service) {
		return serviceId != null ?
				service.getServiceId().equals(serviceId) :
				serviceClass.isAssignableFrom(service.getServiceClass());
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getOrderConstraints() {
		return orderConstraints;
	}

	/**
	 * {@inheritDoc} It intercepts service by invoking configured module method.
	 */
	public Interceptor decorate(final ServiceResources<T> resources) {
		logger.info("Decorate service (%s, %s) from module (%s, %s)", resources.getServiceId(),
				resources.getServiceClass(), moduleClass, decorateMethod);

		try {
			final Object moduleInstance =
					Modifier.isStatic(decorateMethod.getModifiers()) ? null : resources.getResource(moduleClass);
			final Object[] parameters = InternalUtils.calculateParameters(resources, decorateMethod);
			return (Interceptor) decorateMethod.invoke(moduleInstance, parameters);
		} catch (Exception e) {
			throw new ApplicationException(
					String.format("Can't create service decorator with id '%s'", resources.getServiceId()), e);
		}
	}
}