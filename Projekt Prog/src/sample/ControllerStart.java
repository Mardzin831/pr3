package sample;

import entity.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.ws.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

/**
 * Klasa rozpoczynająca działanie pozostałych okienek.
 */

public class ControllerStart extends Control implements Initializable{

    public Button buttonAkt;
    public TextField addTytul, addDlugosc, addData, addOpis;
    public CheckComboBox<String> checkGatunek;
    public Label iloscFilmow;
    public static Filmy wybranyFilm;

    public TableView<Filmy> tab;
    public TableColumn<Filmy, Integer> kolId;
    public TableColumn<Filmy, String> kolTytul;
    public TableColumn<Filmy, Integer> kolDl;
    public TableColumn<Filmy, Date> kolData;
    public TableColumn<Filmy, String> kolOpis;
    public TableColumn<Filmy, String> kolGatunek;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    EntityManager entityManager = emf.createEntityManager();

    List<Gatunek> gat = entityManager.createQuery("from Gatunek").getResultList();
    ObservableList<Gatunek> ogat = FXCollections.observableArrayList(gat);

    List<Filmy> fil = entityManager.createQuery("from Filmy").getResultList();
    ObservableList<Filmy> ofil = FXCollections.observableArrayList(fil);

    /**
     * Metoda inicjalizująca działanie okna.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        kolId.setCellValueFactory(new PropertyValueFactory("id"));
        kolTytul.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTytul()));
        kolDl.setCellValueFactory(new PropertyValueFactory("dlugosc"));
        kolData.setCellValueFactory(new PropertyValueFactory("data"));
        kolOpis.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOpis()));
        kolGatunek.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGatunki()));

        tab.setItems(ofil);
        tab.getSortOrder().add(kolId);

        BigDecimal sumaFil = (BigDecimal) entityManager.createNativeQuery(
                "SELECT ilosc_filmow FROM DUAL").getSingleResult();

        iloscFilmow.setText("Suma Filmow: " + sumaFil.toString());

        for(Gatunek tmp : ogat)
        {
            checkGatunek.getItems().add(tmp.getGatunek());
        }
    }

    /**
     * Metoda rozpoczyna działanie przycisku "Lista recenzji".
     *
     * @param actionEvent
     */
    public void handleRec(ActionEvent actionEvent) {

        new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    Parent root2 = FXMLLoader.load(getClass().getResource("RecenzjeWindow.fxml"));
                    root2.setStyle("-fx-background-color: #D9FFE3;");
                    Stage stage = new Stage();
                    stage.setTitle("Recenzje");
                    stage.setScene(new Scene(root2));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }).start();

    }

    /**
     * Metoda rozpoczyna działanie przycisku "Lista rezyserów".
     *
     * @param actionEvent
     */
    public void handleRez(ActionEvent actionEvent) {

        new Thread(() -> {
            Platform.runLater(() -> {

                try {
                    Parent root3 = FXMLLoader.load(getClass().getResource("RezyserWindow.fxml"));
                    root3.setStyle("-fx-background-color: #FFD1B5;");
                    Stage stage = new Stage();
                    stage.setTitle("Rezyserzy");
                    stage.setScene(new Scene(root3));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }).start();
    }

    /**
     * Metoda rozpoczyna działanie przycisku "Lista aktorów".
     *
     * @param actionEvent
     */
    public void handleAkt(ActionEvent actionEvent) {

        new Thread(() -> {
            Platform.runLater(() -> {

                try {
                    Parent root4 = FXMLLoader.load(getClass().getResource("AktorzyWindow.fxml"));
                    root4.setStyle("-fx-background-color: #F5BADA;");
                    Stage stage = new Stage();
                    stage.setTitle("Aktorzy");
                    stage.setScene(new Scene(root4));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }).start();

    }

    /**
     * Metoda obsługuje dodawanie filmów do listy.
     *
     * @param actionEvent
     */
    public void handleAddFil(ActionEvent actionEvent) throws ParseException {

        if(!addTytul.getText().isEmpty() && !addDlugosc.getText().isEmpty() && !addData.getText().isEmpty() && !addOpis.getText().isEmpty())
        {
            Filmy tmpFilmy = new Filmy(addTytul.getText(), Integer.parseInt(addDlugosc.getText()),
                    new SimpleDateFormat("yyyy-MM-dd").parse(addData.getText()), addOpis.getText());

            entityManager.getTransaction().begin();
            entityManager.persist(tmpFilmy);
            entityManager.flush();

            for(String tmp : checkGatunek.getCheckModel().getCheckedItems())
            {
                OkreslanieGatunku tmpOG = new OkreslanieGatunku(tmp, tmpFilmy.getId());
                entityManager.persist(tmpOG);
                entityManager.flush();
            }

            entityManager.persist(tmpFilmy);
            entityManager.getTransaction().commit();

            fil = entityManager.createQuery("from Filmy").getResultList();
            ofil = FXCollections.observableArrayList(fil);

            tab.setItems(ofil);
            tab.getSortOrder().add(kolId);

            addTytul.clear();
            addDlugosc.clear();
            addData.clear();
            addOpis.clear();
        }
    }

    /**
     * Metoda obsługuje usuwanie filmów z listy.
     *
     * @param actionEvent
     */
    public void handleDelFil(ActionEvent actionEvent) {

        if(tab.getSelectionModel().getSelectedItem() != null)
        {
            entityManager.getTransaction().begin();
            entityManager.remove(tab.getSelectionModel().getSelectedItem());
            entityManager.getTransaction().commit();
            tab.getItems().removeAll(tab.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Metoda rozpoczyna działanie przycisku "Recenzje danego filmu".
     *
     * @param actionEvent
     */
    public void handleShowRec(ActionEvent actionEvent) {

        wybranyFilm = tab.getSelectionModel().getSelectedItem();

        new Thread(() -> {
            Platform.runLater(() -> {

                if(wybranyFilm != null)
                {
                    try {
                        Parent root5 = FXMLLoader.load(getClass().getResource("ShowRecWindow.fxml"));
                        root5.setStyle("-fx-background-color: #E3FFA6;");
                        Stage stage = new Stage();
                        stage.setTitle("Recenzje Filmu");
                        stage.setScene(new Scene(root5));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
        }).start();
    }

    /**
     * Metoda rozpoczyna działanie przycisku "Reżyser danego filmu".
     *
     * @param actionEvent
     */
    public void handleShowRez(ActionEvent actionEvent) {

        wybranyFilm = tab.getSelectionModel().getSelectedItem();

        new Thread(() -> {
            Platform.runLater(() -> {

                if(wybranyFilm != null)
                {
                    try {
                        Parent root6 = FXMLLoader.load(getClass().getResource("ShowRezWindow.fxml"));
                        root6.setStyle("-fx-background-color: #E8D1E7;");
                        Stage stage = new Stage();
                        stage.setTitle("Reżyser Filmu");
                        stage.setScene(new Scene(root6));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
        }).start();
    }

    /**
     * Metoda rozpoczyna działanie przycisku "Recenzje danego filmu".
     *
     * @return wybrany film.
     */
    public Filmy getWybranyFilm()
    {
        return wybranyFilm;
    }
}
