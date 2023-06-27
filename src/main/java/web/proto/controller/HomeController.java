package web.proto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.proto.model.Associate;
import web.proto.model.Phase;
import web.proto.model.Project;
import web.proto.model.Rate;
import web.proto.repository.AssociateRepository;
import web.proto.repository.ProjectRepository;
import web.proto.repository.RateRepository;
import web.proto.service.PhaseService;
import web.proto.service.ProjectService;
import web.proto.service.RateService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PhaseService phaseService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateService rateService;

    @GetMapping
    public String feed(Model model) {
        List<Project> projects = projectService.read();
        List<Phase> phases = phaseService.read();

        model.addAttribute("phases", phases);
        model.addAttribute("projects", projects);

        return "home";
    }

    @PostMapping
    public String boostProject(@RequestParam("login") String login, @RequestParam("project") Long id, Model model) {
        Associate associate = associateRepository.findByLoginIgnoreCase(login);
        Project project = projectRepository.findById(id).orElse(null);

        if (project == null) {

            return "error";
        }

        Rate existingRate = rateRepository.findByAssociateAndProject(associate, project);
        if (existingRate != null) {
            return "error";
        }

        Rate rate = new Rate();
        rate.setAssociate(associate);
        rate.setProject(project);
        rate.setScore(1);

        rateService.save(rate);

        return "redirect:/home";
    }

}
