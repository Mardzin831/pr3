package entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "REZ")
/**
 * Klasa pobierająca informacje o reżyserach z bazy danch;
 */
public class Rezyser {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "autor_id", updatable = false, nullable = false)
    private int id;
    @Column(name = "imie")
    private String imie;
    @Column(name = "nazwisko")
    private String nazwisko;
    @Column(name = "data_urodzenia")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "szkola_filmowa")
    private String szkola_filmowa;

    public Rezyser()
    {}

    /**
     * Konstruktor klasy.
     *
     * @param imie
     * @param nazwisko
     * @param data urodzenia
     * @param szkola_filmowa
     */
    public Rezyser(String imie, String nazwisko, Date data, String szkola_filmowa) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.data = data;
        this.szkola_filmowa = szkola_filmowa;
    }

    @OneToMany(mappedBy = "rezyser")
    /**
     * Pole przechowujące tytuły filmów tego reżysera.
     */
    private Set<Filmy> tytuly;

    public String getFilmy() {
        String filmy = "";

        Iterator<Filmy> itr = tytuly.iterator();
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

    public String getSzkola_filmowa() {
        return szkola_filmowa;
    }

    public void setSzkola_aktorska(String szkola_filmowa) {
        this.szkola_filmowa = szkola_filmowa;
    }
}