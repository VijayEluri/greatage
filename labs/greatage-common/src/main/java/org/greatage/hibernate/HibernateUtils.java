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

package org.greatage.hibernate;

import org.hibernate.proxy.HibernateProxy;

/**
 * @author Ivan Khalopik
 */
public abstract class HibernateUtils {

	/**
	 * Get the true, underlying class of a proxied persistent class.
	 *
	 * @param entityClass proxied persistent class
	 * @param <T>         type of entity
	 * @return true, underlying class of a proxied persistent class
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getRealClass(final Class<T> entityClass) {
		if (HibernateProxy.class.isAssignableFrom(entityClass)) {
			return (Class<T>) entityClass.getSuperclass();
		}
		return entityClass;
	}
}
