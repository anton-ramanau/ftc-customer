package com.example.ftc.customer.controller;

import com.example.ftc.customer.command.CargoCommand;
import com.example.ftc.customer.command.OrderCommand;
import com.example.ftc.customer.domain.Cargo;
import com.example.ftc.customer.service.CargoService;
import com.example.ftc.customer.service.OrderService;
import com.example.ftc.customer.utils.ServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("user/order/{orderId}")
public class CargoController {
    private final OrderService orderService;
    private final CargoService cargoService;

    public CargoController(OrderService orderService, CargoService cargoService) {
        this.orderService = orderService;
        this.cargoService = cargoService;
    }

    @GetMapping("/cargo/new")
    public String getNewCargoView(@PathVariable Long orderId, Model model) {
        CargoCommand cargoCommand = new CargoCommand();
        model.addAttribute("cargo", cargoCommand);
        model.addAttribute("orderId", orderId);
        return "order/cargo/newCargo";
    }

    @PostMapping("/cargo/new")
    public String addNewCargoView(@PathVariable Long orderId, HttpServletRequest request, @ModelAttribute CargoCommand cargoCommand) {
        log.debug("Post new cargoCommand {}", cargoCommand);
        cargoService.saveCargoCommand(orderId, ServerUtils.getSessionUserId(request), cargoCommand);
        return "redirect:/user/order/" + orderId + "/update";
    }

    @PostMapping("/cargo/{cargoId}/delete")
    public String deleteCargo(@PathVariable Long orderId, HttpServletRequest request, @PathVariable Long cargoId) {
        if (orderService.findOrderByIdAndUserId(orderId, ServerUtils.getSessionUserId(request)) != null) {
            cargoService.deleteCargoByCargoIdAndOrderId(cargoId, orderId);
        } else {
            throw new RuntimeException("Choosed order not found");
        }
        return "redirect:/user/order/" + orderId + "/update";
    }

    @GetMapping("/cargo/{cargoId}/update")
    public String getUpdateView(@PathVariable Long orderId, HttpServletRequest request, @PathVariable Long cargoId, Model model) {
        CargoCommand cargoCommand;
        if (orderService.findOrderByIdAndUserId(orderId, ServerUtils.getSessionUserId(request)) != null) {
            model.addAttribute("cargo", cargoService.findCargoCommandByIdAndOrderId(cargoId, orderId));
            model.addAttribute("orderId", orderId);
        } else {
            throw new RuntimeException("Choosed order not found");
        }
        return "order/cargo/cargoUpdate";
    }

    @PostMapping("/cargo/{cargoId}/update")
    public String updateCargo(@PathVariable Long orderId, @PathVariable Long cargoId, HttpServletRequest request, CargoCommand cargoCommand) {
        if (cargoCommand.getId() == null) {
            cargoCommand.setId(cargoId);
        }
        cargoService.saveCargoCommand(orderId, ServerUtils.getSessionUserId(request), cargoCommand);
        return "redirect:/user/order/" + orderId + "/update";
    }
}
