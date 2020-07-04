package org.example.be.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.example.be.model.entity.Classification;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ClassificationRepository implements PanacheRepository<Classification> {

    public Classification findById(UUID id) {
        return find("id = ?1", id).firstResult();
    }

}
