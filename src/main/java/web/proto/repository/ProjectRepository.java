package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    
}
