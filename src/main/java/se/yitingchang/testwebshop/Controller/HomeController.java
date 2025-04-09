package se.yitingchang.testwebshop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String startPage() {
        return "user_login";
    }

    @GetMapping("home")
    public String home() {
        return "home";
    }


}
