package web.proto.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import web.proto.model.Associate;
import web.proto.model.enums.Status;
import web.proto.model.filter.AssociateFilter;
import web.proto.pagination.PageWrapper;
import web.proto.repository.AssociateRepository;
import web.proto.service.AssociateService;

@Controller
@RequestMapping("/associates")
@Slf4j
public class AssociateController {

    private static final Logger logger = LoggerFactory.getLogger(AssociateController.class);

    @Autowired
    private AssociateRepository repository;

    @Autowired
    private AssociateService service;

    @GetMapping("/opensearch")
    public String openSearch() {
        return "associates/search";
    }

    @GetMapping("/search")
    public String search(AssociateFilter filtro, Model model,
            @PageableDefault(size = 10) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {
        Page<Associate> pagina = repository.filtrar(filtro, pageable);
        PageWrapper<Associate> paginaWrapper = new PageWrapper<>(pagina, request);
        logger.info("Pessoas buscadas no BD: {}", paginaWrapper.getConteudo());
        model.addAttribute("pagina", paginaWrapper);
        return "associates/associates";
    }

    @GetMapping("/save")
    public String showRegisterForm(Model model) {
        model.addAttribute("associate", new Associate());
        model.addAttribute("title", "Register Associate");
        model.addAttribute("url", "/associates/save");
        model.addAttribute("btn", "Register");
        return "associates/save";
    }

    @PostMapping("/save")
    public String createAssociate(@ModelAttribute("associate") Associate associate) {
        service.save(associate);
        return "redirect:/associates";
    }

    @PostMapping("/openupdate")
    public String openUpdate(Long id, Model model) {
            Optional<Associate> opt = repository.findById(id);
            Associate associate = opt.get();
            model.addAttribute("associate", associate);
            model.addAttribute("title", "Update Associate");
            model.addAttribute("url", "/associates/update");
            model.addAttribute("btn", "Update");
            return "associates/save";
    }

    @PostMapping("/update")
    public String update(Associate associate, Model model) {
        service.save(associate);
        return "mostrarmensagem";
    }

    @PostMapping("/abrirremover")
    public String openDelete(Associate associate) {
        return "associates/delete";
    }

    @PostMapping("/delete")
    public String delete(Long id, Model model) {
        Optional<Associate> optLote = repository.findById(id);
        if (optLote.isPresent()) {
            Associate associate = optLote.get();
            associate.setStatus(Status.INACTIVE);
            service.save(associate);
            model.addAttribute("option", "associate");
            model.addAttribute("message", "Excluiu");
            return "mostrarmensagem";
        } else {
            model.addAttribute("message", "There is no person with code: " + id);
            model.addAttribute("option", "associate");
            return "mostrarmensagem";
        }
    }

}
