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

package org.greatage.ioc.tapestry;

import org.apache.tapestry5.ioc.ObjectCreator;
import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.ServiceBuilderResources;
import org.apache.tapestry5.ioc.def.ServiceDef;
import org.greatage.ioc.Marker;
import org.greatage.ioc.ServiceLocator;
import org.greatage.ioc.annotations.Named;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class GreatAgeServiceDef implements ServiceDef {
	private final ServiceLocator locator;
	private final Marker<?> marker;
	private final String serviceId;

	GreatAgeServiceDef(final ServiceLocator locator, Marker<?> marker) {
		this.locator = locator;
		this.marker = marker;

		serviceId = calculateServiceId(marker);
	}

	private String calculateServiceId(final Marker<?> marker) {
		final StringBuilder builder = new StringBuilder(marker.getServiceClass().getName());
		final Annotation annotation = marker.getQualifier();
		if (annotation != null) {
			if (annotation instanceof Named) {
				return ((Named) annotation).value();
			}
			builder.append("@");
			builder.append(annotation.annotationType().getSimpleName());
		}
		return builder.toString();
	}

	public ObjectCreator createServiceCreator(final ServiceBuilderResources resources) {
		return new ObjectCreator() {
			public Object createObject() {
				return locator.getService(marker);
			}
		};
	}

	public String getServiceId() {
		return serviceId;
	}

	public Set<Class> getMarkers() {
		return Collections.emptySet();
	}

	public Class getServiceInterface() {
		return marker.getServiceClass();
	}

	public String getServiceScope() {
		return ScopeConstants.DEFAULT;
	}

	public boolean isEagerLoad() {
		return false;
	}
}
