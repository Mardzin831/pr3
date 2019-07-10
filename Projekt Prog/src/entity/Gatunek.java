package entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GAT")
/**
 * Klasa pobierająca informacje o gatunkach z bazy danch;
 */
public class Gatunek
{
    public Gatunek()
    {}

    @Id
    @Column(name = "nazwa")
    private String nazwa;

    @ManyToMany(mappedBy = "gatHash")
    /**
     * Prywatne pole przechowujące filmy należące do danego gatunku.
     */
    private Set<Filmy> filmy = new HashSet<>();

    public Gatunek (String nazwa) {
        this.nazwa = nazwa;
    }

    public String getGatunek() {
        return nazwa;
    }

    public void setGatunek(String nazwa) {
        this.nazwa = nazwa;
    }
}