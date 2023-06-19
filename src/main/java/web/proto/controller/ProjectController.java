package web.proto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.model.Project;
import web.proto.service.AssociateService;
import web.proto.service.ProjectService;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/projects")
public class ProjectController {

    private static final Logger logger = LoggerFactory.getLogger(AssociateController.class);

    @Autowired
    private ProjectService service;

    @Autowired
    private AssociateService associateService;

    @GetMapping("/new")
    public String createView(Model model) {

        List<Associate> associates = associateService.read();

        model.addAttribute("project", new Project());
        model.addAttribute("associates", associates);
        model.addAttribute("url", "/api/projects/new");
        return "api/projects/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("project") Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.info(result.toString());
            return createView(model);
        } else {
            project.setDateCreated(LocalDate.now());
            project.setActive(true);
            service.create(project);
            return "redirect:/api/projects";
        }
    }

    @GetMapping
    public String read(Model model) {
        List<Project> projects = service.read();
        model.addAttribute("projects", projects);
        return "api/projects/list";
    }

    @GetMapping("/{id}/edit")
    public String updateView(@PathVariable("id") Long id, Model model) {
        Project project = service.read(id);
        List<Associate> associates = associateService.read();

        model.addAttribute("project", project);
        model.addAttribute("associates", associates);
        model.addAttribute("url", "/api/projects/{id}/edit");
        return "api/projects/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("project") Project project,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return updateView(id, model);
        } else {
            service.update(project);
            return "redirect:/api/projects";
        }
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/api/projects";
    }
}
