package web.proto.repository.queries.project;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.proto.model.Project;
import web.proto.model.filter.ProjectFilter;

public interface ProjectQueries {
    
    Page<Project> searchWithFilter(ProjectFilter filter, Pageable pageable);

}
