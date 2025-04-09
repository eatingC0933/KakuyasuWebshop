package se.yitingchang.testwebshop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.yitingchang.testwebshop.Service.CategoryService;
import se.yitingchang.testwebshop.Service.ProductService;
import se.yitingchang.testwebshop.Service.UserService;
import se.yitingchang.testwebshop.model.Category;
import se.yitingchang.testwebshop.model.Product;
import se.yitingchang.testwebshop.model.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

   @GetMapping("/")
    public String startPage() {

        return "user_login";
    }

    @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
        if (principal != null) {  // Check if the user is logged in
            String username = principal.getName();
            Optional<User> userOptional = userService.findByUserName(username);
            model.addAttribute("user", userOptional.get());
        }

        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);

        return "home";
    }

    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/user/login";
    }

  
   @GetMapping("/home")
    public String homePage(Model model, Principal principal) {
    
        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
    }



}
