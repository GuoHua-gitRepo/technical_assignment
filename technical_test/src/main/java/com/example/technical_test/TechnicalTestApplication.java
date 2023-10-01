package com.example.technical_test;

import com.example.technical_test.model.*;
import com.example.technical_test.model.JPAService.PriceRepository;
import com.example.technical_test.model.JPAService.TransactionRepository;
import com.example.technical_test.model.JPAService.UserCoinRepository;
import com.example.technical_test.model.JPAService.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
@EnableScheduling
public class TechnicalTestApplication {
	@Autowired
	PriceRepository priceRepository;
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	UserCoinRepository userCoinRepository;
	@Autowired
	UserRepository userRepository;

	@GetMapping("/aggregated_price")
	public Response getAggregatePrice(){
		Response response = new Response();
		List<Price> retrievedPriceList = priceRepository.findAll();
		if(retrievedPriceList == null) {
			response.setStatusCode(StatusCode.NOT_FOUND.getStatusCode());
			response.setMessage(StatusCode.NOT_FOUND.getMessage());
			return response;
		}
		response.setData(retrievedPriceList);
		response.setStatusCode(StatusCode.OK.getStatusCode());
		response.setMessage(StatusCode.OK.getMessage());
		return response;
	}

	@PostMapping("/create_transaction")
	public Response createTransactionRecords(@RequestBody Transaction transaction){
		//Assuming only 1 bid or ask transaction at 1 time
		Response response = new Response();
		if(transaction == null){
			response.setStatusCode(StatusCode.MissingBody.getStatusCode());
			response.setMessage(StatusCode.MissingBody.getMessage());
			return response;
		}
		String userId = transaction.getUserId();
		UserCoin userCoin = userCoinRepository.findUserCoin(userId,transaction.getSymbol());
		User user = userRepository.findByUserId(userId);
		if(user == null){
			response.setStatusCode(400);
			response.setMessage("User not found");
		}
		//For Sell order
		if(transaction.getBidQty() != null && transaction.getBidPrice() != null){
			if(userCoin == null){
				response.setStatusCode(400);
				response.setMessage("Insufficient Token Amt");
				return response;
			}
			boolean isValidCoinBalance = validateCoinBalance(userCoin.getQuantity(), transaction.getBidQty());
			if(!isValidCoinBalance){
				response.setStatusCode(400);
				response.setMessage("Insufficient Token Amt");
			}else{
				Float newQty = userCoin.getQuantity() - transaction.getBidQty();
				userCoin.setQuantity(newQty);
				userCoinRepository.save(userCoin);
				float coinValue = transaction.getBidQty() * transaction.getBidPrice();
				float finalBalance = coinValue + user.getUSDTBalance();
				user.setUSDTBalance(finalBalance);
				userRepository.save(user);
				transactionRepository.save(transaction);
				response.setStatusCode(StatusCode.OK.getStatusCode());
				response.setMessage(StatusCode.OK.getMessage());
			}
			return response;
		}
		// for Buy Order
		else if(transaction.getAskQty() != null && transaction.getAskPrice() != null){
			float coinValue = transaction.getAskQty() * transaction.getAskPrice();
			boolean isValidUSDTBalance = validateUSDTBalance(user.getUSDTBalance(),coinValue);
			if(!isValidUSDTBalance){
				response.setStatusCode(400);
				response.setMessage("Insufficient USDT balance");
			}else{
				 if(userCoin == null){
					 userCoin.setUserId(userId);
					 userCoin.setQuantity(transaction.getAskQty());
					 userCoin.setSymbol(transaction.getSymbol());
					 userCoinRepository.save(userCoin);
				 }else{
					 Float newQty = transaction.getAskQty() + userCoin.getQuantity();
					 userCoin.setQuantity(newQty);
					 userCoinRepository.save(userCoin);
				 }
				 float newUSDTBalance = user.getUSDTBalance() - coinValue;
				 user.setUSDTBalance(newUSDTBalance);
				 userRepository.save(user);
				 transactionRepository.save(transaction);
				response.setStatusCode(StatusCode.OK.getStatusCode());
				response.setMessage(StatusCode.OK.getMessage());
			}
		}
		return response;
	}

	@GetMapping("/cryptoBalance/(userId)")
	public Response getCryptoBalance(@PathVariable String userId){
		Response response = new Response();
		List<UserCoin> retrievedUserCoin = userCoinRepository.findByUserId(userId);
		if(retrievedUserCoin.isEmpty()) {
			response.setStatusCode(StatusCode.NOT_FOUND.getStatusCode());
			response.setMessage(StatusCode.NOT_FOUND.getMessage());
		}
		response.setData(retrievedUserCoin);
		response.setStatusCode(StatusCode.OK.getStatusCode());
		response.setMessage(StatusCode.OK.getMessage());
		return response;
	}
	@GetMapping("/transaction/(userId)")
	public Response getTransaction(@PathVariable String userId){
		Response response = new Response();
		List<Transaction> retrievedUserTransaction = transactionRepository.findByUserId(userId);
		if(retrievedUserTransaction.isEmpty()) {
			response.setStatusCode(StatusCode.NOT_FOUND.getStatusCode());
			response.setMessage(StatusCode.NOT_FOUND.getMessage());
		}
		response.setData(retrievedUserTransaction);
		response.setStatusCode(StatusCode.OK.getStatusCode());
		response.setMessage(StatusCode.OK.getMessage());
		return response;
	}

	public boolean validateCoinBalance(Float balance, Float qty){
		if(qty > balance){
			return false;
		}
		return true;
	}

	public boolean validateUSDTBalance(Float balance, Float amt){
		if(amt > balance){
			return false;
		}
		return true;
	}
	public static void main(String[] args) {

		SpringApplication.run(TechnicalTestApplication.class, args);

	}

}
