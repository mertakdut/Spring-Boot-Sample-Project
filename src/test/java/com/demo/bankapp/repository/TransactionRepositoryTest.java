package com.demo.bankapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
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

import com.demo.bankapp.model.Transaction;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TransactionRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TransactionRepository repository;

	@Test
	public void test() {
		Transaction firstTransaction = new Transaction(2515L, true, "EUR", BigDecimal.TEN);
		entityManager.persist(firstTransaction);
		entityManager.flush();

		assertThat(repository.findAll().size()).isEqualTo(1);

		// save
		Transaction transactionToSave = new Transaction(162L, false, "USD", BigDecimal.valueOf(5126));
		Transaction savedTransfer = repository.save(transactionToSave);

		assertThat(repository.findAll().size()).isEqualTo(2);
		assertThat(savedTransfer.getAmount()).isEqualTo(transactionToSave.getAmount());
		assertThat(savedTransfer.getCurrency()).isEqualTo(transactionToSave.getCurrency());
		assertThat(savedTransfer.getUserId()).isEqualTo(transactionToSave.getUserId());
		assertThat(savedTransfer.getTransactionTime()).isEqualTo(transactionToSave.getTransactionTime());

		// getOperationCountFromLast24Hours
		int operationCount = repository.getOperationCountFromLast24Hours(transactionToSave.getUserId());
		assertThat(operationCount).isEqualTo(1);

		// findAllByUserId
		List<Transaction> byUserId = repository.findAllByUserId(transactionToSave.getUserId());
		assertThat(byUserId.size()).isEqualTo(1);
	}

}
