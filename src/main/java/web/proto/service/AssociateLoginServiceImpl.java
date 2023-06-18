package web.proto.service;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.proto.model.Associate;
import web.proto.repository.AssociateRepository;

@Service
public class AssociateLoginServiceImpl implements AssociateLoginService{

    @Autowired
	private AssociateRepository repository;

	@Override
	public boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException {
		if (!fieldName.equals("login")) {
			throw new UnsupportedOperationException("A anotação deveria ser usada no atributo login");
		}

		Associate novo = (Associate) value;
		//A validacao se foi preenchido um login nao eh obrigacao dessa verificacao
		if (novo.getLogin() == null || novo.getLogin().isBlank()) {
			return true;
		}
		
		//Busca um usuario com esse login
		Associate comEsseNomeUsuario = repository.findByLoginIgnoreCase(novo.getLogin());
		
		//Nao existe um usuario com esse login, entao tudo bem
		if (comEsseNomeUsuario == null) {
			return true;
		} else {  //Existe um contato com esse login
			//Estao tentando validar um novo usuario com um login que ja existe 
			if (novo.getId() == null) {
				return false;
			} else {  //O usuario sendo validado ja existe
				Associate antigo = repository.findById(novo.getId()).orElseThrow(() -> new InvalidParameterException("O código do usuario a validar não existe."));
				// Se o usuario sendo validado for o mesmo que ja existia no BD entao tudo bem
				if (comEsseNomeUsuario.equals(antigo)) {
					return true;
				}
				// Senao eh pq estao tentando validar um login que eh de outro usuario
				return false;
			}
		}
	}
    
}
