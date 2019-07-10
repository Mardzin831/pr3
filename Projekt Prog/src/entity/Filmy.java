package entity;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "FIL")
/**
 * Klasa pobierająca informacje o filmach z bazy danch;
 */
public class Filmy
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "video_id", updatable = false, nullable = false)
    private int id;
    @Column(name = "tytul")
    private String tytul;
    @Column(name = "dlugosc")
    private int dlugosc;
    @Column(name = "data_wydania")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "opis")
    private String opis;

    @Column(name = "rez_autor_id")
    private Integer rezId;


    @ManyToOne
    @JoinColumn(name = "rez_autor_id", insertable = false, updatable = false)
    /**
     * Prywatne pole przechowujące reżysera danego filmu.
     */
    private Rezyser rezyser;

    @OneToMany(mappedBy = "filmy", orphanRemoval = true)
    /**
     * Prywatne pole przechowujące recenzje dango filmu.
     */
    private Set<Recenzje> recenzje;

    @ManyToMany(mappedBy = "filHash")
    /**
     * Prywatne pole przechowujące aktorów danego filmu.
     */
    private Set<Aktorzy> aktorzy = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "okresla",
            joinColumns = { @JoinColumn(name = "fil_video_id") },
            inverseJoinColumns = { @JoinColumn(name = "gat_nazwa") }
    )
    /**
     * Prywatne pole przechowujące filmy danego gatunku.
     */
    private Set<Gatunek> gatHash = new HashSet<>();

    public Filmy()
    {}


    /**
     * Konstruktor klasy.
     *
     * @param tytul
     * @param dlugosc w min
     * @param data wydanie
     * @param opis
     */
    public Filmy(String tytul, int dlugosc, Date data, String opis) {
        this.tytul = tytul;
        this.dlugosc = dlugosc;
        this.data = data;
        this.opis = opis;
    }

    public String getGatunki() {
        String gatunki = "";
        Iterator<Gatunek> itr = gatHash.iterator();
        while(itr.hasNext()){
            gatunki += itr.next().getGatunek() + ", ";
        }
        if(gatunki.isEmpty() == false)
        {
            gatunki = gatunki.substring(0, gatunki.length() - 2);
        }
        return gatunki;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getRezId() {
        return rezId;
    }

    public void setRezId(Integer rezId) {
        this.rezId = rezId;
    }
    public void setRezyser(Rezyser rezyser) {
        this.rezyser = rezyser;
    }

    public Rezyser getRezyser() {
        return rezyser;
    }

}