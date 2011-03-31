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

import org.greatage.ioc.annotations.*;

import java.lang.annotation.Annotation;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class Marker<T> {
	private final Class<T> serviceClass;
	private final Class<? extends T> targetClass;
	private final Annotation annotation;

	Marker(final Class<T> serviceClass, final Class<? extends T> targetClass, final Annotation annotation) {
		this.serviceClass = serviceClass;
		this.targetClass = targetClass;
		this.annotation = annotation;
	}

	public Class<T> getServiceClass() {
		return serviceClass;
	}

	public Class<? extends T> getTargetClass() {
		return targetClass;
	}

	public Annotation getAnnotation() {
		return annotation;
	}

	public boolean isAssignableFrom(final Marker<?> marker) {
		if (!serviceClass.isAssignableFrom(marker.getServiceClass())) {
			return false;
		} else if (!targetClass.isAssignableFrom(marker.getTargetClass())) {
			return false;
		} else if (annotation != null && !annotation.equals(marker.getAnnotation())) {
			return false;
		}
		return true;
	}

	public static <T> Marker<T> generate(final Class<T> defaultClass, final Annotation... annotations) {
		final org.greatage.ioc.annotations.Service service = InternalUtils.findAnnotation(org.greatage.ioc.annotations.Service.class, annotations);
		final MarkerAnnotation marker = InternalUtils.findAnnotation(MarkerAnnotation.class, annotations);
		if (service == null) {
			final Class serviceClass = defaultClass != null ? defaultClass : Object.class;
			//noinspection unchecked
			return new Marker<T>(serviceClass, serviceClass, marker);
		}

		final Class serviceClass = !void.class.equals(service.service()) ? service.service() :
				defaultClass != null ? defaultClass :
						void.class.equals(service.value()) ? Object.class : service.value();

		final Class targetClass = void.class.equals(service.value()) ? serviceClass : service.value();

		if (!serviceClass.isAssignableFrom(targetClass)) {
			throw new IllegalArgumentException("Marker class should be subclassed from service class");
		}
		//noinspection unchecked
		return new Marker<T>(serviceClass, targetClass, marker);
	}
}