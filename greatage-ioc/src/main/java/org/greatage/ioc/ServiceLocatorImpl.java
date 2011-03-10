/*
 * Copyright 2011 Ivan Khalopik
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

import org.greatage.ioc.coerce.TypeCoercer;
import org.greatage.ioc.logging.Logger;
import org.greatage.ioc.logging.LoggerSource;
import org.greatage.ioc.proxy.ProxyFactory;
import org.greatage.ioc.scope.ScopeManager;
import org.greatage.util.CollectionUtils;
import org.greatage.util.OrderingUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents default {@link ServiceLocator} implementation that is used as main entry point of Great Age IoC
 * container.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorImpl implements ServiceLocator {
	private final Map<String, ServiceProvider<?>> servicesById = CollectionUtils.newConcurrentMap();
	private final Set<Class<?>> internalServices = CollectionUtils.newSet();
	private final Logger logger;

	/**
	 * Creates new instance of service locator with defined modules.
	 *
	 * @param logger  system logger
	 * @param modules modules
	 */
	ServiceLocatorImpl(final Logger logger, final List<Module> modules) {
		this.logger = logger;

		//TODO: implement this using set
		final Map<String, Service<?>> services = CollectionUtils.newMap();
		for (Module module : modules) {
			for (Service service : module.getServices()) {
				final String serviceId = service.getServiceId();
				if (services.containsKey(serviceId) && !service.isOverride()) {
					throw new ApplicationException(String.format("Service with id '%s' already declared", serviceId));
				}
				services.put(serviceId, service);
			}
		}

		// initializing internal services collection
		internalServices.add(LoggerSource.class);
		internalServices.add(ProxyFactory.class);
		internalServices.add(ScopeManager.class);
		internalServices.add(TypeCoercer.class);

		final ServiceProvider<ServiceLocator> serviceLocatorProvider = new ServiceLocatorProvider(this);
		servicesById.put(serviceLocatorProvider.getServiceId(), serviceLocatorProvider);

		for (Service<?> service : services.values()) {
			final ServiceProvider<?> provider = createServiceStatus(service, modules);
			servicesById.put(provider.getServiceId(), provider);
		}

		// building statistics for log
		logStatistics();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<String> getServiceIds() {
		return servicesById.keySet();
	}

	/**
	 * {@inheritDoc}
	 */
	public ServiceProvider<?> getServiceStatus(final String id) {
		return servicesById.get(id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final String id, final Class<T> serviceClass) {
		if (servicesById.containsKey(id)) {
			final ServiceProvider<?> provider = servicesById.get(id);
			final Object service = provider.getService();
			return serviceClass.cast(service);
		}
		throw new ApplicationException(String.format("Can't find service with id %s", id));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws ApplicationException if service not found
	 */
	public <T> T getService(final Class<T> serviceClass) {
		final Set<T> services = findServices(serviceClass);
		if (services.size() == 1) {
			return services.iterator().next();
		}
		throw new ApplicationException(String.format("Can't find service of class %s", serviceClass));
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> Set<T> findServices(final Class<T> serviceClass) {
		final Set<T> result = CollectionUtils.newSet();
		for (ServiceProvider<?> serviceProvider : servicesById.values()) {
			if (serviceClass.isAssignableFrom(serviceProvider.getServiceClass())) {
				final Object service = serviceProvider.getService();
				result.add(serviceClass.cast(service));
			}
		}
		return result;
	}

	/**
	 * Creates {@link ServiceProvider} instance for specified service with defined sorted service contributors, decorators
	 * and interceptors.
	 *
	 * @param service service definition
	 * @param modules module definitions
	 * @return service status instance, not null
	 */
	@SuppressWarnings("unchecked")
	private ServiceProvider<?> createServiceStatus(final Service<?> service,
												   final Collection<Module> modules) {
		final List<ServiceContributor<?>> contributors = CollectionUtils.newList();
		final List<ServiceDecorator<?>> decorators = CollectionUtils.newList();
		for (Module module : modules) {
			contributors.addAll(module.getContributors(service));
			decorators.addAll(module.getDecorators(service));
		}

		final List<ServiceContributor<?>> orderedContributors = OrderingUtils.order(contributors);
		final List<ServiceDecorator<?>> orderedDecorators = OrderingUtils.order(decorators);

		return isInternal(service) ?
				new InternalServiceProvider(this, service, orderedContributors) :
				new ServiceProviderImpl(this, service, orderedContributors, orderedDecorators);
	}

	/**
	 * Checks if service definition is internal. Internal services are {@link ProxyFactory}, {@link LoggerSource}, {@link
	 * TypeCoercer} and {@link ScopeManager}.
	 *
	 * @param service service definition
	 * @return true if service is internal, false otherwise
	 */
	private boolean isInternal(final Service<?> service) {
		return internalServices.contains(service.getServiceClass());
	}

	private void logStatistics() {
		int maxLength = 0;
		for (String serviceId : getServiceIds()) {
			if (serviceId.length() > maxLength) {
				maxLength = serviceId.length();
			}
		}

		final String format = "%" + maxLength + "s[%s] : %s\n";
		for (ServiceProvider<?> provider : servicesById.values()) {
			logger.info(format, provider.getServiceId(), provider.getServiceScope(), provider.getServiceClass());
		}
	}
}
