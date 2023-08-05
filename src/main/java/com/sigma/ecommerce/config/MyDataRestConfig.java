package com.sigma.ecommerce.config;

import com.sigma.ecommerce.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Configuration
@AllArgsConstructor
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedAction = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

        // disable HTTP methods for ProductCategory: PUT, POST and DELETE
        disableHttpMethods(Product.class, config, theUnsupportedAction);
        disableHttpMethods(ProductCategory.class, config, theUnsupportedAction);
        disableHttpMethods(Country.class, config, theUnsupportedAction);
        disableHttpMethods(State.class, config, theUnsupportedAction);
        disableHttpMethods(Order.class, config, theUnsupportedAction);

        // expose entity ids
        Class[] classes = entityManager.getMetamodel()
                .getEntities().stream().map(EntityType::getJavaType).toArray(Class[]::new);
        config.exposeIdsFor(classes);

        // Configure cors mapping
        cors.addMapping(config.getBasePath() + "/**").allowedOrigins((theAllowedOrigins));
    }
    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] unWantedHTTPMethods) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure(((metdata, httpMethods) -> httpMethods.disable(unWantedHTTPMethods)))
                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(unWantedHTTPMethods)));


    }
//
//    private void exposeIds(RepositoryRestConfiguration config) {
//
//        // - get a list of all entity classes from the entity manager
//        Set<EntityType<?>> entityTypes = entityManager.getMetamodel().getEntities();
//
//        // - create an array of the entity types
//        List<Class> entityClasses = new ArrayList<>();
//
//        for (EntityType  entityType  : entityTypes) {
//            entityClasses.add(entityType.getJavaType());
//        }
//
//        // - expose the entity ids for the array of entity/domain types
//        Class[] domainTypes = entityClasses.toArray(new Class[0]);
//        config.exposeIdsFor(domainTypes);
//    }
}
