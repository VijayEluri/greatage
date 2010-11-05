package org.greatage.ioc;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Module {

	List<Service> getServices();

	<T> List<Configurator<T>> getConfigurators(Service<T> service);

	<T> List<Decorator<T>> getDecorators(Service<T> service);

}
