package sample;

import entity.Filmy;
import entity.Recenzje;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa rozpoczynająca działanie recenzji danego filmu.
 */
public class ControllerShowRec implements Initializable {

    String wybranyTytul = new ControllerStart().getWybranyFilm().getTytul();
    int wybraneId = new ControllerStart().getWybranyFilm().getId();
    public Label showTytul;
    public TextField addOcena;
    public TextField addZalety;
    public TextField addWady;

    public TableView<Recenzje> tab5;
    public TableColumn<Recenzje, Integer> kolOcena;
    public TableColumn<Recenzje, String> kolZalety;
    public TableColumn<Recenzje, String> kolWady;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Recenzje> rec = entityManager.createQuery("from Recenzje where fil_video_id = " + wybraneId).getResultList();
    ObservableList<Recenzje> orec = FXCollections.observableArrayList(rec);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showTytul.setText(wybranyTytul);

        kolOcena.setCellValueFactory(new PropertyValueFactory("ocena"));
        kolZalety.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getZalety()));
        kolWady.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getWady()));

        tab5.setItems(orec);
        tab5.getSortOrder().add(kolOcena);
    }

    /**
     * Metoda obługuje dodawanie nowych rcenzji do wybranego filmu.
     *
     * @param actionEvent
     */
    public void handleAddRec(ActionEvent actionEvent) {

        if(!addOcena.getText().isEmpty())
        {
            Recenzje tmpRecenzje = new Recenzje(Integer.parseInt(addOcena.getText()), addZalety.getText(), addWady.getText(), wybraneId);

            entityManager.getTransaction().begin();
            entityManager.persist(tmpRecenzje);
            entityManager.getTransaction().commit();

            rec = entityManager.createQuery("from Recenzje where filId = " + wybraneId).getResultList();
            orec = FXCollections.observableArrayList(rec);

            tab5.setItems(orec);
            tab5.getSortOrder().add(kolOcena);

            addOcena.clear();
            addZalety.clear();
            addWady.clear();
        }
    }

    /**
     * Metoda obługuje usuwanie recenzji.
     *
     * @param actionEvent
     */
    public void handleDelRec(ActionEvent actionEvent){

        if(tab5.getSelectionModel().getSelectedItem() != null)
        {
            entityManager.getTransaction().begin();
            entityManager.remove(tab5.getSelectionModel().getSelectedItem());
            entityManager.getTransaction().commit();
            tab5.getItems().removeAll(tab5.getSelectionModel().getSelectedItem());
        }
    }
}
