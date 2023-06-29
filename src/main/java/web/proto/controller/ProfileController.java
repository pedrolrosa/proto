package web.proto.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.model.Phase;
import web.proto.model.Project;
import web.proto.repository.AssociateRepository;
import web.proto.repository.ProjectRepository;
import web.proto.service.PhaseService;
import web.proto.service.ProjectService;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(AssociateController.class);

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private PhaseService phaseService;

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        String login = authentication.getName();
        logger.info(login);

        Associate logged = associateRepository.findByLoginIgnoreCase(login);
        List<Project> projects = projectService.readAll(logged.getId());

        model.addAttribute("associate", logged);
        model.addAttribute("projects", projects);
        return "profile";
    }

    @GetMapping("/newproject")
    public String newProjectView(@RequestParam("login") String login, @ModelAttribute("project") Project project,
            Model model) {
        project.setAssociate(associateRepository.findByLoginIgnoreCase(login));

        model.addAttribute("project", project);
        model.addAttribute("url", "/profile/newproject");
        return "public/projects/form";
    }

    @PostMapping("/newproject")
    public String newProject(@Valid Project project, BindingResult result, Model model,
            @RequestParam("login") String login) {
        if (result.hasErrors()) {
            return newProjectView(login, project, model);
        } else {
            project.setDateCreated(LocalDate.now());
            project.setActive(true);
            projectService.create(project);

            return "redirect:/profile";
        }
    }

    @GetMapping("/projects/{id}")
    public String viewProject(@PathVariable("id") Long id, Model model) {

        Optional<Project> opt = projectRepository.findById(id);
        if (opt != null) {
            Project project = opt.get();
            List<Phase> phases = phaseService.read(project);

            model.addAttribute("project", project);
            model.addAttribute("phases", phases);

            return "public/projects/details";
        }
        return "redirect:/profile";
    }

    @GetMapping("/projects/{id}/edit")
    public String projectUpdateView(@RequestParam("login") String login, @PathVariable("id") Long id, Project project, Model model) {
        if (project.getName() == null) {
            project = projectService.read(id);
        }
        project.setAssociate(associateRepository.findByLoginIgnoreCase(login));

        model.addAttribute("project", project);
        model.addAttribute("url", "/profile/projects/" + id + "/edit");
        return "api/projects/form";
    }

    @PostMapping("/projects/{id}/edit")
    public String projectUpdate(@RequestParam("login") String login, @PathVariable("id") Long id, @Valid Project project,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return projectUpdateView(login, id, project, model);
        } else {
            projectService.update(project);
            return "redirect:/profile";
        }
    }

    @GetMapping("projects/{id}/delete")
    public String projectDelete(@PathVariable("id") Long id) {
        projectService.delete(id);
        return "redirect:/profile";
    }

    @GetMapping("/projects/{id}/newphase")
    public String newPhaseView(@PathVariable("id") Long id, @ModelAttribute("phase") Phase phase, Model model) {

        model.addAttribute("url", "/profile/projects/" + id + "/newphase");
        return "public/phases/form";
    }

    @PostMapping("/projects/{id}/newphase")
    public String newPhase(@PathVariable("id") Long id, @Valid Phase phase,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return newPhaseView(id, phase, model);
        } else {
            Project project = projectRepository.findById(id).orElse(null);
            if (project == null) {
                return newPhaseView(id, phase, model);
            }

            phase.setProject(project);
            phase.setActive(true);
            phaseService.create(phase);

            return "redirect:/profile/projects/{id}";
        }
    }

    @GetMapping("/projects/{idProject}/phases/{idPhase}/edit")
    public String phaseUpdateView(@PathVariable("idProject") Long idProject, @PathVariable("idPhase") Long idPhase, Phase phase, Model model) {
        if (phase.getName() == null) {
            phase = phaseService.read(idPhase);
        }

        model.addAttribute("phase", phase);
        model.addAttribute("url", "/profile/projects/" + idProject + "/phases/"+ idPhase +"/edit");
        return "public/phases/form";
    }

    @PostMapping("/projects/{idProject}/phases/{idPhase}/edit")
    public String phaseUpdate(@PathVariable("idProject") Long idProject, @PathVariable("idPhase") Long idPhase, @Valid Phase phase,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return phaseUpdateView(idProject, idPhase, phase, model);
        } else {
            phaseService.update(phase);
            return "redirect:/profile/projects/"+idProject;
        }
    }

    @GetMapping("/projects/{idProject}/phases/{idPhase}/delete")
    public String phaseDelete(@PathVariable("idProject") Long idProject, @PathVariable("idPhase") Long idPhase) {
        phaseService.delete(idPhase);
        return "redirect:/profile/projects/"+idProject;
    }

}
