package web.proto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Long>{
    
    List<Phase> findByActive(Boolean active);

}
