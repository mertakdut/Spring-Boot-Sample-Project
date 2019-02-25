package com.demo.bankapp.service.abstractions;

import java.math.BigDecimal;

import com.demo.bankapp.model.Wealth;

public interface IWealthService {

	void makeWealthTransaction(Long userId, String currency, BigDecimal amount, boolean isBuying);

	void newWealthRecord(Long userId);

	Wealth findWealth(Long userId);

}
