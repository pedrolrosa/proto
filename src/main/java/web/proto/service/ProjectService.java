package web.proto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.proto.model.Project;
import web.proto.repository.ProjectRepository;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository repository;

    @Transactional
    public void save(Project obj){
        repository.save(obj);
    }

    @Transactional
    public void delete(Long codigo) {
        repository.deleteById(codigo);
    }

}
