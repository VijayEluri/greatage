package org.greatage.ioc.services;

import org.greatage.ioc.ServiceResources;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Scope {

	<E> E get(ServiceResources<E> resources, ObjectBuilder<E> builder);

}
