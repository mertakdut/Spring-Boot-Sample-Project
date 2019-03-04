package com.demo.bankapp.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.demo.bankapp.model.Transfer;
import com.demo.bankapp.model.User;
import com.demo.bankapp.request.CreateTransferRequest;
import com.demo.bankapp.service.abstractions.ITransactionService;
import com.demo.bankapp.service.abstractions.IUserService;
import com.demo.bankapp.service.abstractions.IWealthService;
import com.demo.bankapp.service.concretions.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(Parameterized.class)
@WebMvcTest(value = TransferController.class, secure = false)
public class TransferControllerTest {
	
	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();
	
	@MockBean
	private TransferService transferService;
	
	@MockBean
	private IUserService userService;

	@MockBean
	private ITransactionService transactionService;

	@MockBean
	private IWealthService wealthService;
	
	@Autowired
	private MockMvc mockMvc;
	
	private CreateTransferRequest request;
	
	public TransferControllerTest(CreateTransferRequest request) {
		this.request = request;
	}
	
	@Parameters
	public static List<CreateTransferRequest> data() {
		CreateTransferRequest request1 = new CreateTransferRequest();
		CreateTransferRequest request2 = new CreateTransferRequest();
		request2.setCurrency("");
		CreateTransferRequest request3 = new CreateTransferRequest();
		request3.setCurrency("USD");
		CreateTransferRequest request4 = new CreateTransferRequest();
		request4.setCurrency("USD");
		request4.setSenderUsername("");
		CreateTransferRequest request5 = new CreateTransferRequest();
		request5.setCurrency("USD");
		request5.setSenderUsername("Mert");
		CreateTransferRequest request6 = new CreateTransferRequest();
		request6.setCurrency("USD");
		request6.setSenderUsername("Mert");
		request6.setReceiverTcno("");
		CreateTransferRequest request7 = new CreateTransferRequest();
		request7.setCurrency("USD");
		request7.setSenderUsername("Mert");
		request7.setReceiverTcno("312561");
		CreateTransferRequest request8 = new CreateTransferRequest();
		request8.setCurrency("USD");
		request8.setSenderUsername("Mert");
		request8.setReceiverTcno("31256152521");
		CreateTransferRequest request9 = new CreateTransferRequest();
		request9.setCurrency("USD");
		request9.setSenderUsername("Mert");
		request9.setReceiverTcno("31256152521");
		request9.setAmount(BigDecimal.ZERO);
		CreateTransferRequest request10 = new CreateTransferRequest();
		request10.setCurrency("USD");
		request10.setSenderUsername("Mert");
		request10.setReceiverTcno("31256152521");
		request10.setAmount(BigDecimal.valueOf(-250));
		CreateTransferRequest request11 = new CreateTransferRequest();
		request11.setCurrency("USD");
		request11.setSenderUsername("Mert");
		request11.setReceiverTcno("31256152521");
		request11.setAmount(BigDecimal.valueOf(250));
		CreateTransferRequest request12 = new CreateTransferRequest();
		request12.setCurrency("USD");
		request12.setSenderUsername("Mert");
		request12.setReceiverTcno("31256152521");
		request12.setAmount(BigDecimal.valueOf(20001));
		CreateTransferRequest request13 = new CreateTransferRequest();
		request13.setCurrency("EUR");
		request13.setSenderUsername("Mert");
		request13.setReceiverTcno("31256152521");
		request13.setAmount(BigDecimal.valueOf(1000));
		CreateTransferRequest request14 = new CreateTransferRequest();
		request14.setCurrency("USD");
		request14.setSenderUsername("Mert3");
		request14.setReceiverTcno("31256152521");
		request14.setAmount(BigDecimal.valueOf(1000));
		
		List<CreateTransferRequest> requestList = new ArrayList<>();
		requestList.add(request1);
		requestList.add(request2);
		requestList.add(request3);
		requestList.add(request4);
		requestList.add(request5);
		requestList.add(request6);
		requestList.add(request7);
		requestList.add(request8);
		requestList.add(request9);
		requestList.add(request10);
		requestList.add(request11);
		requestList.add(request12);
		requestList.add(request13);
		requestList.add(request14);
		
		return requestList;
	}
	
	@Test
	public void createTransaction() throws Exception {
		
		boolean shouldThrowBadRequest = request.getCurrency() == null || request.getCurrency().equals("") || request.getSenderUsername() == null || request.getSenderUsername().equals("") ||
				request.getReceiverTcno() == null || request.getReceiverTcno().equals("") || request.getReceiverTcno().length() != 11 || request.getAmount() == null || 
				request.getAmount().signum() == 0 || request.getAmount().signum() == -1;
		
		boolean shouldThrowExceededMaxValuePerTransaction = request.getAmount() != null && request.getAmount().compareTo(BigDecimal.valueOf(20000)) == 1; // As long as we know currency is USD.
		
		Map<String, Double> mockedCurrencyRates = new HashMap<>();
		mockedCurrencyRates.put("USD", Double.valueOf(0.666666777));
		mockedCurrencyRates.put("EUR", Double.valueOf(0.1633186347));
		
		boolean shouldThrowExceededMaxValueForDay = false;
		List<Transfer> mockedLastDayTransfers = new ArrayList<>();
		if (request.getCurrency() != null && request.getCurrency().equals("EUR")) {
			mockedLastDayTransfers.add(new Transfer(2612L, 318L, "EUR", BigDecimal.valueOf(2500)));
			mockedLastDayTransfers.add(new Transfer(2612L, 318L, "EUR", BigDecimal.valueOf(8500)));
			mockedLastDayTransfers.add(new Transfer(2612L, 318L, "EUR", BigDecimal.valueOf(7500)));
			mockedLastDayTransfers.add(new Transfer(2612L, 318L, "EUR", BigDecimal.valueOf(2500)));
			shouldThrowExceededMaxValueForDay = true;
		}
		
		User mockedUserByUsername = new User("Mert", "mert123", "22512567125");
		
		User mockedUserByTcno;
		if (request.getSenderUsername() != null && request.getSenderUsername().equals("Mert3")) {
			mockedUserByTcno = new User("Mert", "mert123", "22512567125");
			shouldThrowBadRequest = true;
		} else {
			mockedUserByTcno = new User("Mert5", "mert1235", "51285625682");
		}
		
		Transfer mockedTransfer = new Transfer(mockedUserByUsername.getId(), mockedUserByTcno.getId(), request.getCurrency(), request.getAmount());
		
		Mockito.when(wealthService.getCurrencyRates()).thenReturn(mockedCurrencyRates);
		Mockito.when(userService.findByUserName(Mockito.anyString())).thenReturn(mockedUserByUsername);
		Mockito.when(transferService.findAllTransfersFrom24Hours(Mockito.any())).thenReturn(mockedLastDayTransfers);
		Mockito.when(userService.findByTcno(Mockito.anyString())).thenReturn(mockedUserByTcno);
		Mockito.when(transferService.createNewTransfer(Mockito.any())).thenReturn(mockedTransfer);
		
		String requestAsJson = new ObjectMapper().writeValueAsString(request);
		RequestBuilder requestBuilder = TestUtils.getPostRequestBuilder("/transfer/create", requestAsJson);
		
		ResultActions resultActions = mockMvc.perform(requestBuilder);
		if(shouldThrowBadRequest) {
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
		} else if (shouldThrowExceededMaxValuePerTransaction) {
			resultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		} else if (shouldThrowExceededMaxValueForDay) {
			resultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		} else {
			resultActions.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.transfer.fromUserId", equalTo(mockedTransfer.getFromUserId())))
			.andExpect(jsonPath("$.transfer.toUserId", equalTo(mockedTransfer.getToUserId())))
			.andExpect(jsonPath("$.transfer.currency", equalTo(mockedTransfer.getCurrency())))
			.andExpect(jsonPath("$.transfer.amount", equalTo(mockedTransfer.getAmount().intValue())));
		}
	}

}
