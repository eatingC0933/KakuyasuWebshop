package se.yitingchang.testwebshop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yitingchang.testwebshop.Repository.*;
import se.yitingchang.testwebshop.model.*;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;


    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartItemById(int cartId) {
        Optional<Cart> optional = cartRepository.findById(cartId);
        Cart cart;
        if (optional.isPresent()) {
            cart = optional.get();
        } else {
            throw new RuntimeException("Cart not found for ID: " + cartId);
        }
        return cart;
    }

    public Cart addToCart(Cart cart) {
        cart.calculateTotal();
        cartRepository.save(cart);
        return cart;
    }


    public List<Cart> getCartItemsByUser(Optional<User> userOptional) {
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return cartRepository.findByUser_Id(user.getId());
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public void clearCart(int userId) {
        List<Cart> cartItems = cartRepository.findByUser_Id(userId);
        cartRepository.deleteAll(cartItems);

    }


    public void removeCartItem(int cartId) {
        cartRepository.deleteById(cartId);
    }

    public void updateCartItemQuantity(int cartId, int quantity) {
        Optional<Cart> cartItemOptional = cartRepository.findById(cartId);
        if(cartItemOptional.isPresent()) {
            Cart cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
            cartRepository.save(cartItem);
        }else{
            throw new RuntimeException("Cart not found for ID: " + cartId);
        }
    }
}
