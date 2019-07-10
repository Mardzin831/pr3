package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OKRESLA")
/**
 * Klasa pobierająca informacje o gatunkach danego filmu z bazy danch;
 */
public class OkreslanieGatunku implements Serializable {

    @Id
    @Column(name = "gat_nazwa")
    private String gatNazwa;
    @Id
    @Column(name = "fil_video_id")
    private int filId;

    public OkreslanieGatunku()
    {}

    /**
     * Konstruktor klasy.
     *
     * @param gatNazwa nazwa gatunku
     * @param filId id filmu
     */
    public OkreslanieGatunku(String gatNazwa, int filId) {
        this.gatNazwa = gatNazwa;
        this.filId = filId;
    }

    public String getGatNazwa() {
        return gatNazwa;
    }

    public void setGatNazwa(int gatId) {
        this.gatNazwa = gatNazwa;
    }

    public int getFilId() {
        return filId;
    }

    public void setFilId(int filId) {
        this.filId = filId;
    }
}
