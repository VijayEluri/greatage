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

package org.greatage.ioc.logging;

import org.greatage.util.DescriptionBuilder;

/**
 * This class represents abstract logger source implementation that delegates creation of logger by class to creation by
 * class name.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public abstract class AbstractLoggerSource implements LoggerSource {

	/**
	 * {@inheritDoc} Delegates invocation to {@link #getLogger(String)} method.
	 */
	public Logger getLogger(final Class clazz) {
		return getLogger(clazz.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return new DescriptionBuilder(getClass()).toString();
	}
}
