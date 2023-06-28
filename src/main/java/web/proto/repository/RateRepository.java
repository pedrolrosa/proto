package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import web.proto.model.Associate;
import web.proto.model.Project;
import web.proto.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {

    Boolean existsByAssociateAndProject(Associate associate, Project project);

    @Query("SELECT SUM(r.score) FROM Rate r WHERE r.project = :project")
    Integer sumScoreByProject(Project project);
    
}

