package com.demo.bankapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.bankapp.model.Wealth;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class WealthRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private WealthRepository repository;
	
	@Test
	public void test() {
		int initialCountOfWealth = repository.findAll().size();
		
		Wealth firstWealth = new Wealth(null, new HashMap<String, BigDecimal>());
		entityManager.persist(firstWealth);
		entityManager.flush();
		
		assertThat(repository.findAll().size()).isEqualTo(initialCountOfWealth + 1);

		Wealth wealthToSave = new Wealth(null, new HashMap<String, BigDecimal>());
		Wealth savedWealth = repository.save(wealthToSave);
		
		assertThat(repository.findAll().size()).isEqualTo(initialCountOfWealth + 2);
		assertThat(savedWealth.getUserId()).isNotNull();
		
		assertThat(repository.findById(savedWealth.getUserId())).isNotNull();
	}

}
