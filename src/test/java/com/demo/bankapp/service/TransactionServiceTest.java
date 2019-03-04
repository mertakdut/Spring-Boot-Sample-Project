package com.demo.bankapp.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.repository.TransactionRepository;
import com.demo.bankapp.service.concretions.TransactionService;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {
	
	@MockBean
	private TransactionRepository repository;
	
	private TransactionService service;
	
	@Before
	public void setUp() {
		service = new TransactionService(repository);
	}
	
	@Test
	public void createNewTransaction() {
		Transaction mockedTransaction = new Transaction(25681L, true, "TRY", BigDecimal.valueOf(61268));
		Mockito.when(repository.save(Mockito.any())).thenReturn(mockedTransaction);
		
		Transaction newTransaction = service.createNewTransaction(mockedTransaction.getUserId(), mockedTransaction.isBought(), mockedTransaction.getCurrency(), mockedTransaction.getAmount());
		
		assertThat(mockedTransaction.getAmount()).isEqualTo(newTransaction.getAmount());
		assertThat(mockedTransaction.getCurrency()).isEqualTo(newTransaction.getCurrency());
		assertThat(mockedTransaction.getUserId()).isEqualTo(newTransaction.getUserId());
		assertThat(mockedTransaction.isBought()).isEqualTo(newTransaction.isBought());
	}
	
	@Test
	public void getOperationCountFromLast24Hours() {
		Mockito.when(repository.getOperationCountFromLast24Hours(Mockito.any())).thenReturn(20);
		
		int operationCount = service.getOperationCountFromLast24Hours(12161L);
		assertThat(operationCount).isPositive();
	}
	
	@Test
	public void findAllByUserId() {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction mockedTransaction = new Transaction(61682L, true, "EUR", BigDecimal.valueOf(12661));
		transactionList.add(mockedTransaction);
		
		Mockito.when(repository.findAllByUserId(Mockito.any())).thenReturn(transactionList);
		
		List<Transaction> foundTransactionList = service.findAllByUserId(mockedTransaction.getUserId());
		assertThat(foundTransactionList).isEqualTo(transactionList);
	}

}
