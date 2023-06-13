package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import web.proto.model.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    
}

