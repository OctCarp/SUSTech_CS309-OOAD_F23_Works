package io.github.octcarp.sustech.cs309.assignment.a1backend.contorller;

import io.github.octcarp.sustech.cs309.assignment.a1backend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String username, Model model) {
        if (username != null) {
            model.addAttribute("username", username);
        }
        return "user/login";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "user/register";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        if (username == null || password == null) {
            model.addAttribute("error_msg", "Empty username or password");
            return "user/login";
        }
        boolean isAuthenticated = userService.authenticateUser(username, password);
        if (isAuthenticated) {
            return "redirect:/dashboard"; //
        } else {
            model.addAttribute("error_msg", "Invalid credentials");
            return "user/login";
        }
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, Model model) {
        if (username == null || password == null || confirmPassword == null) {
            model.addAttribute("error_msg", "Empty username or password");
            return "user/register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error_msg", "Passwords do not match");
            return "user/register";
        }

        boolean isRegistered = userService.registerUser(username, password);
        if (isRegistered) {
            return "redirect:/user/login?username=" + username;
        } else {
            model.addAttribute("error_msg", "Username already exists");
            return "user/register";
        }
    }
}
