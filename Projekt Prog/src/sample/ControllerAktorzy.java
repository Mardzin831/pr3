package sample;

import entity.*;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa rozpoczynająca działanie okna listy aktorów.
 */
public class ControllerAktorzy implements Initializable {


    public TableView<Aktorzy> tab4;
    public TableColumn<Aktorzy, String> kolImie;
    public TableColumn<Aktorzy, String> kolNazwisko;
    public TableColumn<Aktorzy, Date> kolData;
    public TableColumn<Aktorzy, String> kolRola;
    public TableColumn<Aktorzy, String> kolSzkola;
    public TableColumn<Aktorzy, String> kolTytuly;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Aktorzy> akt = entityManager.createQuery("from Aktorzy").getResultList();
    ObservableList<Aktorzy> oakt = FXCollections.observableArrayList(akt);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        kolImie.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getImie()));
        kolNazwisko.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwisko()));
        kolData.setCellValueFactory(new PropertyValueFactory("data"));
        kolRola.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRola()));
        kolSzkola.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSzkola_aktorska()));
        kolTytuly.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFilmy()));

        tab4.setItems(oakt);

    }
}
