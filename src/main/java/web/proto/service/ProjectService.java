package web.proto.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.proto.model.Project;
import web.proto.repository.ProjectRepository;
import web.proto.repository.RateRepository;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository repository;

    @Autowired
    private RateRepository rateRepository;


    @Transactional
    public void create(Project project) {
        project.setDateCreated(LocalDate.now());
        repository.save(project);
    }

    @Transactional
    public List<Project> read() {
        return repository.findByActive(true);
    }

    @Transactional
    public Project read(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Project update(Project obj) {
        return repository.save(obj);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Project> opt = repository.findById(id);

        opt.ifPresent(project -> {
            project.setActive(false);
            repository.save(project);
        });
    }

    @Transactional
    public Integer getProjectScore(Project project) {
        return rateRepository.sumScoreByProject(project);
    }

}