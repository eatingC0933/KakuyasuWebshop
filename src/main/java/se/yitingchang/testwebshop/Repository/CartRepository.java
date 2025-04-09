package se.yitingchang.testwebshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yitingchang.testwebshop.model.Cart;
import se.yitingchang.testwebshop.model.Product;
import se.yitingchang.testwebshop.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(Optional<User> user);



    Cart findByCartId(int cartId);

    List<Cart> findByUser_Id(int userId);
}
