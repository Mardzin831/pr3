package sample;

import entity.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Klasa rozpoczynająca działanie listy reżyserów.
 */
public class ControllerRezyser implements Initializable {

    public TextField addImie;
    public TextField addNazwisko;
    public TextField addData;
    public TextField addSzkola;

    public TableView<Rezyser> tab3;
    public TableColumn<Rezyser, Integer> kolId;
    public TableColumn<Rezyser, String> kolImie;
    public TableColumn<Rezyser, String> kolNazwisko;
    public TableColumn<Rezyser, Date> kolData;
    public TableColumn<Rezyser, String> kolSzkola;
    public TableColumn<Rezyser, String> kolTytuly;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Rezyser> rez = entityManager.createQuery("from Rezyser").getResultList();
    ObservableList<Rezyser> orez = FXCollections.observableArrayList(rez);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolImie.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getImie()));
        kolNazwisko.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNazwisko()));
        kolData.setCellValueFactory(new PropertyValueFactory("data"));
        kolSzkola.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSzkola_filmowa()));
        kolTytuly.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getFilmy()));

        tab3.setItems(orez);
        tab3.getSortOrder().add(kolId);
    }

    /**
     * Metoda obługuje dodawanie nowych reżyserów.
     *
     * @param actionEvent
     */
    public void handleAddRez(ActionEvent actionEvent) throws ParseException {

        if(!addImie.getText().isEmpty() && !addNazwisko.getText().isEmpty())
        {
            Date tmpDate = null;
            if(!addData.getText().isEmpty())
            {
                tmpDate = new SimpleDateFormat("yyyy-MM-dd").parse(addData.getText());
            }
            Rezyser tmpRezyser = new Rezyser(addImie.getText(), addNazwisko.getText(),
                   tmpDate, addSzkola.getText());

            entityManager.getTransaction().begin();
            entityManager.persist(tmpRezyser);
            entityManager.getTransaction().commit();

            rez = entityManager.createQuery("from Rezyser").getResultList();
            orez = FXCollections.observableArrayList(rez);

            tab3.setItems(orez);
            tab3.getSortOrder().add(kolId);

            addImie.clear();
            addNazwisko.clear();
            addData.clear();
            addSzkola.clear();
        }
    }

    /**
     * Metoda obługuje usuwanie reżyserów.
     *
     * @param actionEvent
     */
    public void handleDelRez(ActionEvent actionEvent){

        if(tab3.getSelectionModel().getSelectedItem() != null)
        {
            entityManager.getTransaction().begin();
            entityManager.remove(tab3.getSelectionModel().getSelectedItem());
            entityManager.getTransaction().commit();
            tab3.getItems().removeAll(tab3.getSelectionModel().getSelectedItem());
        }
    }
}
