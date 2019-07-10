package sample;

import entity.Filmy;
import entity.Recenzje;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa rozpoczynająca działanie listy recenzji.
 */
public class ControllerRecenzje implements Initializable {


    public TableView<Recenzje> tab2;
    public TableColumn<Recenzje, String> kolTytul;
    public TableColumn<Recenzje, Integer> kolOcena;
    public TableColumn<Recenzje, String> kolZalety;
    public TableColumn<Recenzje, String> kolWady;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Recenzje> rec = entityManager.createQuery("from Recenzje").getResultList();
    ObservableList<Recenzje> orec = FXCollections.observableArrayList(rec);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        kolTytul.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTytul()));
        kolOcena.setCellValueFactory(new PropertyValueFactory("ocena"));
        kolZalety.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getZalety()));
        kolWady.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getWady()));

        tab2.setItems(orec);

    }
}
