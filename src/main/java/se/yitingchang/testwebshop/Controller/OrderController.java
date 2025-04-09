package se.yitingchang.testwebshop.Controller;


import org.springframework.web.bind.annotation.*;
import se.yitingchang.testwebshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import se.yitingchang.testwebshop.Service.OrderService;

import java.util.List;


@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

  
    @GetMapping("/{orderID}")
    public Order getOrderByID(@PathVariable int orderID) {
        return  orderService.getOrderByOrderId(orderID);
    }

    @PostMapping
    public String saveOrder(@ModelAttribute Order order) {
        orderService.saveOrder(order);
        return "redirect:/admin/orders";
    }

    @PostMapping("/updateStatus")
    public String updateOrderStatus(@RequestParam("orderId") int orderId, @RequestParam("status") String status) {
        orderService.updateOrderStatus(orderId, status);
        return "redirect:/admin/orders";
    }
}

