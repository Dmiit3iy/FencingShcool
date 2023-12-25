package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.User;
import org.dmiit3iy.retrofit.ApprenticeRepository;
import org.dmiit3iy.retrofit.TrainerRepository;

import java.io.IOException;
import java.util.prefs.Preferences;

public class MainController {

    public Button buttonLogOff;
    public Label labelGreetings;
    public ListView<Trainer> listViewTrainer;
    public ListView<Apprentice> listViewApprentice;
    User user;
    TrainerRepository trainerRepository = new TrainerRepository();
    ApprenticeRepository apprenticeRepository = new ApprenticeRepository();

    public void initData(User user) {
        this.user = user;
        labelGreetings.setText("Приветствую, " + user.getName() + "!");
    }

    @FXML
    void initialize() {
        try {
            ObservableList<Trainer> trainersList = FXCollections.observableList(trainerRepository.get());
            this.listViewTrainer.setItems(trainersList);

            ObservableList<Apprentice> apprenticesList = FXCollections.observableList(apprenticeRepository.get());
            this.listViewApprentice.setItems(apprenticesList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void buttonLogOff(ActionEvent actionEvent) {
        Preferences userlog = Preferences.userRoot();
        userlog.put("authorization", "null");
        Preferences userIDlog = Preferences.userRoot();
        userlog.putBoolean("authorization", false);
        userIDlog.put("userID", "-1");
        Stage stage1 = (Stage) buttonLogOff.getScene().getWindow();
        stage1.close();
    }

    public void buttonAddTrainer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/add.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.show();

        Stage stage1 = (Stage) buttonLogOff.getScene().getWindow();
        stage1.close();
    }

    public void buttonDeleteTrainer(ActionEvent actionEvent) throws IOException {
        Trainer trainer = listViewTrainer.getSelectionModel().getSelectedItems().get(0);
        trainerRepository.delete(trainer.getId());
        ObservableList<Trainer> trainersList = FXCollections.observableList(trainerRepository.get());
        this.listViewTrainer.setItems(trainersList);
        //listViewTrainer.refresh();
    }

    public void buttonAddApprentice(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/addApprentice.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        stage.show();

        Stage stage1 = (Stage) buttonLogOff.getScene().getWindow();
        stage1.close();
    }

    public void buttonDeleteApprentice(ActionEvent actionEvent) throws IOException {
        Apprentice apprentice = listViewApprentice.getSelectionModel().getSelectedItems().get(0);
        apprenticeRepository.delete(apprentice.getId());
        ObservableList<Apprentice> apprenticeList = FXCollections.observableList(apprenticeRepository.get());
        this.listViewApprentice.setItems(apprenticeList);
    }

    public void buttonChangeTrainer(ActionEvent actionEvent) {
    }

    public void buttonChangeApprentice(ActionEvent actionEvent) {
    }
}
