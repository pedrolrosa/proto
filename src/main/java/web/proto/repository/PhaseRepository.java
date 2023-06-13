package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.proto.model.Phase;

@Repository
public interface PhaseRepository extends JpaRepository<Phase, Long>{
    
}
