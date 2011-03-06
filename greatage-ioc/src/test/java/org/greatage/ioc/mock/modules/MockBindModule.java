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

package org.greatage.ioc.mock.modules;

import org.greatage.ioc.scope.ScopeConstants;
import org.greatage.ioc.ServiceBinder;
import org.greatage.ioc.annotations.Bind;
import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.mock.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockBindModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(MockTalkServiceImpl1.class).withId("talkService1");
		binder.bind(MockTalkService.class, MockTalkServiceImpl.class).withId("talkService2");
	}

	@Build(scope = ScopeConstants.THREAD)
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}
}
