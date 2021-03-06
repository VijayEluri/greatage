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

package org.greatage.inject.internal.scope;

import org.greatage.inject.Marker;
import org.greatage.inject.annotations.Singleton;
import org.greatage.inject.services.ProxyFactory;
import org.greatage.inject.services.Scope;
import org.greatage.inject.services.ServiceBuilder;
import org.greatage.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * This class represents {@link org.greatage.inject.services.Scope} implementation that is used for services that have
 * the same state for whole application. Default scope identifier is {@link Singleton}.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class SingletonScope implements Scope {
	private final Map<Marker, Object> services = CollectionUtils.newMap();

	private final ProxyFactory proxyFactory;

	public SingletonScope(final ProxyFactory proxyFactory) {
		this.proxyFactory = proxyFactory;
	}

	public Class<? extends Annotation> getKey() {
		return Singleton.class;
	}

	public <T> T get(final Marker<T> marker) {
		return marker.getServiceClass().cast(services.get(marker));
	}

	public <T> void register(final ServiceBuilder<T> builder) {
		final Marker<T> marker = builder.getMarker();
		if (builder.eager()) {
			services.put(marker, builder.build());
		} else {
			final CachedBuilder<T> cachedBuilder = new CachedBuilder<T>(builder);
			final T proxy = proxyFactory.createProxy(cachedBuilder);
			services.put(marker, proxy);
		}
	}
}
