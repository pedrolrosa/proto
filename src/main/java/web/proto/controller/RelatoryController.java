package web.proto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.proto.service.RelatoryService;

@Controller
@RequestMapping("/relatory")
public class RelatoryController {

	private static final Logger logger = LoggerFactory.getLogger(RelatoryController.class);
	
	@Autowired
	private RelatoryService relatorioService;
	
	@GetMapping
	public ResponseEntity<byte[]> gerarRelatorioSimplesTodasPessoas() {
		
		byte[] relatorio = relatorioService.generateRelatory();
		
		logger.debug("Relatório simples de todas as projects gerado");
		logger.debug("Retornando o relatório simples de todas as projects");
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Projects.pdf")
				.body(relatorio);
	}
	
}
