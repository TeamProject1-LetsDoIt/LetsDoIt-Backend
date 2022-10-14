package teamproject1.letsdoit.common.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ViewController {

    @GetMapping("/")
    public String beforeLoginForm() {

        return "beforeLogin";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String mainForm() {
        return "home";
    }
}
