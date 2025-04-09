package se.yitingchang.testwebshop.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="cart")
public class Cart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id",nullable = false)
    private int cartId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @Column(name="quantity",nullable = false)
    private Integer quantity;

    @Column(name="price",nullable = false)
    private double price;

    @Column(name = "total", nullable = false)
    private double total;


    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;



    public Cart(User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

    public Cart() {

    }



    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateTotal();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        calculateTotal();
    }

    public void calculateTotal() {
        this.total = this.price * this.quantity;
    }

    public double getTotal() {
        return total;
    }

    public String getProductName() {
        return product.toString();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
