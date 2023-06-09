package web.proto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Phase;
import web.proto.model.Project;

public interface PhaseRepository extends JpaRepository<Phase, Long>{

    Phase findByIdAndActiveTrue(Long id);

    List<Phase> findByProjectAndActiveTrue(Project project);
    
    List<Phase> findByActive(Boolean active);

}
