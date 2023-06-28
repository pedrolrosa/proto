package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Associate;
import web.proto.model.Project;
import web.proto.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Boolean existsByAssociateAndProject(Associate associate, Project project);
    
}

