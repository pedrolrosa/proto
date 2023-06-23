package web.proto.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.service.AssociateService;

@Controller
public class IndexController {

	@Autowired
    private AssociateService service;

    @Autowired
	private PasswordEncoder passwordEncoder;
    
    @GetMapping(value = {"/", "/index.html"} )
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String openRegister(@ModelAttribute("associate") Associate associate, Model model) {
		model.addAttribute("url", "/register");
		return "api/associates/form";
	}

    @PostMapping("/register")
    public String register(@Valid Associate associate, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return openRegister(associate, model);
        } else {
            associate.setPassword(passwordEncoder.encode(associate.getPassword()));
            associate.setDateCreated(LocalDate.now());
            associate.setActive(true);
            service.create(associate);

            return "redirect:/";
        }

    }

}
