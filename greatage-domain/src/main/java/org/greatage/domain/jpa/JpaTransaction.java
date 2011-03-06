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

package org.greatage.domain.jpa;

import org.greatage.domain.Transaction;

import javax.persistence.EntityTransaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JpaTransaction implements Transaction {
	private final EntityTransaction transaction;

	public JpaTransaction(final EntityTransaction transaction) {
		this.transaction = transaction;
	}

	public void commit() {
		transaction.commit();
	}

	public void rollback() {
		transaction.rollback();
	}
}
