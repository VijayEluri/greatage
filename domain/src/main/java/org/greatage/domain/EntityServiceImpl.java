/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import org.greatage.domain.annotations.Transactional;
import org.greatage.util.DescriptionBuilder;
import org.greatage.util.ReflectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents default implementation of {@link EntityService}.
 *
 * @author Ivan Khalopik
 * @param <PK>       type of entities primary key
 * @param <E>        type of entities
 * @param <Q>        type of entities query
 * @since 1.0
 */
public class EntityServiceImpl<PK extends Serializable, E extends Entity<PK>, Q extends EntityQuery<PK, E, Q>>
		implements EntityService<PK, E> {

	private final EntityRepository repository;
	private final Class<E> entityClass;
	private final Class<Q> queryClass;
	private final String entityName;

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 */
	public EntityServiceImpl(final EntityRepository repository, final Class<E> entityClass) {
		this(repository, entityClass, null);
	}

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 * @param queryClass  entity filter class
	 */
	public EntityServiceImpl(final EntityRepository repository, final Class<E> entityClass, final Class<Q> queryClass) {
		this(repository, entityClass, queryClass, null);
	}

	/**
	 * Constructor with specified repository and entity class.
	 *
	 * @param repository  entity repository
	 * @param entityClass entity class
	 * @param queryClass  entity filter class
	 * @param entityName  entity name
	 */
	public EntityServiceImpl(final EntityRepository repository, final Class<E> entityClass, final Class<Q> queryClass, final String entityName) {
		this.repository = repository;
		this.entityClass = entityClass;
		this.queryClass = queryClass;
		this.entityName = entityName != null ? entityName : entityClass.getName();
	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public String getEntityName() {
		return entityName;
	}

	public E create() {
		return repository().create(getEntityClass());
	}

	@Transactional
	public void saveOrUpdate(final E entity) {
		if (entity.isNew()) {
			save(entity);
		} else {
			update(entity);
		}
	}

	@Transactional
	public void save(final E entity) {
		repository().save(entity);
	}

	@Transactional
	public void update(final E entity) {
		repository().update(entity);
	}

	@Transactional
	public void delete(final E entity) {
		repository().delete(entity);
	}

	public E get(final PK pk) {
		return repository().get(getEntityClass(), pk);
	}

	public int getEntitiesCount() {
		return createQuery().count();
	}

	public List<E> getEntities() {
		return createQuery().list(createDefaultPagination());
	}

	public List<E> getEntities(final Pagination pagination) {
		return createQuery().list(pagination);
	}

	/**
	 * Gets entity repository.
	 *
	 * @return entity repository
	 */
	protected EntityRepository repository() {
		return repository;
	}

	/**
	 * Gets default pagination for entities selection.
	 *
	 * @return default pagination for entities selection
	 */
	protected Pagination createDefaultPagination() {
		return Pagination.ALL;
	}

	/**
	 * Creates default filter for entities selection.
	 *
	 * @return default filter for entities selection
	 */
	protected Q createQuery() {
		return ReflectionUtils.newInstance(queryClass).assign(repository());
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		builder.append("class", entityClass);
		builder.append("name", entityName);
		return builder.toString();
	}
}