package com.sigma.ecommerce.repository;

import com.sigma.ecommerce.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "countries", path = "country")
public interface CountryRepository extends JpaRepository<Country, Long> {
}
