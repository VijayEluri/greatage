/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain.jpa;

import org.greatage.domain.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class JpaExecutorImpl implements JpaExecutor {
	private final EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public JpaExecutorImpl(final EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public <T> T execute(final JpaCallback<T> callback) {
		try {
			final EntityManager entityManager = getEntityManager();
			return callback.doInJpa(entityManager);
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Throwable throwable) {
			throw new RuntimeException(throwable);
		}
	}

	private EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

	public Transaction begin() {
		final EntityTransaction transaction = getEntityManager().getTransaction();
		transaction.begin();
		return new JpaTransaction(transaction);
	}
}
