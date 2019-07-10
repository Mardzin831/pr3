package entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "REC")
/**
 * Klasa pobierająca informacje o recenzjach z bazy danch;
 */
public class Recenzje {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "oceny_id", updatable = false, nullable = false)
    private int id;
    @Column(name = "ocena")
    private int ocena;
    @Column(name = "zalety")
    private String zalety;
    @Column(name = "wady")
    private String wady;
    @Column(name = "fil_video_id")
    private int filId;

    public Recenzje()
    {}

    /**
     * Konstruktor klasy.
     *
     * @param ocena
     * @param zalety
     * @param wady
     */
    public Recenzje(int ocena, String zalety, String wady, int filId)
    {
        this.ocena = ocena;
        this.zalety = zalety;
        this.wady = wady;
        this.filId = filId;
    }

    @ManyToOne
    @JoinColumn(name="fil_video_id", insertable = false, updatable = false)
    /**
     * Prywatne pole przechowujące filmy.
     */
    private Filmy filmy;

    public String getTytul()
    {
        return filmy.getTytul();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getZalety() {
        return zalety;
    }

    public void setZalety(String zalety) {
        this.zalety = zalety;
    }

    public String getWady() {
        return wady;
    }

    public void setWady(String wady) {
        this.wady = wady;
    }

    public int getFilId() {
        return filId;
    }

    public void setFilId(int filId) {
        this.filId = filId;
    }
}
