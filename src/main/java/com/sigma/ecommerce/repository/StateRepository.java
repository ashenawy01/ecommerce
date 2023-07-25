package com.sigma.ecommerce.repository;

import com.sigma.ecommerce.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, Long> {
    State findByCountryCode(@Param("code") String code);
}
