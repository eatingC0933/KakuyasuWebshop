package se.yitingchang.testwebshop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.yitingchang.testwebshop.Service.CategoryService;
import se.yitingchang.testwebshop.Service.ProductService;
import se.yitingchang.testwebshop.model.Category;
import se.yitingchang.testwebshop.model.Product;

import java.util.List;


@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    public String getAllCategories(Model model) {
    model.addAttribute("categories", categoryService.getAllCategories());
    model.addAttribute("category", new Category());
    return "category";
    }


    @PostMapping
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/category";
    }









}
