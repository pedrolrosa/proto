package web.proto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.util.logging.Slf4j;
import web.proto.model.Associate;
import web.proto.model.enums.Area;

@Controller
@RequestMapping("/associates")
@Slf4j
public class AssociateController {    
    
    @GetMapping("/register")
	public String showRegisterForm(Model model) {
        model.addAttribute("associate", new Associate());
        return "associates/register";
    }

}
