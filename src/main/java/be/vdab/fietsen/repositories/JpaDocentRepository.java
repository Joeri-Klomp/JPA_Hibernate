package be.vdab.fietsen.repositories;

import be.vdab.fietsen.domain.Docent;
import be.vdab.fietsen.projections.AantalDocentenPerWedde;
import be.vdab.fietsen.projections.IdEnEmailAdres;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
class JpaDocentRepository implements DocentRepository {
    private final EntityManager manager;

    JpaDocentRepository(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public Optional<Docent> findById(long id) {
        return Optional.ofNullable(manager.find(Docent.class, id));
    }

    @Override
    public void create(Docent docent) {
        manager.persist(docent);
    }

    @Override
    public void delete(long id) {
        findById(id)
                .ifPresent(docent -> manager.remove(docent));
    }

    @Override
    public List<Docent> findAll() {
        return manager.createQuery("SELECT d FROM Docent d order by d.wedde", Docent.class).getResultList();
    }

    @Override
    public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot) {
        return manager.createNamedQuery("Docent.findByWeddeBetween", Docent.class)
                .setParameter("van", van)
                .setParameter("tot", tot)
                .setHint("javax.persistence.loadgraph",
                        manager.createEntityGraph(Docent.MET_CAMPUS))
                .getResultList();
    }

    @Override
    public List<String> findEmailAdressen() {
        return manager.createQuery("SELECT d.emailAdres FROM Docent d", String.class)
                .getResultList();
    }

    @Override
    public List<IdEnEmailAdres> findIdsEnEmailAdressen() {
        return manager.createQuery(
                "SELECT new be.vdab.fietsen.projections.IdEnEmailAdres(d.id, d.emailAdres)" +
                        "FROM Docent d", IdEnEmailAdres.class).getResultList();
    }

    @Override
    public BigDecimal findGrootsteWedde() {
        return manager.createQuery("SELECT MAX(d.wedde) FROM Docent d", BigDecimal.class)
                .getSingleResult();
    }

    @Override
    public List<AantalDocentenPerWedde> findAantalDocentenPerWedde() {
        return manager.createQuery(
                "SELECT new be.vdab.fietsen.projections.AantalDocentenPerWedde(" +
                        "d.wedde, COUNT(d)) FROM Docent d GROUP BY d.wedde",
                AantalDocentenPerWedde.class)
                .getResultList();
    }

    @Override
    public int algemeneOpslag(BigDecimal percentage) {
        return manager.createNamedQuery("Docent.algemeneOpslag")
                .setParameter("percentage", percentage)
                .executeUpdate();
    }

}