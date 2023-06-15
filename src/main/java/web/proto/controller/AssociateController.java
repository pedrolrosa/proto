package web.proto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.proto.model.Associate;
import web.proto.service.AssociateService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/associates")
public class AssociateController {

    @Autowired
    private AssociateService associateService;

    @GetMapping("/api/associates")
    public String associateView(Model model) {
        return "api/associates/index";
    }

    @GetMapping("/new")
    public String createView(Model model) {
        model.addAttribute("associate", new Associate());
        return "associate-form";
    }

    @PostMapping
    public String create(@ModelAttribute("associate") Associate associate) {
        associate.setDateCreated(LocalDate.now());
        associateService.create(associate);
        return "redirect:/associates";
    }

    @GetMapping
    public String read(Model model) {
        List<Associate> associates = associateService.read();
        model.addAttribute("associates", associates);
        return "associate-list";
    }

    @GetMapping("/{id}/edit")
    public String updateView(@PathVariable("id") Long id, Model model) {
        Associate associate = associateService.read(id);
        model.addAttribute("associate", associate);
        return "associate-form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id, @ModelAttribute("associate") Associate associate) {
        Associate existingAssociate = associateService.read(id);
        existingAssociate.setName(associate.getName());
        existingAssociate.setEmail(associate.getEmail());
        // Atualize os outros campos conforme necessário
        associateService.update(existingAssociate);
        return "redirect:/associates";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        associateService.delete(id);
        return "redirect:/associates";
    }
}
