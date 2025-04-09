package se.yitingchang.testwebshop.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.yitingchang.testwebshop.model.OrderDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findByOrderOrderId(int orderId);

    List<OrderDetails> findByOrderDetailId(int orderDetailId);



}
