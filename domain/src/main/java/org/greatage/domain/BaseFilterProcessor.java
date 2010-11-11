/*
 * Copyright 2000 - 2010 Ivan Khalopik. All Rights Reserved.
 */

package org.greatage.domain;

import org.greatage.util.DescriptionBuilder;

import java.io.Serializable;

/**
 * This class represents implementation if {@link EntityFilterProcessor} that processes existing, includeKeys and
 * excludeKeys filter parameters.
 *
 * @author Ivan Khalopik
 * @since 1.0
 */
public class BaseFilterProcessor implements EntityFilterProcessor {

	public <PK extends Serializable, E extends Entity<PK>>
	void process(final EntityCriteria criteria, final EntityFilter<PK, E> filter, final Pagination pagination) {
		processPagination(criteria, pagination);
		processFilter(criteria, filter);
	}

	protected void processPagination(final EntityCriteria criteria, final Pagination pagination) {
		criteria.setPagination(pagination);

		if (pagination.getSortConstraints() != null) {
			for (SortConstraint definition : pagination.getSortConstraints()) {
				processSort(criteria, definition);
			}
		}
	}

	protected void processSort(final EntityCriteria criteria, final SortConstraint sort) {
		final String property = sort.getProperty();
		criteria.getProperty(property).sort(sort.isAscending(), sort.isIgnoreCase());
	}

	protected <PK extends Serializable, E extends Entity<PK>>
	void processFilter(final EntityCriteria criteria, final EntityFilter<PK, E> filter) {
		if (filter.getIncludeKeys() != null) {
			criteria.add(criteria.getProperty(Entity.ID_PROPERTY).in(filter.getIncludeKeys()));
		}

		if (filter.getExcludeKeys() != null && !filter.getExcludeKeys().isEmpty()) {
			criteria.add(criteria.getProperty(Entity.ID_PROPERTY).in(filter.getExcludeKeys()).not());
		}
	}

	@Override
	public String toString() {
		final DescriptionBuilder builder = new DescriptionBuilder(getClass());
		return builder.toString();
	}
}