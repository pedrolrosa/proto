package web.proto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.proto.model.Phase;
import web.proto.repository.PhaseRepository;

@Service
public class PhaseService {
    
    @Autowired
    private PhaseRepository repository;

    @Transactional
    public void save(Phase obj){
        repository.save(obj);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Phase> opt = repository.findById(id);

        opt.ifPresent(phase -> {
            phase.setActive(false);
            repository.save(phase);
        });
    }

}
