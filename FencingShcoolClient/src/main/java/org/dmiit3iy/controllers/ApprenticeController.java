package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmiit3iy.App;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.model.TrainerScheduleForTable;
import org.dmiit3iy.model.Training;
import org.dmiit3iy.retrofit.ApprenticeRepository;
import org.dmiit3iy.retrofit.TrainingRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApprenticeController {
    private TrainingRepository trainingRepository = new TrainingRepository();
    private ApprenticeRepository apprenticeRepository = new ApprenticeRepository();
    private List<Training> trainingArrayList = new ArrayList<>();
    public TextField textFieldSurname;
    public TextField textFieldName;
    public TextField textFieldPatronymic;
    public TextField textFieldPhone;
    public ListView <Training> listViewTraining;

    private Apprentice apprentice;

    @FXML
    public void buttonUpdateApprentice(ActionEvent actionEvent) throws IOException {
        apprentice.setName(textFieldName.getText());
        apprentice.setSurname(textFieldSurname.getText());
        apprentice.setPatronymic(textFieldPatronymic.getText());
        apprentice.setPhoneNumber(textFieldPhone.getText());
        this.apprenticeRepository.update(apprentice);
        App.showMessage("Success", "the apprentice has been successfully update", Alert.AlertType.INFORMATION);
        clearFields();
    }

    @FXML
    public void buttonRemoveApprentice(ActionEvent actionEvent) throws IOException {
        this.apprenticeRepository.delete(apprentice.getId());
        App.showMessage("Success", "the apprentice has been successfully delete", Alert.AlertType.INFORMATION);
        clearFields();
    }

    @FXML
    public void buttonAddTraining(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/addTraining.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        AddTrainingController addTrainingController = loader.getController();
        addTrainingController.initData(apprentice);

        stage.setOnCloseRequest(event -> {
                    try {
                        List<Training> trainingList = trainingRepository.getByIdApprentice(apprentice.getId());
                        ObservableList<Training> observableList = FXCollections.observableArrayList(trainingList);
                        listViewTraining.setItems(observableList);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        stage.show();
    }

    @FXML
    public void buttonRemoveTraining(ActionEvent actionEvent) throws IOException {

        try {
            trainingRepository.delete(listViewTraining.getSelectionModel().getSelectedItems().get(0).getId());
            App.showMessage("Success", "training has been successfully delete", Alert.AlertType.INFORMATION);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        trainingRepository.getByIdApprentice(apprentice.getId());
        ObservableList<Training> observableList = FXCollections.observableArrayList(trainingArrayList);
        listViewTraining.setItems(observableList);

    }

    public void initData(Apprentice apprentice) {
        this.apprentice = apprentice;
        this.textFieldName.setText(apprentice.getName());
        this.textFieldPatronymic.setText(apprentice.getPatronymic());
        this.textFieldSurname.setText(apprentice.getSurname());
        this.textFieldPhone.setText(apprentice.getPhoneNumber());
        try {
            if (!trainingRepository.getByIdApprentice(apprentice.getId()).isEmpty()) {
                trainingArrayList.addAll(trainingRepository.getByIdApprentice(apprentice.getId()));
                ObservableList<Training> observableList = FXCollections.observableArrayList(trainingArrayList);
                listViewTraining.setItems(observableList);
                System.out.println(trainingArrayList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void clearFields() {
        textFieldSurname.clear();
        textFieldName.clear();
        textFieldPatronymic.clear();
        textFieldPhone.clear();

    }
}
