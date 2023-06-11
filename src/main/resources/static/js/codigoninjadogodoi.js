window.addEventListener("load", function() {

	atualizarCodigoNaPagina();

	function atualizarCodigoNaPagina() {
		configurarAlertifyJS();
		prepararComponentesData();
		prepararConfirmacoes();
		mostrarNotificacaoFromHTML();
		prepararEnvioFormulariosComAJAX();
	}


	//Configuracao do AlertifyJS
	function configurarAlertifyJS() {
		alertify.defaults.transition = "slide";
		alertify.defaults.theme.ok = "btn btn-primary";
		alertify.defaults.theme.cancel = "btn btn-danger";
		alertify.defaults.theme.input = "form-control";
		alertify.set('notifier', 'position', 'top-right');
	}
	
	
	//Busca campos input com a classe dateComponent na pagina atual e liga-os com
	// o componente flatpickr.
	function prepararComponentesData() {
    	let inputsData = document.querySelectorAll(".dateComponent");
    	inputsData.forEach(function(input) {
    		flatpickr(input, {
    	    	"locale": "pt",
    	    	"dateFormat": "d/m/Y" 
    		});
    	});
    }
    
    //Busca por botoes com a classe confirmation e usa o AlertifyJS para pedir uma 
    // confirmation do usuario antes de efetuar o submit do formulario pai do botao.
    //O valor do input hidden com a classe message dentro do formulario eh exibido na pergunta.
    //Caso o usuario aceite, o formulario eh submetido, caso cancele ou feche nada acontece.
    function prepararConfirmacoes() {
    	let botoesConfirmacao = document.querySelectorAll(".confirmation");
    	botoesConfirmacao.forEach(function(botao) {
    		let formPai = botao.parentNode;
    		formPai.addEventListener("submit", function(evento) {
    			let message = "Você esqueceu de definir a message a ser exibida aqui...";   			
    			let inputMensagem = formPai.getElementsByClassName("message")[0];
    			if (inputMensagem !== undefined) {
    				message = inputMensagem.value;
    			}    				
    			evento.preventDefault();
	    		alertify.set('notifier', 'position', 'top-right');
    			alertify.confirm('Confirmação', message, aceitou, cancelou()).set('labels', { ok: 'Sim', cancel: 'Não' });

	    		function aceitou() {
	    			formPai.submit();
	    		}

	    		function cancelou() {
	    		}
	    	});
    	});
    }
    
    
    //Busca por um input hidden com o id message no HTML da página atual.
    //Caso encontre, busca por um input hidden com o id type e um input hidden com o id interval.
    //Mostra uma notificao do AlertifyJS desse type com essa message por esse intervalor.
    function mostrarNotificacaoFromHTML() {
        let inputMensagem = document.getElementById("message");
    	if (inputMensagem !== null && inputMensagem.value !== "") {
	    	message = inputMensagem.value;
	    	type = document.getElementById("type").value;
	    	interval = document.getElementById("interval").value;
            alertify.set('notifier', 'position', 'top-right');
    		alertify.notify(message, type, interval);
	    }
	}
	
	//Tratamento de formularios via AJAX
	//Para funcionar a pagina deve ter ao menos um formulario com a classe "enviarcomajax".
	//Caso o envio tenha como resposta um fragmento HTML o formulario deverá ter um atributo
	// data-iddestinoresposta com o id do elemento que recebera o fragmento HTML de resposta
	//Caso nao exista um atributo data-iddestinoresposta a resposta será colocada no body da página.
	function prepararEnvioFormulariosComAJAX() {
		let formulariosAJAX = document.querySelectorAll(".enviarcomajax");
		formulariosAJAX.forEach(function(formulario) {
			formulario.addEventListener("submit", enviarFormulario);
		});
	}

	//Funcao que envia o formulario alvo do evento (submit) via AJAX.
	//A URL da requisicao é obtida do action do formulario.
	//Os dados do formulario são enviados no formato JSON.
	//Dependendo do type da resposta recebida (FRAGMENTO, NOTIFICACAO ou  FRAGMENTO_E_NOTIFICACAO) a pagina atual é atualizada com o fragmento recebido e/ou a notificacao é exibida.
	function enviarFormulario(evento) {
		evento.preventDefault();
		let formulario = evento.target;
		let urlDestino = formulario.getAttribute("action");
		let dadosForm = new FormData(formulario);
		let dados = {};
		for (let chave of dadosForm.keys()) {
			dados[chave] = dadosForm.get(chave);
		}
		enviarViaAjax(dados, urlDestino, function(resposta) {
			//Não deveria acontecer, mas se chegou apenas texto/HTML colocamos como conteúdo do body
			if (typeof resposta === "String" || typeof resposta === "string") {
				atualizarFragmentoHTML(formulario, resposta);
			} else if (typeof resposta === 'object') {  //É o esperado que se receba
				switch (resposta.tipoResposta) {
					case 'FRAGMENTO':
						atualizarFragmentoHTML(formulario, resposta.htmlFragmento);
						break;
					case 'NOTIFICACAO':
						mostrarNotificacao(resposta.notificacao);

						break;
					case 'FRAGMENTO_E_NOTIFICACAO':
						mostrarNotificacao(resposta.notificacao);
						atualizarFragmentoHTML(formulario, resposta.htmlFragmento);
						break;
				}
			}
		}, (dados != {}) ? 'POST' : 'GET');
		return false;
	}

	//Funcao que mostra uma notificacao do AlertifyJS.
	//notificacao deve ser um objeto com os atributos message, type e interval.
	//o type deve ser um dos tipos permitidos pelo AlertifyJS: success, error, warning ou message
	function mostrarNotificacao(notificacao) {
		alertify.notify(notificacao.message, notificacao.type, notificacao.interval);
		atualizarCodigoNaPagina();
	}

	//Funcao que atualiza um pedaco da pagina atual
	//O formulario deve ter um atributo data-iddestinoresposta com o id do elemento que recebera o conteudo. Caso não exista o conteudo será colocado no body da página.
	function atualizarFragmentoHTML(formulario, conteudo) {
		let destinoresposta = document.getElementById(formulario.dataset.iddestinoresposta);
		if (destinoresposta !== null ) {
			destinoresposta.innerHTML = conteudo;	
		} else {
			document.body.innerHTML = conteudo;
		}
		atualizarCodigoNaPagina();
	}

	//Funcao responsavel pelo AJAX na página.
	//Se conteudoJSON for um objeto JSON com conteúdo ele será enviado.
	//Se conteudoJSON for vazio {} nada é enviado e nesse caso o metodo deveria ser um GET.
	//A resposta recebida via AJAX pode ser um objeto JSON ou não (HTML/texto puro).
	// De qualquer forma a funcaoSucesso é chamada passando essa resposta como argumento. 
	function enviarViaAjax(conteudoJSON, url, funcaoSucesso, metodo = 'POST', funcaoErro = function(e) { console.log(e); }) {
		console.log("Na funcao enviarViAjax");
		let xhr = new XMLHttpRequest();
		xhr.onload = function() {
			console.log(this);
			if (this.status == 200) {
				let resposta = {};
				if (this.response.startsWith('{')) {
					console.log("O type da resposta recebida é JSON");
					resposta = JSON.parse(this.response);
				} else {
					console.log("O type da resposta recebida NÃO é JSON");
					resposta = this.response;
				}
				funcaoSucesso(resposta);
			} else if ((this.status == 404) || (this.status == 403) || (this.status == 500)) {
				funcaoErro(this);
			}
		}
		xhr.onerror = function() {
			funcaoErro(this);
		}
		xhr.open(metodo, url, true);
		if (conteudoJSON !== {}) {
			xhr.setRequestHeader("Content-type", "application/json");
			xhr.send(JSON.stringify(conteudoJSON));
		} else {
			xhr.send();
		}
	}

});