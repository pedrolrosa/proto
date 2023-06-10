package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
    
}
