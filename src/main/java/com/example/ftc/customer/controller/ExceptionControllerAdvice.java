package com.example.ftc.customer.controller;

import com.example.ftc.customer.exception.CargoNotFoundException;
import com.example.ftc.customer.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice {


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ModelAndView orderExceptionHandlerView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/errorOrder");
        return modelAndView;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CargoNotFoundException.class)
    public ModelAndView cargoExceptionHandlerView(CargoNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("orderId", exception.getOrderId());
        modelAndView.setViewName("error/errorCargo");
        return modelAndView;
    }
}

