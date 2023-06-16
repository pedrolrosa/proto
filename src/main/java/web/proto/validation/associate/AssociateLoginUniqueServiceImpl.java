package web.proto.validation.associate;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import web.proto.model.Associate;
import web.proto.repository.AssociateRepository;

@Service
public class AssociateLoginUniqueServiceImpl implements AssociateLoginUniqueService {

    @Autowired
    private AssociateRepository repository;

    @Override
    public boolean isValueUnique(Object value, String fieldName) throws UnsupportedOperationException {
        if (!fieldName.equals("login")) {
            throw new UnsupportedOperationException("Anotation for login");
        }

        Associate obj = (Associate) value;
        String login = obj.getLogin();

        if (login == null || login.isBlank()) {
            return true;
        }

        Associate exists = repository.findByLogin(login);

        if (exists == null) {
            return true;
        } else {
            if (obj.getId() == null) {
                return false;
            } else {
                Associate actual = repository.findById(obj.getId())
                        .orElseThrow(() -> 
                            new InvalidParameterException(
                                "O código do usuario a validar não existe."));

                if (exists.equals(actual)) {
                    return true;
                }

                return false;
            }
        }

    }

}
