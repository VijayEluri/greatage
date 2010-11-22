/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.Locker;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceBuildResources<T> extends ServiceAdditionalResources<T> {
	private final List<Contributor<T>> contributors;
	private final Locker locker = new Locker();

	ServiceBuildResources(final ServiceResources<T> delegate, final List<Contributor<T>> contributors) {
		super(delegate);
		this.contributors = contributors;
	}

	@Override
	public <E> E getAdditionalResource(final Class<E> resourceClass) {
		if (Map.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getMappedConfiguration());
		}
		if (List.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getOrderedConfiguration());
		}
		if (Collection.class.equals(resourceClass)) {
			locker.lock();
			return resourceClass.cast(getConfiguration());
		}
		return null;
	}

	private <K, V> Map<K, V> getMappedConfiguration() {
		final MappedConfigurationImpl<T, K, V> configuration = new MappedConfigurationImpl<T, K, V>(getDelegate());
		configure(configuration);
		return configuration.build();
	}

	private <V> List<V> getOrderedConfiguration() {
		final OrderedConfigurationImpl<T, V> configuration = new OrderedConfigurationImpl<T, V>(getDelegate());
		configure(configuration);
		return configuration.build();
	}

	private <V> Collection<V> getConfiguration() {
		final ConfigurationImpl<T, V> configuration = new ConfigurationImpl<T, V>(getDelegate());
		configure(configuration);
		return configuration.build();
	}

	private void configure(Object configuration) {
		final ServiceConfigureResources<T> resources = new ServiceConfigureResources<T>(getDelegate(), configuration);
		for (Contributor<T> contributor : contributors) {
			contributor.contribute(resources);
		}
	}
}
