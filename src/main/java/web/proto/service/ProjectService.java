package web.proto.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.proto.model.Phase;
import web.proto.model.Project;
import web.proto.repository.PhaseRepository;
import web.proto.repository.ProjectRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private PhaseRepository phaseRepository;

    @Transactional
    public void save(Project project) {
        project.setDateCreated(LocalDate.now());
        repository.save(project);
    }

    @Transactional
    public void save(Project project, List<Phase> phases) {
        save(project);
        
        for (Phase phase : phases) {
            phase.setProject(project);
            phaseRepository.save(phase);
        }
    }

    @Transactional
    public void delete(Long id) {
        Optional<Project> opt = repository.findById(id);

        opt.ifPresent(project -> {
            project.setActive(false);
            repository.save(project);
        });
    }
}
