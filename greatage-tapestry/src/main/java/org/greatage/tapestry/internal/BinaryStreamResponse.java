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

package org.greatage.tapestry.internal;

import org.apache.tapestry5.ContentType;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.services.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ivan Khalopik
 */
public class BinaryStreamResponse implements StreamResponse {
	private final ContentType contentType;
	private final InputStream inputStream;

	public BinaryStreamResponse(String contentType, byte[] inputStream) {
		this(contentType, new ByteArrayInputStream(inputStream)); //todo: defence
	}

	public BinaryStreamResponse(String contentType, InputStream inputStream) {
		this.inputStream = inputStream;
		//todo: defence
//		Defense.notBlank(contentType, "contentType");
//		Defense.notNull(inputStream, "inputStream");
		this.contentType = new ContentType(contentType);
	}

	public String getContentType() {
		return contentType.toString();
	}

	public InputStream getStream() throws IOException {
		return inputStream;
	}

	public void prepareResponse(Response response) {
		//do nothing
	}
}
