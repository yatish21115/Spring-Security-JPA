package com.login.springsecurityjpa;
import com.login.springsecurityjpa.Models.User;
import com.login.springsecurityjpa.Repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class ControllerClass {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject( "user",user);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("successMessage","Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else if(userService.isUserAlreadyPresent(user)){
            modelAndView.addObject("successMessage","Username already exist");
        }
        else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User is registered successfully");
        }
        modelAndView.addObject("user",user);
        modelAndView.setViewName("register");
        return modelAndView;
    }
}
