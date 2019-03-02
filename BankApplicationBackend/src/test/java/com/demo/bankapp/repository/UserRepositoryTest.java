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

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	public void test() {
		
		int initialCountOfUsers = repository.findAll().size();
		
		// findAll
		User firstUser = new User("Mert15", "mert123", "12453561256");
		entityManager.persist(firstUser);
		entityManager.flush();

		User secondUser = new User("Mert12", "mert125", "12455561256");
		entityManager.persist(secondUser);
		entityManager.flush();

		List<User> users = repository.findAll();

		assertThat(users.size()).isEqualTo(initialCountOfUsers + 2);
		assertThat(users.get(initialCountOfUsers + 0)).isEqualTo(firstUser);
		assertThat(users.get(initialCountOfUsers + 1)).isEqualTo(secondUser);
		
		// save
		User userToSave = new User("Saved_Mert", "mert1235", "12453571256");
		User savedUser = repository.save(userToSave);
		
		assertThat(repository.findAll().size()).isEqualTo(initialCountOfUsers + 3);
		assertThat(userToSave.getUsername()).isEqualTo(savedUser.getUsername());
		assertThat(userToSave.getTcno()).isEqualTo(savedUser.getTcno());
		
		// findByUsername
		User foundByUsername = repository.findByUsername(firstUser.getUsername());
		assertThat(firstUser.getUsername()).isEqualTo(foundByUsername.getUsername());
		assertThat(firstUser.getPassword()).isEqualTo(foundByUsername.getPassword());
		assertThat(firstUser.getTcno()).isEqualTo(foundByUsername.getTcno());
		
		// findByTcno
		User foundByTcno = repository.findByTcno(firstUser.getTcno());
		assertThat(firstUser.getUsername()).isEqualTo(foundByTcno.getUsername());
		assertThat(firstUser.getPassword()).isEqualTo(foundByTcno.getPassword());
		assertThat(firstUser.getTcno()).isEqualTo(foundByTcno.getTcno());
	}

}
