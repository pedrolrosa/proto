package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Phase;

public interface PhaseRepository extends JpaRepository<Phase, Long>{
    
}
