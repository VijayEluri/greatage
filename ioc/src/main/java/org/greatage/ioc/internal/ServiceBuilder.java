package org.greatage.ioc.internal;

import org.greatage.ioc.Configurator;
import org.greatage.ioc.Decorator;
import org.greatage.ioc.Service;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.services.ObjectBuilder;
import org.greatage.util.OrderingUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBuilder<T> implements ObjectBuilder<T> {
	private final Service<T> service;
	private final ServiceResources<T> resources;
	private final List<Configurator<T>> configurators;
	private final List<Decorator<T>> decorators;

	ServiceBuilder(final Service<T> service,
				   final ServiceResources<T> resources,
				   final List<Configurator<T>> configurators,
				   final List<Decorator<T>> decorators) {
		this.service = service;
		this.resources = resources;
		this.configurators = configurators;
		this.decorators = decorators;
	}

	public Class<T> getObjectClass() {
		return service.getServiceClass();
	}

	public T build() {
		final ServiceResources<T> buildResources = new ServiceBuildResources<T>(resources, configurators);
		final T serviceInstance = service.build(buildResources);
		return decorateService(serviceInstance);
	}

	private T decorateService(final T service) {
		final List<Decorator<T>> ordered = OrderingUtils.order(decorators);
		T decoratedService = service;
		for (Decorator<T> decorator : ordered) {
			final ServiceDecorateResources<T> decorateResources = new ServiceDecorateResources<T>(resources, decoratedService);
			decoratedService = decorator.decorate(decorateResources);
			if (decoratedService == null || decoratedService.equals(decorateResources.getServiceInstance())) {
				throw new RuntimeException("Decorator returns the same instance");
			}
		}
		return decoratedService;
	}
}
