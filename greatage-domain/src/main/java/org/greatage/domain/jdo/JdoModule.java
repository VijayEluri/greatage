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

package org.greatage.domain.jdo;

import org.greatage.domain.BaseFilterProcessor;
import org.greatage.domain.CompositeFilterProcessor;
import org.greatage.domain.EntityFilterProcessor;
import org.greatage.domain.EntityRepository;
import org.greatage.inject.Configuration;
import org.greatage.inject.ServiceBinder;
import org.greatage.inject.annotations.Bind;
import org.greatage.inject.annotations.Build;
import org.greatage.inject.annotations.Contribute;
import org.greatage.inject.annotations.Threaded;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
import java.util.Map;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JdoModule {

	@Bind
	public static void bind(final ServiceBinder binder) {
		binder.bind(EntityRepository.class, JdoRepository.class);
		binder.bind(JdoExecutor.class, JdoExecutorImpl.class).withScope(Threaded.class);
		binder.bind(EntityFilterProcessor.class, CompositeFilterProcessor.class);
	}

	@Build
	public PersistenceManagerFactory buildPersistenceManagerFactory(final Map<String, String> jdoConfiguration) {
		return JDOHelper.getPersistenceManagerFactory(jdoConfiguration);
	}

	@Contribute(EntityFilterProcessor.class)
	public void contributeEntityFilterProcessor(final Configuration<EntityFilterProcessor> configuration) {
		configuration.addInstance(BaseFilterProcessor.class);
	}
}