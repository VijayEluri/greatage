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

package org.greatage.domain.objectify;

import com.google.appengine.api.datastore.Transaction;
import com.googlecode.objectify.Objectify;
import org.greatage.domain.SessionCallback;
import org.greatage.domain.TransactionCallback;
import org.greatage.domain.TransactionExecutor;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class ObjectifyExecutor implements TransactionExecutor<Transaction, Objectify> {
	private final Objectify objectify;

	public ObjectifyExecutor(final Objectify objectify) {
		this.objectify = objectify;
	}

	public <V> V execute(final TransactionCallback<V, Transaction> callback) {
		Transaction transaction = null;
		try {
			transaction = objectify.getTxn();
			final V result = callback.doInTransaction(transaction);
			transaction.commit();
			return result;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	public <V> V execute(final SessionCallback<V, Objectify> callback) {
		try {
			return callback.doInSession(objectify);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
