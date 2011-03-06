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

package org.greatage.ioc.access;

/**
 * @author Ivan Khalopik
 * @since 1.1
 */
public class MockObject {
	private final String message;
	private int type = 10;

	public MockObject(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getType() {
		return type;
	}

	public void setType(final int type) {
		this.type = type;
	}
}
