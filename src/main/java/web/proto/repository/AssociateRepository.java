package web.proto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long>{
    
    Associate findByLogin(String login);

    List<Associate> findByActive(Boolean active);

}
