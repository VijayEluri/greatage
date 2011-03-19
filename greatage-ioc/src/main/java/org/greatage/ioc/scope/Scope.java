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

package org.greatage.ioc.scope;

import org.greatage.ioc.ServiceResources;
import org.greatage.ioc.proxy.ObjectBuilder;

/**
 * This interface represents service scope object that is used to obtain service instances according to their scope.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface Scope {

	/**
	 * Gets service by its resource. If service is missed in this scope new instance will be built via specified builder.
	 *
	 * @param resources service resources that identifies the service
	 * @param builder   service builder
	 * @param <E>       type of service
	 * @return service instance bound to this scope
	 */
	<E> E get(ServiceResources<E> resources, ObjectBuilder<E> builder);

	/**
	 * Close scope before end of lifetime.
	 */
	void cleanup();
}
