package web.proto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

    List<Project> findByActive(boolean active);
    
}
