package com.example.technical_test;

import com.example.technical_test.model.JPAService.UserRepository;
import com.example.technical_test.model.User;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import com.example.technical_test.model.JPAService.PriceRepository;
import com.example.technical_test.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PriceBatchJob {
	@Autowired
	PriceRepository priceRepository;
	@Autowired
	UserRepository userRepository;
	@Scheduled(fixedDelay = 10000)
	public void retrievePrice() throws InterruptedException  {
		LocalDateTime current = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDateTime = current.format(format);
		log.info("Schedule Timing : " + formattedDateTime);
		String uri = "https://api.binance.com/api/v3/ticker/bookTicker";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		Gson gson = new Gson();
		Price[] response = gson.fromJson(result, Price[].class);
		ArrayList<Price> priceList = getTokensFromList(response,"ETHUSDT");
		for (Price price : priceList){
			savePrice(price);
		}

	}

	@Scheduled(fixedDelay = 100000000)
	public void insertUser() throws InterruptedException  {
		List<User> userList = userRepository.findAll();
		log.info("Start insetUser");
		if(userList.isEmpty()){
			User user = new User();
			user.setUSDTBalance(50000);
			user.setUserId("0001");
			userRepository.save(user);
		}
	}

	private ArrayList <Price> getTokensFromList(Price[] response, String token){
		ArrayList priceList = new ArrayList();
		for(Price price : response){
			if(price.getSymbol().equalsIgnoreCase("ETHUSDT") || price.getSymbol().equalsIgnoreCase("BTCUSDT")) {
				priceList.add(price);
			}
		}
		return priceList;
	}

	public void savePrice(Price savedPrice) {
		Price price =  priceRepository.findBySymbol(savedPrice.getSymbol());
		if(price != null) {
			price.setAskPrice(savedPrice.getAskPrice());
			price.setBidPrice(savedPrice.getBidPrice());
			price.setAskQty(savedPrice.getAskQty());
			price.setBidQty(savedPrice.getBidQty());
			priceRepository.save(price);
		}else{
			priceRepository.save(savedPrice);
		}
	}
}
