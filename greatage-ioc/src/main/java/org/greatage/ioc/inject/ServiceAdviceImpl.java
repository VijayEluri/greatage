package org.greatage.ioc.inject;

import org.greatage.ioc.ServiceAdvice;
import org.greatage.ioc.ServiceAdviceOptions;
import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.Interceptor;
import org.greatage.util.CollectionUtils;
import org.greatage.util.OrderingUtils;

import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 8.0
 */
public class ServiceAdviceImpl<T> extends AbstractConfiguration<T, Interceptor> implements ServiceAdvice {
	private final List<ServiceAdviceOptionsImpl> advices = CollectionUtils.newList();

	ServiceAdviceImpl(final ServiceResources<T> resources) {
		super(resources);
	}

	@Override
	protected Interceptor build() {
		final List<ServiceAdviceOptionsImpl> ordered = OrderingUtils.order(advices);
		final List<InterceptorHolder> interceptors = CollectionUtils.newList();
		for (ServiceAdviceOptionsImpl options : ordered) {
			interceptors.add(options.build());
		}
		return new CompositeInterceptor(interceptors);
	}

	public ServiceAdviceOptions add(final Interceptor interceptor,
									final String orderId,
									final String... constraints) {
		final ServiceAdviceOptionsImpl options = new ServiceAdviceOptionsImpl(interceptor, orderId, constraints);
		advices.add(options);
		return options;
	}

	public ServiceAdviceOptions addInstance(final Class<? extends Interceptor> interceptorClass,
											final String orderId,
											final String... constraints) {
		final Interceptor interceptor = newInstance(interceptorClass);
		return add(interceptor, orderId, constraints);
	}
}
