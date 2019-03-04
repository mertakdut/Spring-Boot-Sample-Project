package com.demo.bankapp.service.concretions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.bankapp.configuration.Constants;
import com.demo.bankapp.exception.BadRequestException;
import com.demo.bankapp.exception.InsufficientFundsException;
import com.demo.bankapp.exception.UserNotFoundException;
import com.demo.bankapp.model.Wealth;
import com.demo.bankapp.repository.WealthRepository;
import com.demo.bankapp.service.abstractions.IWealthService;

@Service
public class WealthService implements IWealthService {

	private WealthRepository repository;

	@Autowired
	public WealthService(WealthRepository repository) {
		this.repository = repository;
	}

	@Override
	public void newWealthRecord(Long userId) {

		Map<String, BigDecimal> wealthMap = new HashMap<>();

		Map<String, Double> currencyMap = getCurrencyRates();
		for (Map.Entry<String, Double> entry : currencyMap.entrySet()) {
			wealthMap.put(entry.getKey(), BigDecimal.ZERO);
		}

		addInitialBalance(wealthMap);

		Wealth userWealth = new Wealth(userId, wealthMap);
		repository.save(userWealth);
	}

	@Override
	public void makeWealthExchange(Long userId, String currency, BigDecimal amount, boolean isBuying) {

		Wealth userWealth = repository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		Map<String, BigDecimal> wealthMap = userWealth.getWealthMap();

		if (!wealthMap.containsKey(currency)) {
			throw new BadRequestException("Invalid currency.");
		}

		BigDecimal rate = BigDecimal.valueOf(getCurrencyRates().get(currency));
		BigDecimal tryEquivalent = amount.divide(rate, 9, RoundingMode.HALF_UP);

		if (isBuying) {
			if (tryEquivalent.compareTo(wealthMap.get(Constants.MAIN_CURRENCY)) == 1) { // Trying to buy more than he can.
				throw new InsufficientFundsException();
			}
		} else {
			if (amount.compareTo(wealthMap.get(currency)) == 1) { // Trying to sell more than he has.
				throw new InsufficientFundsException(currency);
			}
		}

		if (isBuying) {
			wealthMap.put(Constants.MAIN_CURRENCY, wealthMap.get(Constants.MAIN_CURRENCY).subtract(tryEquivalent));
			wealthMap.put(currency, wealthMap.get(currency).add(amount));
		} else {
			wealthMap.put(currency, wealthMap.get(currency).subtract(amount));
			wealthMap.put(Constants.MAIN_CURRENCY, wealthMap.get(Constants.MAIN_CURRENCY).add(tryEquivalent));
		}

		userWealth.setWealthMap(wealthMap);
		repository.save(userWealth);
	}

	@Override
	public void makeWealthTransaction(Long userId, String currency, BigDecimal amount, boolean isIncrementing) {

		Wealth userWealth = repository.findById(userId).orElseThrow(() -> new UserNotFoundException());
		Map<String, BigDecimal> wealthMap = userWealth.getWealthMap();

		if (!wealthMap.containsKey(currency)) {
			throw new BadRequestException(Constants.MESSAGE_INVALIDCURRENCY);
		}

		if (!isIncrementing) {
			if (amount.compareTo(wealthMap.get(currency)) == 1) {
				throw new InsufficientFundsException(currency);
			}

			wealthMap.put(currency, wealthMap.get(currency).subtract(amount));
		} else {
			wealthMap.put(currency, wealthMap.get(currency).add(amount));
		}

		userWealth.setWealthMap(wealthMap);
		repository.save(userWealth);
	}

	@Override
	public Wealth findWealth(Long userId) {
		return repository.findById(userId).orElseThrow(() -> new UserNotFoundException());
	}

	@Override
	public Map<String, Double> getCurrencyRates() {
		final String uri = "https://api.exchangeratesapi.io/latest?base=TRY";

		RestTemplate restTemplate = new RestTemplate();
		return ((Map<String, Map<String, Double>>) restTemplate.getForObject(uri, Map.class)).get("rates");
	}

	private void addInitialBalance(Map<String, BigDecimal> wealthMap) {
		String currency = Constants.MAIN_CURRENCY;

		BigDecimal currentAmount = wealthMap.get(currency);
		BigDecimal amountToAdd = new BigDecimal(130000);
		BigDecimal finalAmount = currentAmount.add(amountToAdd);

		wealthMap.put(currency, finalAmount);
	}

}
