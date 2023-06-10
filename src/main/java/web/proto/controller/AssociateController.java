package web.proto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.util.logging.Slf4j;
import web.proto.model.Associate;
import web.proto.service.AssociateService;

@Controller
@RequestMapping("/associates")
@Slf4j
public class AssociateController { 
    
    @Autowired
    private AssociateService service;
    
    @GetMapping("/register")
	public String showRegisterForm(Model model) {
        model.addAttribute("associate", new Associate());
        return "associates/register";
    }

    @PostMapping("/register")
    public String createAssociate(@ModelAttribute("associate") Associate associate) {
        service.save(associate);
        return "redirect:/associates";
    }

}
