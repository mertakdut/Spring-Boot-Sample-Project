package com.demo.bankapp.service.concretions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.exception.InsufficientFundsException;
import com.demo.bankapp.exception.UserNotFoundException;
import com.demo.bankapp.model.Wealth;
import com.demo.bankapp.repository.WealthRepository;
import com.demo.bankapp.service.abstractions.IWealthService;

@Service
public class WealthService implements IWealthService {

	@Autowired
	private WealthRepository repository;

	@Override
	public void makeWealthTransaction(Long userId, String currency, BigDecimal amount, boolean isBuying) {

		Wealth userWealth = repository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		Map<String, BigDecimal> wealthMap = userWealth.getWealthMap();

		if (!wealthMap.containsKey(currency)) {
			throw new BadRequestException("Invalid currency.");
		}

		double rate = getCurrencyRate(currency);
		BigDecimal tryEquivalent = new BigDecimal(amount.doubleValue() * rate);

		if (isBuying) {
			if (tryEquivalent.compareTo(wealthMap.get("TRY")) == 1) { // Trying to buy more than he can.
				throw new InsufficientFundsException();
			}
		} else {
			if (amount.compareTo(wealthMap.get(currency)) == 1) { // Trying to sell more than he has.
				throw new InsufficientFundsException(currency);
			}
		}

		if (isBuying) {
			wealthMap.put("TRY", wealthMap.get("TRY").subtract(tryEquivalent));
			wealthMap.put(currency, wealthMap.get(currency).add(amount));
		} else {
			wealthMap.put(currency, wealthMap.get(currency).subtract(amount));
			wealthMap.put("TRY", wealthMap.get("TRY").add(tryEquivalent));
		}

		userWealth.setWealthMap(wealthMap);
		repository.save(userWealth);
	}

	@Override
	public void newWealthRecord(Long userId) {

		Map<String, BigDecimal> wealthMap = new HashMap<>();

		Map<String, Double> currencyMap = getCurrencyMap();
		for (Map.Entry<String, Double> entry : currencyMap.entrySet()) {
			wealthMap.put(entry.getKey(), BigDecimal.ZERO);
		}

		addInitialBalance(wealthMap);

		Wealth userWealth = new Wealth(userId, wealthMap);
		repository.save(userWealth);
	}

	@Override
	public Wealth findWealth(Long userId) {
		return repository.findById(userId).orElseThrow(() -> new UserNotFoundException());
	}

	private void addInitialBalance(Map<String, BigDecimal> wealthMap) {
		String currency = "TRY";

		BigDecimal currentAmount = wealthMap.get(currency);
		BigDecimal amountToAdd = new BigDecimal(13000);
		BigDecimal finalAmount = currentAmount.add(amountToAdd);

		wealthMap.put(currency, finalAmount);
	}

	private Map<String, Double> getCurrencyMap() {
		final String uri = "https://api.exchangeratesapi.io/latest?base=TRY";

		RestTemplate restTemplate = new RestTemplate();
		return ((Map<String, Map<String, Double>>) restTemplate.getForObject(uri, Map.class)).get("rates");
	}

	private double getCurrencyRate(String currency) {
		Map<String, Double> currencyMap = getCurrencyMap();
		return currencyMap.get(currency);
	}

}
