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

package org.greatage.ioc.resource;

import java.net.URL;
import java.util.Locale;

/**
 * This class represents {@link Resource} implementation that is based on application classpath.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ClasspathResource extends AbstractResource {
	private static final ClasspathResource ROOT = new ClasspathResource(null, "", null, null);

	public static ClasspathResource root() {
		return ROOT;
	}

	/**
	 * Creates new instance of classpath resource with defined location, parent resource, name and locale.
	 *
	 * @param location resource location, can be <code>null</code>
	 * @param name	 resource name, not <code>null</code>
	 * @param type,	can be <code>null</code>
	 * @param locale   resource locale, can be <code>null</code>
	 */
	public ClasspathResource(final String location, final String name, final String type, final Locale locale) {
		super(location, name, type, locale);
	}

	/**
	 * {@inheritDoc} Obtains URL using class loader.
	 */
	@Override
	protected URL toURL() {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader != null ?
				classLoader.getResource(getPath()) :
				ClassLoader.getSystemResource(getPath());
	}

	/**
	 * {@inheritDoc} Always creates classpath resources.
	 */
	@Override
	protected Resource createResource(final String path, final String name, final String type, final Locale locale) {
		return new ClasspathResource(path, name, type, locale);
	}
}
