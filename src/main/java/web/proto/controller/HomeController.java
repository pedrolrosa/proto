package web.proto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.proto.model.Phase;
import web.proto.model.Project;
import web.proto.service.PhaseService;
import web.proto.service.ProjectService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private PhaseService phaseService;

    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public String feed(Model model){
        List<Project> projects = projectService.read();

        model.addAttribute("projects", projects);

        return "home";
    }

}
