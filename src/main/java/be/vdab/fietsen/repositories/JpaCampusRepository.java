package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Campus;

import javax.persistence.EntityManager;
import java.util.Optional;

public class JpaCampusRepository implements CampusRepository{
    private final EntityManager manager;

    public JpaCampusRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void create(Campus campus) {
        manager.persist(campus);
    }

    @Override
    public Optional<Campus> findById(long id) {
        return Optional.ofNullable(manager.find(Campus.class, id));
    }
}
