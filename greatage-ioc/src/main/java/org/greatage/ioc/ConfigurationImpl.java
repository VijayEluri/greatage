/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc;

import org.greatage.util.CollectionUtils;

import java.util.Collection;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ConfigurationImpl<T, V> extends AbstractConfiguration<T, V, Collection<V>> implements Configuration<V> {
	private final Collection<V> configuration = CollectionUtils.newSet();

	ConfigurationImpl(final ServiceResources<T> resources) {
		super(resources);
	}

	public Configuration<V> add(final V item) {
		configuration.add(item);
		return this;
	}

	public Configuration<V> addInstance(final Class<? extends V> clazz) {
		return add(newInstance(clazz));
	}

	public Collection<V> build() {
		return configuration;
	}
}