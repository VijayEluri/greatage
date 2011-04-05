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

package org.greatage.ioc;

import org.greatage.ioc.scope.Scope;

/**
 * This class represents specific information about service inside the IoC container.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface ServiceProvider<T> {

	Marker<T> getMarker();

	/**
	 * Gets service scope. {@link org.greatage.ioc.scope.ScopeManager} service must be configured to understand this value
	 * of scope.
	 *
	 * @return service scope, not null
	 */
	Scope getScope();

	/**
	 * Retrieves service instance. It returns the service's proxy that implements the same interface as the actual service
	 * and instantiates the actual service only as needed with all configured method advices.
	 *
	 * @return service instance
	 */
	T getService();
}
