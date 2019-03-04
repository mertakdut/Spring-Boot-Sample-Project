package com.demo.bankapp.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.bankapp.config.TestUtils;
import com.demo.bankapp.model.Transaction;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.CreateTransactionRequest;
import com.demo.bankapp.request.FindAllTransactionsByUserRequest;
import com.demo.bankapp.service.abstractions.ITransactionService;
import com.demo.bankapp.service.abstractions.IUserService;
import com.demo.bankapp.service.abstractions.IWealthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Parameterized.class)
@WebMvcTest(value = TransactionController.class, secure = false)
public class TransactionControllerTest {

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@MockBean
	private IUserService userService;

	@MockBean
	private ITransactionService transactionService;

	@MockBean
	private IWealthService wealthService;

	@Autowired
	private MockMvc mockMvc;

	private CreateTransactionRequest ctRequest;
	private FindAllTransactionsByUserRequest fatbuRequest;

	public TransactionControllerTest(CreateTransactionRequest ctRequest, FindAllTransactionsByUserRequest fatbuRequest) {
		this.ctRequest = ctRequest;
		this.fatbuRequest = fatbuRequest;
	}

	@Parameters
	public static Collection<Object[]> data() {
		
		Collection<Object[]> params = new ArrayList<>();
		
		CreateTransactionRequest request2 = new CreateTransactionRequest();
		CreateTransactionRequest request3 = new CreateTransactionRequest();
		request3.setUsername("Mert");
		CreateTransactionRequest request4 = new CreateTransactionRequest();
		request4.setUsername("Mert");
		request4.setCurrency("TRY");
		CreateTransactionRequest request5 = new CreateTransactionRequest();
		request5.setUsername("Mert");
		request5.setCurrency("EUR");
		request5.setAmount(BigDecimal.TEN);
		CreateTransactionRequest request6 = new CreateTransactionRequest();
		request6.setUsername("Mert");
		request6.setCurrency("USD");
		request6.setAmount(BigDecimal.valueOf(1250));
		CreateTransactionRequest request7 = new CreateTransactionRequest();
		request7.setUsername("Mert");
		request7.setCurrency("TRY");
		request7.setAmount(BigDecimal.valueOf(1250));
		CreateTransactionRequest request8 = new CreateTransactionRequest();
		request8.setUsername("Mert");
		request8.setCurrency("EUR");
		request8.setAmount(BigDecimal.ZERO);
		CreateTransactionRequest request9 = new CreateTransactionRequest();
		request9.setUsername("Mert");
		request9.setCurrency("USD");
		request9.setAmount(BigDecimal.valueOf(-1));
		CreateTransactionRequest request10 = new CreateTransactionRequest();
		request10.setUsername("Mert");
		request10.setCurrency("");
		request10.setAmount(BigDecimal.valueOf(-1));
		CreateTransactionRequest request11 = new CreateTransactionRequest();
		request11.setUsername("");
		request11.setCurrency("XSD");
		
		FindAllTransactionsByUserRequest fatbuRequest1 = new FindAllTransactionsByUserRequest();
		FindAllTransactionsByUserRequest fatbuRequest2 = new FindAllTransactionsByUserRequest();
		fatbuRequest2.setUsername("");
		FindAllTransactionsByUserRequest fatbuRequest3 = new FindAllTransactionsByUserRequest();
		fatbuRequest3.setUsername("Mert");
		
		params.add(new Object[] {request2, fatbuRequest1});
		params.add(new Object[] {request3, fatbuRequest2});
		params.add(new Object[] {request4, fatbuRequest3});
		params.add(new Object[] {request5, fatbuRequest3});
		params.add(new Object[] {request6, fatbuRequest2});
		params.add(new Object[] {request7, fatbuRequest1});
		params.add(new Object[] {request8, fatbuRequest3});
		params.add(new Object[] {request9, fatbuRequest3});
		params.add(new Object[] {request10, fatbuRequest2});
		params.add(new Object[] {request11, fatbuRequest1});
		
		return params;
	}

	@Test
	public void createTransaction() throws Exception {
		boolean shouldThrowBadRequest = ctRequest == null || ctRequest.getUsername() == null || ctRequest.getUsername().equals("") ||
				ctRequest.getCurrency() == null || ctRequest.getCurrency().equals("") || ctRequest.getCurrency().equals("TRY") || ctRequest.getAmount() == null ||
				ctRequest.getAmount().signum() == 0 || ctRequest.getAmount().signum() == -1;
		
		boolean shouldThrowDailyOperationLimitExceeded = false;
		
		Transaction mockTransaction = new Transaction(250L, true, "USD", BigDecimal.TEN);
		
		int mockedOperationCount = ctRequest != null && ctRequest.getCurrency() != null && ctRequest.getCurrency().equals("EUR") ? 15 : 5;
		if(mockedOperationCount == 15) {
			shouldThrowDailyOperationLimitExceeded = true;
		}
		
		Mockito.when(userService.findByUserName(Mockito.anyString())).thenReturn(new User("Mert", "mert123", "22512567125"));
		Mockito.when(transactionService.getOperationCountFromLast24Hours(Mockito.any())).thenReturn(mockedOperationCount);
		Mockito.when(transactionService.createNewTransaction(Mockito.any(), Mockito.anyBoolean(), Mockito.anyString(), Mockito.any())).thenReturn(mockTransaction);
		
		String requestAsJson = new ObjectMapper().writeValueAsString(ctRequest);
		RequestBuilder requestBuilder = TestUtils.getPostRequestBuilder("/transaction/create", requestAsJson);
		
		ResultActions resultActions = mockMvc.perform(requestBuilder);
		if (shouldThrowBadRequest) {
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
		} else if (shouldThrowDailyOperationLimitExceeded) {
			resultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		} else {
			resultActions.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.transaction.amount", equalTo(mockTransaction.getAmount().intValue())))
				.andExpect(jsonPath("$.transaction.currency", equalTo(mockTransaction.getCurrency())));
		}
		
	}
	
	@Test
	public void findAllTransactions() throws Exception {
		List<Transaction> transactionList = new ArrayList<>();
		Transaction mockTransaction = new Transaction(63L, false, "EUR", BigDecimal.TEN);
		transactionList.add(mockTransaction);
		
		Mockito.when(userService.findByUserName(Mockito.anyString())).thenReturn(new User("Mert", "mert123", "22512567125"));
		Mockito.when(transactionService.findAllByUserId(Mockito.any())).thenReturn(transactionList);
		
		boolean shouldThrowBadRequest = fatbuRequest.getUsername() == null || fatbuRequest.getUsername().equals("");
		
		String requestAsJson = new ObjectMapper().writeValueAsString(fatbuRequest);
		RequestBuilder requestBuilder = TestUtils.getPostRequestBuilder("/transaction/find/all", requestAsJson);
		
		ResultActions resultActions = mockMvc.perform(requestBuilder);
		if (shouldThrowBadRequest) {
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
		} else {
			resultActions.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.transactionList", hasSize(1)))
				.andExpect(jsonPath("$.transactionList[0].currency", equalTo(mockTransaction.getCurrency())))
				.andExpect(jsonPath("$.transactionList[0].amount", equalTo(mockTransaction.getAmount().intValue())));
		}
	}

}
