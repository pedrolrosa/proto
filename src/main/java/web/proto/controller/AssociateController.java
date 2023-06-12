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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import web.proto.ajax.NotificacaoAlertify;
import web.proto.ajax.RespostaJSON;
import web.proto.ajax.ThymeleafUtil;
import web.proto.ajax.TipoNotificaoAlertify;
import web.proto.ajax.TipoResposta;
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

    @Autowired
    private ThymeleafUtil util;

    @GetMapping("/opensearch")
    public String openSearch() {
        return "associates/search";
    }

    @GetMapping("/search")
    public String search(AssociateFilter filtro, Model model,
            @PageableDefault(size = 10) @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            HttpServletRequest request) {

        Page<Associate> pagina = repository.filtrar(filtro, pageable);

        if (!pagina.isEmpty()) {
            PageWrapper<Associate> paginaWrapper = new PageWrapper<>(pagina, request);
            logger.info("Pessoas buscadas no BD: {}", paginaWrapper.getConteudo());
            model.addAttribute("pagina", paginaWrapper);
            return "associates/associates";
        } else {
            model.addAttribute("message", "não acho gente");
            model.addAttribute("option", "associate");
            return "mostrarmensagem";
        }

    }

    @GetMapping("/save")
    public String showRegisterForm(@ModelAttribute("associate") Associate obj, Model model) {
        model.addAttribute("title", "Register Associate");
        model.addAttribute("url", "/associates/save");
        model.addAttribute("btn", "Register");
        return "associates/save";
    }

    @PostMapping(value = { "/save" }, produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public RespostaJSON createAssociate(@RequestBody @Valid @ModelAttribute("associate") Associate obj,
            BindingResult resultado,
            Model model, HttpServletRequest request, HttpServletResponse response) {

        RespostaJSON resposta;

        if (resultado.hasErrors()) {
            model.addAttribute("title", "Register Associate");
            model.addAttribute("url", "/associates/save");
            model.addAttribute("btn", "Register");
            resposta = new RespostaJSON(TipoResposta.FRAGMENTO);

            String html = util.processThymeleafTemplate(request, response, model.asMap(), "associates/save",
                    "save");
            resposta.setHtmlFragmento(html);

        } else {
            service.save(obj);

            NotificacaoAlertify notificacaoAlertify = new NotificacaoAlertify("Created Associate",
                    TipoNotificaoAlertify.SUCESSO, 5);
            model.addAttribute("title", "Register Associate");
            model.addAttribute("url", "/associates/save");
            model.addAttribute("btn", "Register");

            resposta = new RespostaJSON(TipoResposta.FRAGMENTO_E_NOTIFICACAO);
            resposta.setNotificacao(notificacaoAlertify);
            model.addAttribute("associate", new Associate());
            String html = util.processThymeleafTemplate(request, response, model.asMap(), "/associates/save",
                    "save");
            resposta.setHtmlFragmento(html);

        }

        return resposta;
    }

    @PostMapping("/openupdate")
    public String openUpdate(Long id, Model model) {
        if (id != null) {
            Optional<Associate> opt = repository.findById(id);
            Associate associate = opt.get();
            model.addAttribute("associate", associate);
            model.addAttribute("title", "Update Associate");
            model.addAttribute("url", "/associates/update");
            model.addAttribute("btn", "Update");
            return "associates/save";
        }
        model.addAttribute("message", "There is no person with code: " + id);
        model.addAttribute("option", "associate");
        return "mostrarmensagem";
    }

    @PostMapping("/update")
    public String update(@Valid Associate obj, BindingResult resultado, Model model) {
        if (resultado.hasErrors()) {
            return openUpdate(obj.getId(), model);
        } else {
            service.save(obj);
            model.addAttribute("message", "Deu bao");
            model.addAttribute("option", "associate");
            return "mostrarmensagem";
        }
    }

    @PostMapping("/abrirremover")
    public String openDelete(Associate obj) {
        return "associates/delete";
    }

    @PostMapping("/delete")
    public String delete(Long id, Model model) {
        Optional<Associate> opt = repository.findById(id);
        if (opt.isPresent()) {
            Associate obj = opt.get();
            obj.setStatus(Status.INACTIVE);
            service.save(obj);
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
