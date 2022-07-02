package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.converter.CargoCommandToCargo;
import com.example.ftc.customer.converter.CargoToCargoCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.domain.CargoType;
import com.example.ftc.customer.domain.Order;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.CargoTypeService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.utils.ServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("user/order/{orderId}")
public class CargoController {
    private final OrderService orderService;
    private final CargoService cargoService;
    private final CargoCommandToCargo cargoCommandToCargo;
    private final CargoToCargoCommand cargoToCargoCommand;
    private final CargoTypeService cargoTypeService;
    private final Validator validator;

    public CargoController(OrderService orderService, CargoService cargoService, CargoCommandToCargo cargoCommandToCargo, CargoToCargoCommand cargoToCargoCommand, CargoTypeService cargoTypeService, Validator validator) {
        this.orderService = orderService;
        this.cargoService = cargoService;
        this.cargoCommandToCargo = cargoCommandToCargo;
        this.cargoToCargoCommand = cargoToCargoCommand;
        this.cargoTypeService = cargoTypeService;
        this.validator = validator;
    }

    @GetMapping("/cargo/new")
    public String getNewCargoView(@PathVariable Long orderId, Model model) {
        CargoCommand cargoCommand = new CargoCommand();
        Set<CargoType> cargoTypes = cargoTypeService.findAll();
        model.addAttribute("cargoTypes", cargoTypes);
        model.addAttribute("cargo", cargoCommand);
        model.addAttribute("orderId", orderId);
        return "order/cargo/newCargo";
    }

    @PostMapping("/cargo/new")
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
        cargoService.saveCargo(cargo, orderId, ServerUtils.getSessionUserId(request));
        return "redirect:/user/order/" + orderId + "/details";
    }

    @PostMapping("/cargo/{cargoId}/delete")
    public String deleteCargo(@PathVariable Long orderId, HttpServletRequest request, @PathVariable Long cargoId) {
        cargoService.deleteCargoByCargoIdAndOrderId(cargoId, orderId, ServerUtils.getSessionUserId(request));
        return "redirect:/user/order/" + orderId + "/details";
    }

    @GetMapping("/cargo/{cargoId}/update")
    public String getUpdateView(@PathVariable Long orderId, HttpServletRequest request, @PathVariable Long cargoId, Model model) {
        Order order = orderService.findOrderByIdAndUserId(orderId, ServerUtils.getSessionUserId(request));
        Cargo cargo = cargoService.findCargoByIdAndOrder(cargoId, orderId, ServerUtils.getSessionUserId(request));
        model.addAttribute("cargo", cargoToCargoCommand.convert(cargo));
        Set<CargoType> cargoTypes = cargoTypeService.findAll();
        model.addAttribute("cargoTypes", cargoTypes);
        model.addAttribute("orderId", orderId);
        return "order/cargo/cargoUpdate";
    }

    @PostMapping("/cargo/{cargoId}/update")
    public String updateCargo(CargoCommand cargoCommand, BindingResult bindingResult, @PathVariable Long orderId, @PathVariable Long cargoId, HttpServletRequest request, Model model) {
        validator.validate(cargoCommand, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorsLoadDate", bindingResult.getFieldErrors("loadDate"));
            model.addAttribute("errorsUnloadDate", bindingResult.getFieldErrors("unloadDate"));
            cargoCommand.setId(cargoId);
            model.addAttribute("cargo", cargoCommand);
            Set<CargoType> cargoTypes = cargoTypeService.findAll();
            model.addAttribute("cargoTypes", cargoTypes);
            model.addAttribute("orderId", orderId);
            return "order/cargo/cargoUpdate";
        }
        Cargo cargo = cargoCommandToCargo.convert(cargoCommand);
        cargo.setId(cargoId);
        cargoService.saveCargo(cargo, orderId, ServerUtils.getSessionUserId(request));
        return "redirect:/user/order/" + orderId + "/details";
    }
}
