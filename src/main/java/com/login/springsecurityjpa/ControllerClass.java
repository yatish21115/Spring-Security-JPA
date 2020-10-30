package com.login.springsecurityjpa;
import com.login.springsecurityjpa.Models.User;
import com.login.springsecurityjpa.Repositories.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Provider;
import java.util.Map;

@RestController
public class ControllerClass {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/forgot")
    public ModelAndView forgot(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject( "user",user);
        modelAndView.setViewName("forgot");
        return modelAndView;
    }

    @PostMapping("/forgot")
    public ModelAndView forgot_password(User user,@RequestParam("email") String email, @RequestParam("security") String security,@RequestParam("answer") String answer, @RequestParam("password") String password, RedirectAttributes redirectAttributes){
        ModelAndView modelAndView = new ModelAndView();
        User reset_user = userService.isEmailAlreadyPresent(user);
        try {
            if (email.equals(reset_user.getEmail())) {
                if (security.equals(reset_user.getSecurity()) && answer.equals(reset_user.getAnswer())) {
                reset_user.setPassword(password);
                userService.saveUser(reset_user);
                modelAndView.addObject("user",user);
                redirectAttributes.addFlashAttribute("Message", "You have successfully reset your password. You may now login");
                modelAndView.setViewName("redirect:login");

                }
                else {
                    modelAndView.addObject("successMessage", "Security question & answer does not match.");
                    modelAndView.addObject("user", user);
                    modelAndView.setViewName("forgot");
                }
            }
        }
        catch(NullPointerException e) {
            modelAndView.addObject("successMessage", "Email-ID does not exist.");
            modelAndView.addObject("user",user);
            modelAndView.setViewName("forgot");
        }

        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView modelAndView = new ModelAndView();


        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, ModelMap modelMap){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("failureMessage","Please correct the errors in form!");
            modelMap.addAttribute("bindingResult", bindingResult);
        }
        else if(userService.isUserAlreadyPresent(user)){
            modelAndView.addObject("failureMessage","Username already exist");
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


//    @GetMapping("/reset")
//    public ModelAndView reset_password(){
//        ModelAndView modelAndView = new ModelAndView();
//        User user = new User();
//        modelAndView.addObject( "user",user);
//        modelAndView.setViewName("reset");
//        return modelAndView;
//    }

//    @PostMapping("/reset")
//    public ModelAndView reset_password(User user, @RequestParam Map<String,String> requestParams){
//        ModelAndView modelAndView = new ModelAndView();
//        User reset_user = userService.isSecurityAlreadyPresent(user);
//        if(requestParams.get("answer").equals(reset_user.getAnswer()))
//        modelAndView.setViewName("password");
//        return modelAndView;
//    }

//    @PostMapping("/password")
//    public ModelAndView new_password(User user, @RequestParam Map<String,String> requestParams, RedirectAttributes redirectAttributes){
//        ModelAndView modelAndView = new ModelAndView();
//        reset_user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
//        userService.saveUser(reset_user);
//        redirectAttributes.addFlashAttribute("successMessage", "You have successfully reset your password. You may now login");
//        modelAndView.addObject("user",user);
//        modelAndView.setViewName("redirect:login");
//        return modelAndView;
//    }