package se.yitingchang.testwebshop.Repository;


import se.yitingchang.testwebshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByCategoryId(int categoryId);
}
