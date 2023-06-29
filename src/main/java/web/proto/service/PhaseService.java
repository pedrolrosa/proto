package web.proto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.proto.model.Phase;
import web.proto.model.Project;
import web.proto.repository.PhaseRepository;

@Service
public class PhaseService {
    
    @Autowired
    private PhaseRepository repository;

    @Transactional
    public void create(Phase obj){
        repository.save(obj);
    }

    @Transactional
    public List<Phase> read() {
        return repository.findByActive(true);
    }

    @Transactional
    public List<Phase> read(Project project) {
        return repository.findByProject(project);
    }

    @Transactional
    public Phase update(Phase obj) {
        return repository.save(obj);
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
