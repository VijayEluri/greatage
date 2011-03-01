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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ValueEncoder;
import org.greatage.util.I18nUtils;

import java.util.Locale;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class LocaleValueEncoder implements ValueEncoder<Locale> {

	public String toClient(final Locale value) {
		return value != null ? value.toString() : null;
	}

	public Locale toValue(final String clientValue) {
		return I18nUtils.getLocale(clientValue);
	}

}
