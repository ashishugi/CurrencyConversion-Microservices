package com.in28minutes.microservices.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse") // by default it does retry 3 times, after that it give error
    @CircuitBreaker(name = "default", fallbackMethod = "hardCodedResponse")
    @RateLimiter(name="default") // set the number of call per second allowed
    @Bulkhead(name = "default") // concurrent calls
    public String sampleApi() {
        logger.info("Sample Api called received");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/somedummyurl", String.class); // this url does not exist;
//        return forEntity.getBody();
        return "Sample Api";
    }

    public String hardCodedResponse(Exception ex) {
        return "fallback response";
    }
}
