package teamproject1.letsdoit.common.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String beforeLoginForm() {
        return "beforeLogin";
    }

    @GetMapping("/oauth2/callback/*")
    public String afterLoginForm(){
        return "main";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}