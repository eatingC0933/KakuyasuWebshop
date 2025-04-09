package se.yitingchang.testwebshop;


import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.yitingchang.testwebshop.UserService;
import se.yitingchang.testwebshop.User;

import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
        }
        return "user_login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String userName, @RequestParam String password) {
        User user = userService.login(userName, password);
        if (user != null) {
            return "redirect:/home";
        } else {
            return "redirect:/user/login?error=true";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        return "user_register";
    }

   
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.register(user);
            redirectAttributes.addFlashAttribute("message", "User registered successfully!");
            return "redirect:/home"; // Redirect to loginOrRegister page after registration
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Try again.");
            return "redirect:/user/register"; // Redirect back if there is an error
        }
    }




   
    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list"; // Points to user-list.html
    }

    
    @GetMapping("/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user-profile"; // Points to user-profile.html
        } else {
            model.addAttribute("error", "User not found");
            return "error"; // Redirect to an error page
        }
    }

  
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/users/list"; // Redirect to user list after deletion
    }
}



