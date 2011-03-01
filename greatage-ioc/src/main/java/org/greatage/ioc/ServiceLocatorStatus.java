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

import org.greatage.ioc.scope.ScopeConstants;

/**
 * This class represents {@link ServiceStatus} implementation for {@link ServiceLocator} service. It always has hardly
 * specified service identifier, class, scope and instance.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ServiceLocatorStatus implements ServiceStatus<ServiceLocator> {
	private final ServiceLocator locator;

	/**
	 * Creates new instance of service locator status with defined service locator.
	 *
	 * @param locator service locator
	 */
	ServiceLocatorStatus(final ServiceLocator locator) {
		this.locator = locator;
	}

	/**
	 * {@inheritDoc} Always returns <tt>ServiceLocator</tt> service identifier.
	 */
	public String getServiceId() {
		return ServiceLocator.class.getName();
	}

	/**
	 * {@inheritDoc} Always returns <tt>ServiceLocator</tt> service class.
	 */
	public Class<ServiceLocator> getServiceClass() {
		return ServiceLocator.class;
	}

	/**
	 * {@inheritDoc} Always returns <tt>global</tt> service scope.
	 */
	public String getServiceScope() {
		return ScopeConstants.GLOBAL;
	}

	/**
	 * {@inheritDoc} Always returns <tt>ServiceLocator</tt> instance.
	 */
	public ServiceLocator getService() {
		return locator;
	}
}
