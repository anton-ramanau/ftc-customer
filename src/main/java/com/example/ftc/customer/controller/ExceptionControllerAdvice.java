package com.example.ftc.customer.controller;

import com.example.ftc.customer.exception.CargoNotFoundException;
import com.example.ftc.customer.exception.OrderNotFoundException;
import com.example.ftc.customer.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {


    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ModelAndView orderExceptionHandlerView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/errorOrder");
        return modelAndView;
    }
}

