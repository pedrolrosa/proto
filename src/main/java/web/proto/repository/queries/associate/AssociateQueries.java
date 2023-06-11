package web.proto.repository.queries.associate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import web.proto.model.Associate;
import web.proto.model.filter.AssociateFilter;

public interface AssociateQueries {
    
    Page<Associate> filtrar(AssociateFilter filtro, Pageable pageable);

}
