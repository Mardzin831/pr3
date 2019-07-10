package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "AKT")

/**
 * Klasa pobierająca informacje o aktorach z bazy danch;
 */
public class Aktorzy {

    @Id
    @Column(name = "aktor_id")
    private int id;
    @Column(name = "imie")
    private String imie;
    @Column(name = "nazwisko")
    private String nazwisko;
    @Column(name = "data_urodzenia")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "rola")
    private String rola;
    @Column(name = "szkola_aktorska")
    private String szkola_aktorska;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "graja",
            joinColumns = { @JoinColumn(name = "akt_aktor_id") },
            inverseJoinColumns = { @JoinColumn(name = "fil_video_id") }
    )
    Set<Filmy> filHash = new HashSet<>();

    public String getFilmy() {
        String filmy = "";

        Iterator<Filmy> itr = filHash.iterator();
        while(itr.hasNext()){
            filmy += itr.next().getTytul() + ", ";
        }
        if(filmy.isEmpty() == false)
        {
            filmy = filmy.substring(0, filmy.length() - 2);
        }
        return filmy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getRola() {
        return rola;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public String getSzkola_aktorska() {
        return szkola_aktorska;
    }

    public void setSzkola_aktorska(String szkola_aktorska) {
        this.szkola_aktorska = szkola_aktorska;
    }
}
