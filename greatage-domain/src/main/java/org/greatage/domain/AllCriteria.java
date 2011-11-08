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

package org.greatage.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
public class AllCriteria<PK extends Serializable, E extends Entity<PK>> implements Criteria<PK, E> {
	private boolean negative;

	public Criteria<PK, E> and(final Criteria<PK, E> criteria) {
		return new JunctionCriteria<PK, E>(JunctionCriteria.Operator.AND, junction(criteria));
	}

	public Criteria<PK, E> or(final Criteria<PK, E> criteria) {
		return new JunctionCriteria<PK, E>(JunctionCriteria.Operator.OR, junction(criteria));
	}

	public Criteria<PK, E> not() {
		negative = !negative;
		return this;
	}

	public boolean isNegative() {
		return negative;
	}

	private List<Criteria<PK, E>> junction(final Criteria<PK, E> criteria) {
		final List<Criteria<PK, E>> group = new ArrayList<Criteria<PK, E>>();
		group.add(this);
		group.add(criteria);
		return group;
	}

	@Override
	public String toString() {
		return negative ? "not 1=1" : "1=1";
	}
}