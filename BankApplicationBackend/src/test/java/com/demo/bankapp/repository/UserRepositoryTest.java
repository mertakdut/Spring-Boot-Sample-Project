package com.demo.bankapp.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.demo.bankapp.model.User;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

//	@Autowired
//	private TestEntityManager entityManager;
//
//	@Autowired
//	private UserRepository arrivalRepository;
//
//	@Test
//	public void whenFindAll() {
//		// given
//		User firstUser = new User("Mert", "mert123", "12453561256");
//		entityManager.persist(firstUser);
//		entityManager.flush();
//
//		User secondUser = new User("Mert2", "mert125", "12455561256");
//		entityManager.persist(secondUser);
//		entityManager.flush();
//
//		// when
//		List<User> arrivals = arrivalRepository.findAll();
//
//		// then
//		assertThat(arrivals.size()).isEqualTo(2);
//		assertThat(arrivals.get(0)).isEqualTo(firstUser);
//		assertThat(arrivals.get(1)).isEqualTo(secondUser);
//	}

}
