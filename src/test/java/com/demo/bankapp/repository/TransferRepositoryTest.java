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

import com.demo.bankapp.model.Transfer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TransferRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TransferRepository repository;
	
	@Test
	public void test() {
		Transfer firstWealth = new Transfer(255526L, 521125L, "USD", BigDecimal.valueOf(251266));
		entityManager.persist(firstWealth);
		entityManager.flush();
		
		assertThat(repository.findAll().size()).isEqualTo(1);
		
		// save
		Transfer transferToSave = new Transfer(2556L, 5125L, "USD", BigDecimal.valueOf(2516));
		Transfer savedTransfer = repository.save(transferToSave);
		
		assertThat(repository.findAll().size()).isEqualTo(2);
		assertThat(savedTransfer.getFromUserId()).isEqualTo(transferToSave.getFromUserId());
		assertThat(savedTransfer.getToUserId()).isEqualTo(transferToSave.getToUserId());
		assertThat(savedTransfer.getCurrency()).isEqualTo(transferToSave.getCurrency());
		assertThat(savedTransfer.getAmount()).isEqualTo(transferToSave.getAmount());
		
		// findAllTransfersFrom24Hours
		List<Transfer> transferList = repository.findAllTransfersFrom24Hours(savedTransfer.getFromUserId());
		assertThat(transferList.size()).isEqualTo(1);
	}

}
