package com.login.springsecurityjpa;
import com.login.springsecurityjpa.Models.Role;
import com.login.springsecurityjpa.Models.User;
import com.login.springsecurityjpa.Repositories.RoleRepository;
import com.login.springsecurityjpa.Repositories.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
public class ControllerClass {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/admin")
    public ModelAndView admin(){
        ModelAndView modelAndView = new ModelAndView();
        List<User> listusers = userService.listAll();
        modelAndView.addObject("listusers",listusers);
        modelAndView.setViewName("admin");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(name = "id") long id){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.get(id);
        modelAndView.addObject("user",user);
        modelAndView.setViewName("edit");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable(name = "id") long id){
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.get(id);
        userService.delete(id);     //Always do - SET FOREIGN_KEY_CHECKS=0; IN MYsql
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView saveUser(User user,@RequestParam("enabled") boolean enabled,@RequestParam("firstname") String firstname,@RequestParam("lastname") String lastname,@RequestParam("email") String email,@RequestParam("username") String username,  @RequestParam("number") int number){
        ModelAndView modelAndView = new ModelAndView();
        String role="";
        User edit_user = userService.isEmailAlreadyPresent(user);
        modelAndView.addObject("successMessage", "The status is already"+((enabled==true)?"Activated":"Deactivated"));
        edit_user.setFirstname(firstname);
        edit_user.setLastname(lastname);
        edit_user.setEmail(email);
        edit_user.setUsername(username);
        edit_user.setEnabled(enabled);
        if(number==4)role = "ADMIN";
        if(number==3)role = "EDITOR";
        if(number==2)role = "CREATOR";
        if(number==1)role = "USER";
        Role userRole = roleRepository.findByname(role);
        edit_user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userService.saveEditUser(edit_user);
        modelAndView.addObject( "user",user);
        modelAndView.setViewName("redirect:admin");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView redir_home(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:home");
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
    public ModelAndView registerUser(User user, BindingResult bindingResult, ModelMap modelMap){
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
        modelAndView.setViewName("redirect:login");
        return modelAndView;
    }
}