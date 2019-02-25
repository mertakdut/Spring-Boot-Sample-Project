package com.demo.bankapp.service.abstractions;

import java.math.BigDecimal;

public interface IUserWealthService {

	void makeWealthTransaction(Long userId, String currency, BigDecimal amount, boolean isBuying);

	void newWealthRecord(Long userId);

}
