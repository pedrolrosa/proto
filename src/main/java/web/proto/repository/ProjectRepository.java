package web.proto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Project;
import web.proto.repository.queries.project.ProjectQueries;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectQueries{

    List<Project> findByActive(boolean active);
    
}
