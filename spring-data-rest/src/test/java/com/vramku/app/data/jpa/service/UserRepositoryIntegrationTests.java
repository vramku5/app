/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vramku.app.data.jpa.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vramku.app.data.jpa.SampleDataRestApplication;
import com.vramku.app.data.jpa.domain.User;
import com.vramku.app.data.jpa.service.UserRepository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration tests for {@link UserRepository}.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SampleDataRestApplication.class)
public class UserRepositoryIntegrationTests {

	@Autowired
	UserRepository repository;

	@Test
	public void findsFirstPageOfUsers() {

		Page<User> users = this.repository.findAll(new PageRequest(0, 10));
		assertThat(users.getTotalElements(), is(greaterThan(0L)));
	}

	@Test
	public void findByNameAndCountry() {
		User user = this.repository.findByFirstNameAndLastNameAllIgnoringCase("Karthik",
				"Ramkumar");
		assertThat(user, notNullValue());
		assertThat(user.getFirstName(), is(equalTo("Karthik")));
	}

	@Test
	public void findContaining() {
		Page<User> users = this.repository
				.findByFirstNameContainingAndLastNameContainingAllIgnoringCase("Kar", "Ram",
						new PageRequest(0, 10));
		assertThat(users.getTotalElements(), is(equalTo(1L)));
	}
}
