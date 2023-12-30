package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.User;
import org.dmiit3iy.retrofit.ApprenticeRepository;
import org.dmiit3iy.retrofit.TrainerRepository;
import org.dmiit3iy.retrofit.UserRepository;

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
    UserRepository userRepository = new UserRepository();

    public void initData(User user) {
        this.user = user;
        labelGreetings.setText("Приветствую, " + user.getName() + "!");
    }

    @FXML
    void initialize() {

        try {
            Preferences userIDlog = Preferences.userRoot();
            int userId = Integer.parseInt(userIDlog.get("userID", "-1"));
            labelGreetings.setText("Приветствую, " + userRepository.get(userId).getName() + "!");

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

    @FXML
    public void buttonAddTrainer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/add.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));


        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    System.out.println("список тренеров при закрытии:" + trainerRepository.get());
                    ObservableList<Trainer> trainersList1 = FXCollections.observableList(trainerRepository.get());
                    ObservableList<Apprentice> apprenticesList1 = FXCollections.observableList(apprenticeRepository.get());
                    System.out.println("по закрытию" + trainersList1);
                    listViewTrainer.setItems(trainersList1);
                    listViewApprentice.setItems(apprenticesList1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        stage.show();
//        Stage stage1 = (Stage) buttonLogOff.getScene().getWindow();
//        stage1.close();
    }

    @FXML
    public void buttonDeleteUser(ActionEvent actionEvent) throws IOException {
        userRepository.delete(user.getId());
        Stage stage = (Stage) buttonLogOff.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/authorization.fxml"));
        Stage stage2 = new Stage(StageStyle.DECORATED);
        stage2.setScene(new Scene(loader.load()));
        stage.close();
        stage2.show();

    }


    @FXML
    public void handleMouseTrainer(MouseEvent mouseEvent) throws IOException {
        Trainer trainer = listViewTrainer.getSelectionModel().getSelectedItems().get(0);
        System.out.println(trainer);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/schedule.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        ScheduleController scheduleController = loader.getController();
        scheduleController.initData(trainer);

        //Действие по закрытию модальной формы
        //Действие по закрытию модальной формы

        stage.setOnCloseRequest(event -> {
                    try {
                        ObservableList<Trainer> trainersList1 = FXCollections.observableList(trainerRepository.get());
                        System.out.println("по закрытию" + trainersList1);
                        this.listViewTrainer.setItems(trainersList1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
        );


        stage.show();


    }

    @FXML
    public void handleMouseApprentice(MouseEvent mouseEvent) throws IOException {
        Apprentice apprentice = listViewApprentice.getSelectionModel().getSelectedItems().get(0);

        System.out.println(apprentice);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/apprentice.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        ApprenticeController apprenticeController = loader.getController();
        apprenticeController.initData(apprentice);

        //Действие по закрытию модальной формы
        //Действие по закрытию модальной формы

        stage.setOnCloseRequest(event -> {
                    try {
                        ObservableList<Apprentice> apprenticesList1 = FXCollections.observableList(apprenticeRepository.get());
                        System.out.println("по закрытию" + apprenticesList1);
                        this.listViewApprentice.setItems(apprenticesList1);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
        );


        stage.show();

    }
}
