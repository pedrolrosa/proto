package web.proto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.proto.model.Associate;
import web.proto.repository.AssociateRepository;

@Service
public class AssociateService {
    
    @Autowired
    private AssociateRepository repository;

    @Transactional
    public void save(Associate obj) {
        repository.save(obj);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Associate> opt = repository.findById(id);

        opt.ifPresent(associate -> {
            associate.setActive(false);
            repository.save(associate);
        });
    }

}
