package springboot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springboot.models.User;
import springboot.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String AllUsers(Model model) {
        List<User> users=userService.listUsers();
        model.addAttribute("users", users);
        return "user/userList";
    }

    @GetMapping("/add")
    public String showAddUser ( Model model) {
        model.addAttribute("user", new User());
        return "user/addUser";
    }

    @PostMapping("/add")
    public String save( @ModelAttribute("user") @Valid User user,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/addUser";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit")
    public String showEditUser (@RequestParam("id") int id, Model model) {
        Optional<User> userById = userService.findById(id);
        if (userById.isPresent()) {
            model.addAttribute("user", userById.get());
            return "user/editUser";
        } else {
            return "redirect:/users";
        }
    }
    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/editUser";
        }
        userService.editUser(user);
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String showDeleteUser(@RequestParam("id") int id, Model model) {
        Optional<User> userById = userService.findById(id);
        if (userById.isPresent()) {
            model.addAttribute("user", userById.get());
            return "user/eleteUser";
        } else {
            return "redirect:/users";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}