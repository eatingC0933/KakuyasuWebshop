package se.yitingchang.testwebshop.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import se.yitingchang.testwebshop.Service.CategoryService;
import se.yitingchang.testwebshop.Service.ProductService;
import se.yitingchang.testwebshop.model.Category;
import se.yitingchang.testwebshop.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;



   @GetMapping("/category/{categoryId}")
    public String getProductsByCategoryId(@RequestParam("category") int categoryId,
                                           Model model) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category-products"; // Render category.html
    }

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        return "products";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("product") String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);
        model.addAttribute("products", products);
        return "category-products";
    }


    @GetMapping("/admin/addNewProduct")
    public String showAddProductForm(@RequestParam(value = "imageUrl", required = false) String imageUrl, Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("imageUrl", imageUrl);
        return "addNewProductForm";
    }


    @PostMapping("/admin/addNewProduct")
    public String addNewProduct(@ModelAttribute Product product,
                                @RequestParam("category") int categoryId,
                                @RequestParam("imageUrl") String imageUrl) {

        Category category = categoryService.getCategoryById(categoryId);
        product.setCategory(category);
        product.setImagePath(imageUrl);

        productService.saveProduct(product);
        return "redirect:/admin/addNewProduct";
    }





    @GetMapping("/admin/updateProduct")
    public String showUpdateProductForm(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "update_product"; // shows the page with dropdown
    }


    @GetMapping("/admin/updateProduct/select")
    public String ProductToUpdate(@RequestParam("productId") int productId, Model model) {


        Optional<Product> productOpt = productService.findById(productId);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
        } else {
            model.addAttribute("error", "Product not found!");
        }

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("products", productService.getAllProducts());
        return "update_product";
    }

    @PostMapping("/admin/updateProduct")
    public String updateProduct(@RequestParam("productId") int productId,
                                @RequestParam("productName") Optional<String> productName,
                                @RequestParam("price") Optional<String> price,
                                @RequestParam("category") int categoryId,
                                @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        Optional<Product> optionalProduct = productService.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existing = optionalProduct.get();

            productName.filter(name -> !name.isEmpty()).ifPresent(existing::setProductName);
            price.filter(p -> !p.isEmpty()).map(Double::parseDouble).ifPresent(existing::setPrice);

            Category category = categoryService.getCategoryById(categoryId);
            existing.setCategory(category);

            if (!imageFile.isEmpty()) {
                String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
                String uploadDir = new ClassPathResource("static/uploads/").getFile().getAbsolutePath();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                existing.setImagePath("/uploads/" + fileName);
            }

            productService.saveProduct(existing);
        }

        return "redirect:/admin/updateProduct";
    }


    @PostMapping("/admin/updateProduct/delete")
    public String deleteProduct(@RequestParam("productId") int productId, RedirectAttributes redirectAttributes) {
        try {
            System.out.println(">>> Deleting product: " + productId);
            productService.deleteProductById(productId);
            redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Failed to delete product."+e.getMessage());
        }
        return "redirect:/admin/updateProduct";
    }

    @GetMapping("/admin/updateProduct/delete")
    public String deleteProductGet(@RequestParam("productId") int productId,
                                   RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProductById(productId);
            redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete product.");
        }
        return "redirect:/admin/updateProduct";
    }
}


 
  
