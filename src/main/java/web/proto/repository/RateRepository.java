package web.proto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.proto.model.Rate;

public interface RateRepository extends JpaRepository<Rate, Long> {
    
}
