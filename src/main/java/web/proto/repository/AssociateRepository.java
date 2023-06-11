package web.proto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Associate;
import web.proto.model.enums.Status;
import web.proto.repository.queries.associate.AssociateQueries;

public interface AssociateRepository extends JpaRepository<Associate, Long>,  AssociateQueries {

    List<Associate> findByStatus(Status status);

}
