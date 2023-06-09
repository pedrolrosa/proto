package web.proto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void delete(Long codigo) {
        repository.deleteById(codigo);
    }

}