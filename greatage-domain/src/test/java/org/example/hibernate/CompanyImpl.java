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

package org.example.hibernate;

import org.example.model.Company;
import org.greatage.domain.internal.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "company")
public class CompanyImpl extends AbstractEntity<Long> implements Company {

	@Id
	@Column(name = "company_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "registered_at")
	private Date registeredAt;

	public CompanyImpl() {
	}

	public CompanyImpl(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(final Date registeredAt) {
		this.registeredAt = registeredAt;
	}
}
