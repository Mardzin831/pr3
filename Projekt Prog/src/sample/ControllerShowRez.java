package sample;

import entity.Filmy;
import entity.Recenzje;
import entity.Rezyser;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa rozpoczynająca działanie reżysera danego filmu.
 */
public class ControllerShowRez implements Initializable {

    public Filmy tmp = new ControllerStart().getWybranyFilm();
    public Label showTytul;
    public TextField chooseRez;

    public TableView<Rezyser> tab6;
    public TableColumn<Rezyser, String> kolId;
    public TableColumn<Rezyser, String> kolImie;
    public TableColumn<Rezyser, String> kolNazwisko;
    public TableColumn<Rezyser, Date> kolData;
    public TableColumn<Rezyser, String> kolSzkola;
    public TableColumn<Rezyser, String> kolTytuly;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolImie.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getImie()));
        kolNazwisko.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwisko()));
        kolData.setCellValueFactory(new PropertyValueFactory("data"));
        kolSzkola.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSzkola_filmowa()));
        kolTytuly.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFilmy()));

        showTytul.setText(tmp.getTytul());

        if(tmp.getRezyser() != null)
        {
            tab6.getItems().setAll(new ControllerStart().getWybranyFilm().getRezyser());
        }
    }

    /**
     * Metoda obługuje przydzielenie wybranego reżysera do filmu.
     *
     * @param actionEvent
     */
    public void handleUstawRez(ActionEvent actionEvent) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
        EntityManager entityManager = emf.createEntityManager();

        List<Rezyser> rez = entityManager.createQuery("from Rezyser where autor_id =" + chooseRez.getText()).getResultList();
        ObservableList<Rezyser> orez = FXCollections.observableArrayList(rez);

        if(orez.isEmpty() == false)
        {
            entityManager.getTransaction().begin();
            tmp.setRezId(Integer.parseInt(chooseRez.getText()));
            entityManager.merge(tmp);
            entityManager.getTransaction().commit();
            tab6.setItems(orez);
        }

    }
}
