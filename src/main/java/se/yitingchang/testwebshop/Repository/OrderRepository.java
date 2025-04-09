package se.yitingchang.testwebshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.yitingchang.testwebshop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    public Order findByOrderId(int orderId);
}
