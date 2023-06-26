package web.proto.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.model.Project;
import web.proto.repository.AssociateRepository;
import web.proto.repository.ProjectRepository;
import web.proto.service.ProjectService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        String login = authentication.getName();
        Associate associate = associateRepository.findByLoginIgnoreCase(login);
        List<Project> projects = projectRepository.findByAssociateId(associate.getId());
        
        model.addAttribute("associate", associate);
        model.addAttribute("projects", projects);
        return "profile";
    }

    @GetMapping("/newproject")
    public String newProjectView(@RequestParam("login") String login, @ModelAttribute("project") Project project, Model model){
        project.setAssociate(associateRepository.findByLoginIgnoreCase(login));

        model.addAttribute("project", project);
        model.addAttribute("url", "/profile/newproject");
        return "public/projects/form";
    }

    @PostMapping("/newproject")
    public String newProject(@Valid Project project, BindingResult result, Model model, @RequestParam("login") String login) {
        if (result.hasErrors()) {
            return newProjectView(login, project, model);
        } else {
            project.setDateCreated(LocalDate.now());
            project.setActive(true);
            projectService.create(project);

            return "redirect:/profile";
        }
    }

}
