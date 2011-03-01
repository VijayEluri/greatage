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

import org.slf4j.LoggerFactory;

/**
 * This class represents logger source implementation through Slf4j logging API.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class Slf4jLoggerSource extends AbstractLoggerSource {

	/**
	 * {@inheritDoc} Creates {@link Slf4jLogger} instance.
	 */
	public Logger getLogger(final String name) {
		return new Slf4jLogger(LoggerFactory.getLogger(name));
	}
}
