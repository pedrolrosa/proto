package web.proto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.proto.model.Associate;
import web.proto.model.Project;
import web.proto.repository.AssociateRepository;
import web.proto.repository.ProjectRepository;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private ProjectRepository projectRepository;
    
    @GetMapping
    public String showProfilePage(Model model, Authentication authentication) {
        String login = authentication.getName();
        Associate associate = associateRepository.findByLoginIgnoreCase(login);
        List<Project> projects = projectRepository.findByAssociateId(associate.getId());
        
        model.addAttribute("associate", associate);
        model.addAttribute("projects", projects);
        return "profile";
    }

}
