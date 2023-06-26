package web.proto.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.service.AssociateService;

@Controller
@RequestMapping("/api/associates")
public class AssociateController {

    private static final Logger logger = LoggerFactory.getLogger(AssociateController.class);

    @Autowired
    private AssociateService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String createView(@ModelAttribute("associate") Associate associate, Model model) {
        model.addAttribute("url", "/api/associates/new");
        return "api/associates/form";
    }

    @PostMapping("/new")
    public String create(@Valid Associate associate, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return createView(associate, model);
        } else {
            associate.setPassword(passwordEncoder.encode(associate.getPassword()));
            associate.setDateCreated(LocalDate.now());
            associate.setActive(true);
            service.create(associate);

            return "redirect:/api/associates";
        }

    }

    @GetMapping
    public String read(Model model) {
        List<Associate> associates = service.read();
        model.addAttribute("associates", associates);
        return "api/associates/list";
    }

    @GetMapping("/{id}/edit")
    public String updateView(@PathVariable("id") Long id, Associate associate,
            Model model) {

        if (associate.getName() == null) {
            associate = service.read(id);
        }

        model.addAttribute("associate", associate);
        model.addAttribute("url", "/api/associates/" + id + "/edit");
        return "api/associates/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id, @Valid Associate associate,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return updateView(id, associate, model);
        } else {
            associate.setPassword(passwordEncoder.encode(associate.getPassword()));
            service.update(associate);

            return "redirect:/api/associates";
        }

    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        logger.info(id.toString());
        service.delete(id);
        return "redirect:/api/associates";
    }

}
