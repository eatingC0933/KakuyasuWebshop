package se.yitingchang.testwebshop.Service;

import se.yitingchang.testwebshop.Repository.OrderDetailRepository;
import se.yitingchang.testwebshop.model.Cart;
import se.yitingchang.testwebshop.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yitingchang.testwebshop.Repository.OrderRepository;
import se.yitingchang.testwebshop.model.OrderDetails;
import se.yitingchang.testwebshop.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private CartService cartService;

    public Order checkout(User user) {
        List<Cart> cartItems = cartService.getCartItemsByUser(Optional.ofNullable(user));
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setTotalPrice(cartItems.stream().mapToDouble(Cart::getTotal).sum());
        orderRepository.save(order);

        for (Cart cartItem : cartItems) {
            OrderDetails orderDetail = new OrderDetails();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setPrice(cartItem.getPrice());
            orderDetail.setTotal(cartItem.getTotal());
            orderDetail.setCreateTime(new Date());
            orderDetailRepository.save(orderDetail);
        }

        cartService.clearCart(user.getId());
        return order;
    }

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderByOrderId(int orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public void deleteOrder(int orderId) {
        orderRepository.deleteById(orderId);
    }

    public void updateOrderStatus(int orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}

