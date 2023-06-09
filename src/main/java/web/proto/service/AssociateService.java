package web.proto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import web.proto.model.Associate;
import web.proto.repository.AssociateRepository;

@Service
public class AssociateService {
    
    @Autowired
    private AssociateRepository repository;

    @Transactional
    public void save(Associate obj){
        repository.save(obj);
    }

}
