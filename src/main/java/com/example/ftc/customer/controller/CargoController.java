package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.converter.CargoCommandToCargo;
import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.CargoType;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.domain.User;
import com.example.ftc.customer.exception.AccessForbiddenException;
import com.example.ftc.customer.exception.CargoNotFoundException;
import com.example.ftc.customer.exception.OrderNotFoundException;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.CargoTypeService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.service.UserService;
import com.example.ftc.customer.utils.ServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("user/order")
public class CargoController {
    private final OrderService orderService;
    private final CargoService cargoService;
    private final CargoCommandToCargo cargoCommandToCargo;
    private final CargoToCargoCommand cargoToCargoCommand;
    private final CargoTypeService cargoTypeService;
    private final SmartValidator validator;
    private final UserService userService;

    public CargoController(OrderService orderService, CargoService cargoService, CargoCommandToCargo cargoCommandToCargo, CargoToCargoCommand cargoToCargoCommand, CargoTypeService cargoTypeService, SmartValidator validator, UserService userService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.cargoCommandToCargo = cargoCommandToCargo;
        this.cargoToCargoCommand = cargoToCargoCommand;
        this.cargoTypeService = cargoTypeService;
        this.validator = validator;
        this.userService = userService;
    }

    @GetMapping("{orderId}/cargo/new")
    public String getNewCargoView(@PathVariable Long orderId, Model model) {
        CargoCommand cargoCommand = new CargoCommand();
        Set<CargoType> cargoTypes = cargoTypeService.findAll();
        model.addAttribute("cargoTypes", cargoTypes);
        model.addAttribute("cargo", cargoCommand);
        model.addAttribute("orderId", orderId);
        return "order/cargo/newCargo";
    }

    @PostMapping("{orderId}/cargo/new")
    public String addNewCargoView(@Valid CargoCommand cargoCommand, BindingResult bindingResult, @PathVariable Long orderId, HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorsLoadDate", bindingResult.getFieldErrors("loadDate"));
            model.addAttribute("errorsUnloadDate", bindingResult.getFieldErrors("unloadDate"));
            Set<CargoType> cargoTypes = cargoTypeService.findAll();
            model.addAttribute("cargoTypes", cargoTypes);
            model.addAttribute("cargo", cargoCommand);
            model.addAttribute("orderId", orderId);
            return "order/cargo/newCargo";
        }
        Cargo cargo = cargoCommandToCargo.convert(cargoCommand);
        Order order = orderService.findOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        if (!order.getUser().getId().equals(ServerUtils.getSessionUserId(request))) {
            throw new AccessForbiddenException();
        }
        cargo.setOrder(order);
        cargoService.save(cargo);
        return "redirect:/user/order/" + orderId + "/details";
    }

    @PostMapping("/cargo/{cargoId}/delete")
    public String deleteCargo(HttpServletRequest request, @PathVariable Long cargoId) {
        Cargo cargo = cargoService.findCargoById(cargoId);
        if (cargo == null) {
            throw new CargoNotFoundException(cargoId);
        }
        if (!cargo.getOrder().getUser().getId().equals(ServerUtils.getSessionUserId(request))) {
            throw new AccessForbiddenException();
        }
        cargoService.delete(cargo);
        return "redirect:/user/order/" + cargo.getOrder().getId() + "/details";
    }

    @GetMapping("/cargo/{cargoId}/update")
    public String getUpdateView(HttpServletRequest request, @PathVariable Long cargoId, Model model) {
        Cargo cargo = cargoService.findCargoById(cargoId);
        if (cargo == null) {
            throw new CargoNotFoundException(cargoId);
        }
        if (!cargo.getOrder().getUser().getId().equals(ServerUtils.getSessionUserId(request))) {
            throw new AccessForbiddenException();
        }
        model.addAttribute("cargo", cargoToCargoCommand.convert(cargo));
        Set<CargoType> cargoTypes = cargoTypeService.findAll();
        model.addAttribute("cargoTypes", cargoTypes);
        return "order/cargo/cargoUpdate";
    }

    @PostMapping("/cargo/update")
    public String updateCargo(CargoCommand cargoCommand, BindingResult bindingResult, HttpServletRequest request, Model model) {
        Cargo cargoDb = cargoService.findCargoById(cargoCommand.getId());
        if (cargoDb == null) {
            throw new CargoNotFoundException(cargoCommand.getId());
        }
        if (cargoDb.getLoadDate() == null || !cargoDb.getLoadDate().equals(cargoCommand.getLoadDate())) {
            validator.validateValue(CargoCommand.class, "loadDate", cargoCommand.getLoadDate(), bindingResult);
        }
        if (cargoDb.getUnloadDate() == null || !cargoDb.getUnloadDate().equals(cargoCommand.getUnloadDate())) {
            validator.validateValue(CargoCommand.class, "unloadDate", cargoCommand.getUnloadDate(), bindingResult);
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorsLoadDate", bindingResult.getFieldErrors("loadDate"));
            model.addAttribute("errorsUnloadDate", bindingResult.getFieldErrors("unloadDate"));
            model.addAttribute("cargo", cargoCommand);
            Set<CargoType> cargoTypes = cargoTypeService.findAll();
            model.addAttribute("cargoTypes", cargoTypes);
            return "order/cargo/cargoUpdate";
        }
        if (!cargoDb.getOrder().getUser().getId().equals(ServerUtils.getSessionUserId(request))) {
            throw new AccessForbiddenException();
        }
        Cargo cargo = cargoCommandToCargo.convert(cargoCommand);
        cargo.setOrder(cargoDb.getOrder()); //to make sure that someone don't add false order
        cargoService.save(cargo);
        return "redirect:/user/order/" + cargoDb.getOrder().getId() + "/details";
    }
}
