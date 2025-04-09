package se.yitingchang.testwebshop.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import se.yitingchang.testwebshop.Service.CartService;
import se.yitingchang.testwebshop.Service.OrderService;
import se.yitingchang.testwebshop.Service.ProductService;
import se.yitingchang.testwebshop.Service.UserService;
import se.yitingchang.testwebshop.model.Cart;
import se.yitingchang.testwebshop.model.Order;
import se.yitingchang.testwebshop.model.Product;
import se.yitingchang.testwebshop.model.User;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String viewCart(@RequestParam("userId") int userId, Model model) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Cart> cartItems = cartService.getCartItemsByUser(userOptional);
            double totalPrice = cartItems.stream().mapToDouble(Cart::getTotal).sum();
            model.addAttribute("user", user);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);

            return "cart";
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam("userId") int userId,
                            @RequestParam("quantity") int quantity) {
        System.out.println("Adding to cart: productId=" + productId + ", userId=" + userId + ", quantity=" + quantity);

        Optional<User> userOptional = userService.findById(userId);
        Optional<Product> productOptional = productService.findById(productId);

        if (userOptional.isPresent() && productOptional.isPresent()) {
            User user = userOptional.get();
            Product product = productOptional.get();

            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setPrice(product.getPrice());
            cart.calculateTotal();
            cartService.addToCart(cart);

            return "redirect:/cart?userId=" + userId;
        } else {
            return "redirect:/error";
        }
    }




    @PostMapping("/update")
    public String updateCart(@RequestParam("cartId") int cartId,
                             @RequestParam("userId") int userId,
                             @RequestParam("quantity") int quantity) {
    cartService.updateCartItemQuantity(cartId, quantity);
    return "redirect:/cart?userId=" + userId;
    }


    @PostMapping("/remove")
    public String removeCartItem(@RequestParam("cartId") int cartId,
                                 @RequestParam("userId") int userId) {
        cartService.removeCartItem(cartId);
        return "redirect:/cart?userId=" + userId;
    }

    @PostMapping("/clear")
    public String clearCart(@RequestParam("userId") int userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            cartService.clearCart(userId);
            return "redirect:/cart?userId=" + userId;
        } else {
            return "redirect:/error";
        }
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("userId") int userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Order order=orderService.checkout(user);

            return "redirect:/cart/order_confirmation?userId=" + userId;
        } else {
            return "redirect:/error";
        }
    }

    @GetMapping("/order_confirmation")
    public String orderConfirmation(@RequestParam("userId") int userId, Model model) {
        model.addAttribute("userId", userId);
        return "order_confirmation";  // Render order_confirmation.html
    }
}

