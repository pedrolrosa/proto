package web.proto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import web.proto.model.Associate;
import web.proto.model.Project;
import web.proto.model.Rate;
import web.proto.repository.RateRepository;

@Service
public class RateService {
    
    @Autowired
    private RateRepository repository;

    @Transactional
    public void save(Rate obj) {
        repository.save(obj);
    }

    @Transactional
    public List<Rate> read(){
        return repository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Boolean hasBoosted(Associate associate, Project project){

        if(repository.findByAssociateAndProject(associate, project) != null){
            return true;
        }

        return false;
    }

}
