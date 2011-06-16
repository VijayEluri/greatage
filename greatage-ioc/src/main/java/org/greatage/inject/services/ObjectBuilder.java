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

package org.greatage.inject.services;

import org.greatage.inject.Interceptor;

/**
 * This interface represents proxy generation data.
 *
 * @param <T> type of class that will be created by {@link #build()} method
 * @author Ivan Khalopik
 * @since 1.0
 */
public interface ObjectBuilder<T> {

	/**
	 * Gets proxy interface that will be implemented with both proxy and real object classes.
	 *
	 * @return proxy interface
	 */
	Class<T> getObjectClass();

	Interceptor getInterceptor();

	/**
	 * Builds real object. It is used for lazy creation of real object under the proxy.
	 *
	 * @return new instance of real object
	 */
	T build();
}
