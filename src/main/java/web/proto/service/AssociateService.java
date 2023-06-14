package web.proto.service;

import java.time.LocalDate;
import java.util.List;
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
    public void create(Associate obj) {
        obj.setDateCreated(LocalDate.now());
        repository.save(obj);
    }

    @Transactional
    public List<Associate> read() {
        return repository.findAll();
    }

    @Transactional
    public Associate update(Associate obj) {
        return repository.save(obj);
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
