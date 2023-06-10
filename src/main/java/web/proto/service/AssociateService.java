package web.proto.service;

import java.time.LocalDate;

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
        obj.setDateCreated(LocalDate.now());
        repository.save(obj);
    }

}
