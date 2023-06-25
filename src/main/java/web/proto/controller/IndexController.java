package web.proto.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.model.Project;
import web.proto.model.filter.ProjectFilter;
import web.proto.pagination.PageWrapper;
import web.proto.repository.ProjectRepository;
import web.proto.service.AssociateService;

@Controller
public class IndexController {

    @Autowired
    private AssociateService associateService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = { "/", "/index.html" })
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String openRegister(@ModelAttribute("associate") Associate associate, Model model) {
        model.addAttribute("url", "/register");
        return "api/associates/form";
    }

    @PostMapping("/register")
    public String register(@Valid Associate associate, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return openRegister(associate, model);
        } else {
            associate.setPassword(passwordEncoder.encode(associate.getPassword()));
            associate.setDateCreated(LocalDate.now());
            associate.setActive(true);
            associateService.create(associate);

            return "redirect:/";
        }

    }

    @GetMapping("/opensearch")
    public String openSearch() {
        return "api/projects/search";
    }

    @GetMapping("/search")
    public String search(ProjectFilter filter, Model model,
            @PageableDefault(size = 5) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
            
        Page<Project> page = projectRepository.searchWithFilter(filter, pageable);
        if (!page.isEmpty()) {
            PageWrapper<Project> pageWrapper = new PageWrapper<>(page, request);
            model.addAttribute("pagina", pageWrapper);
            return "api/projects/page";
        } else {
            // nao acho nada
            return "redirect:/opensearch";
        }

    }

}
