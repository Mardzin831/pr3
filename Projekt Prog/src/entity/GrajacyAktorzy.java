package entity;
import javax.persistence.*;
import java.io.Serializable;


@Embeddable
@Table(name = "GRAJA")
/**
 * Klasa pobierająca informacje o aktorach grających w danym filmie z bazy danch;
 */
public class GrajacyAktorzy implements Serializable {

    @Id
    @Column(name = "akt_aktor_id")
    private int aktId;
    @Id
    @Column(name = "fil_video_id")
    private int filId;

    public int getAktId() {
        return aktId;
    }

    public void setAktId(int aktId) {
        this.aktId = aktId;
    }

    public int getFilId() {
        return filId;
    }

    public void setFilId(int filId) {
        this.filId = filId;
    }
}
