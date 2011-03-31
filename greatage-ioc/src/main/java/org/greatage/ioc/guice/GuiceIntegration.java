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

package org.greatage.ioc.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.greatage.ioc.Module;
import org.greatage.ioc.Service;
import org.greatage.ioc.ServiceContributor;
import org.greatage.ioc.ServiceDecorator;
import org.greatage.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class GuiceIntegration implements Module {
	private final Set<Service> services = CollectionUtils.newSet();

	public GuiceIntegration(final com.google.inject.Module... modules) {
		this(Guice.createInjector(modules));
	}

	@SuppressWarnings("unchecked")
	public GuiceIntegration(final Injector injector) {
		for (Key<?> key : injector.getBindings().keySet()) {
			services.add(new GuiceService(injector, key));
		}
	}

	public Collection<Service> getServices() {
		return services;
	}

	public <T> List<ServiceContributor<T>> getContributors(final Service<T> service) {
		return CollectionUtils.newList();
	}

	public <T> List<ServiceDecorator<T>> getDecorators(final Service<T> service) {
		return CollectionUtils.newList();
	}
}