package com.greenfoxacademy.baloghdominik.mysql.controllers;

        import com.greenfoxacademy.baloghdominik.mysql.models.UserModels;
        import com.greenfoxacademy.baloghdominik.mysql.repositories.UserModelsRepository;
        import com.greenfoxacademy.baloghdominik.mysql.services.Validation;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.*;
        import javax.servlet.http.Cookie;
        import javax.servlet.http.HttpServletResponse;
        import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/login")
public class LoginController {

    private UserModelsRepository userModelsRepository;
    private Validation validation;

    @Autowired
    public LoginController(Validation validation, UserModelsRepository userModelsRepository) {
        this.userModelsRepository = userModelsRepository;
        this.validation = validation;
    }

    @GetMapping(value={"", "/"})
    public String register() {
        return "login";
    }

    @PostMapping(value = "/signin")
    public String add(@ModelAttribute(value="username") String username, @ModelAttribute(value = "password") String password,
                       HttpServletResponse response) throws NoSuchAlgorithmException {
        if (validation.checklogin(username, password)) {
            UserModels thisUser = userModelsRepository.findByUsername(username);
            Cookie cookie = new Cookie("userValidation", validation.toMD5(thisUser.getPassword()));
            cookie.setPath("/");
            cookie.setMaxAge(100000);
            response.addCookie(cookie);
            Cookie cookieID = new Cookie("userID", thisUser.getId().toString());
            cookieID.setPath("/");
            cookieID.setMaxAge(100000);
            response.addCookie(cookieID);
            return  "redirect:../todo";
        }
        return "redirect:../login";
    }
}
