package be.vdab.fietsen.domain;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "verantwoordelijkheden")
public class Verantwoordelijkheid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    @ManyToMany
    @JoinTable(
            name = "docentenverantwoordelijkheden",
            joinColumns = @JoinColumn(name = "verantwoordelijkheidId"),
            inverseJoinColumns = @JoinColumn(name = "docentId"))
    private Set<Docent> docenten = new LinkedHashSet<>();

    protected Verantwoordelijkheid() {
    }

    public Verantwoordelijkheid(String naam) {
        this.naam = naam;
    }

    public boolean add(Docent docent) {
        var toegevoegd = docenten.add(docent);
        if(! docent.getVerantwoordelijkheden().contains(this)){
            docent.add(this);
        }
        return toegevoegd;
    }

    public boolean remove(Docent docent) {
        var toegevoegd = docenten.remove(docent);
        if(docent.getVerantwoordelijkheden().contains(this)){
            docent.remove(this);
        }
        return toegevoegd;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public Set<Docent> getDocenten() {
        return docenten;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Verantwoordelijkheid verantwoordelijkheid
                && naam.equalsIgnoreCase(verantwoordelijkheid.naam);
    }

    @Override
    public int hashCode() {
        return naam.toLowerCase().hashCode();
    }
}
