/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.ioc.mock.modules;

import org.greatage.ioc.annotations.Build;
import org.greatage.ioc.annotations.Decorate;
import org.greatage.ioc.annotations.Order;
import org.greatage.ioc.mock.*;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class MockDecorateModule {

	@Build
	public MockMessageService buildMessageService() {
		return new MockMessageServiceImpl("hello");
	}

	@Build
	public MockTalkService buildTalkService(final MockMessageService messageService) {
		return new MockTalkServiceImpl(messageService);
	}

	@Decorate(MockTalkService.class)
	@Order("first")
	public MockTalkService decorateTalkService(final MockTalkService talkService) {
		return new MockTalkServiceDelegate(talkService, "[", "]");
	}

	@Decorate(MockTalkService.class)
	@Order(value = "second", constraints = "after:first")
	public MockTalkService redecorateTalkService(final MockTalkService talkService) {
		return new MockTalkServiceDelegate(talkService, "{", "}");
	}
}
