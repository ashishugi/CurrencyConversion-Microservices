package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRespository extends JpaRepository<CurrencyExchange, Long> { // entity we want to connect, primary key type

    CurrencyExchange findByFromAndTo(String from, String to); // spring jpa will automatically create a query with this function
}
