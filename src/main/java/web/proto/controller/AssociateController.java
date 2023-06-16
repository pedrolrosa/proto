package web.proto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.service.AssociateService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/associates")
public class AssociateController {

    @Autowired
    private AssociateService service;

    @GetMapping("/new")
    public String createView(Model model) {
        model.addAttribute("associate", new Associate());
        model.addAttribute("url", "/api/associates/new");
        return "api/associates/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("associate") Associate associate, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return createView(model);
        } else {
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
    public String updateView(@PathVariable("id") Long id, Model model) {
        Associate associate = service.read(id);
        model.addAttribute("associate", associate);
        model.addAttribute("url", "/api/associates/{id}/edit");
        return "api/associates/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("associate") Associate associate,
            BindingResult result, Model model) {

        if (result.hasErrors()) {
            return updateView(id, model);
        } else {
            service.update(associate);

            return "redirect:/api/associates";
        }

    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/api/associates";
    }

}
