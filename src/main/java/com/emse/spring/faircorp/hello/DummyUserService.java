package com.emse.spring.faircorp.hello;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;



@Service
public class DummyUserService implements UserService {
    private String[] names={"Elodie","Charles"};
    private GreetingService greetingService;

    public DummyUserService(GreetingService greetingService) {
        this.greetingService = greetingService;

    }
    @Override
    public void greetAll() {
    this.greetingService.greet(names[0]);
        this.greetingService.greet(names[1]);
    }
}
