package se.yitingchang.testwebshop.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.yitingchang.testwebshop.Service.OrderDetailService;
import se.yitingchang.testwebshop.model.OrderDetails;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/orderDetails")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/{orderId}")
    public String showOrderDetails(@PathVariable("orderId")int orderId, Model model) {
        List<OrderDetails> orderDetails =  orderDetailService.getOrderDetailsByOrderId(orderId);
        model.addAttribute("orderDetails", orderDetails);
        return "orderDetail";
    }

 

}

