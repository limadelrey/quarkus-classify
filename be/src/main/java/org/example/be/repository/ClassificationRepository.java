package org.example.be.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.example.be.model.entity.Classification;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClassificationRepository implements PanacheRepository<Classification> {

}
