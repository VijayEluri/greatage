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

/**
 * This class represents service contribution definition that distributively configures services. By default it is configured by
 * module contribute methods annotated with {@link org.greatage.ioc.annotations.Contribute} class.
 *
 * @param <T> service type
 * @author Ivan Khalopik
 * @since 1.1
 */
public interface ServiceContributor<T> {

	Marker<T> getMarker();

	/**
	 * Contributes to service configuration using service resource. It manipulates with three types of service configuration: {@link
	 * org.greatage.ioc.Configuration}, {@link org.greatage.ioc.OrderedConfiguration} and {@link
	 * org.greatage.ioc.MappedConfiguration} and provides collection, list and map to service via {@link
	 * org.greatage.ioc.ServiceResources} respectively.
	 *
	 * @param resources service resources
	 * @throws ApplicationException if error occurs while configuring service
	 */
	void contribute(ServiceResources<T> resources);
}
