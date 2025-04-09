package se.yitingchang.testwebshop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.yitingchang.testwebshop.Repository.CategoryRepository;
import se.yitingchang.testwebshop.model.Category;

import java.util.List;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

   public Category getCategoryById(int categoryId) {
        return  categoryRepository.findByCategoryId(categoryId);
   }

}

