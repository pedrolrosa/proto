package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.proto.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{
    
}
