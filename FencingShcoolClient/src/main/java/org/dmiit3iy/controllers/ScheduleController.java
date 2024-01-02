package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dmiit3iy.App;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.model.TrainerScheduleForTable;
import org.dmiit3iy.retrofit.ScheduleRepository;
import org.dmiit3iy.retrofit.TrainerRepository;
import org.dmiit3iy.util.Constants;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class ScheduleController {
    public TableView<TrainerScheduleForTable> tableViewSchedule;

    private ScheduleRepository scheduleRepository = new ScheduleRepository();
    private TrainerRepository trainerRepository = new TrainerRepository();
    private Trainer trainer;
    private TrainerSchedule trainerSchedule;
    @FXML
    public TextField textFieldSurname = new TextField();
    @FXML
    public TextField textFieldName = new TextField();
    @FXML
    public TextField textFieldPatronymic = new TextField();
    @FXML
    public TextField textFieldExperience = new TextField();

    @FXML

    public void buttonAddSchedule(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/addSchedule.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(loader.load()));
        AddScheduleController addScheduleController = loader.getController();
        addScheduleController.initData(trainerSchedule, trainer);

        stage.setOnCloseRequest(event -> {
                    try {
                        TrainerSchedule trainerSchedule1 = scheduleRepository.get(trainer.getId());
                        if(trainerSchedule1!=null) {
                            List<TrainerScheduleForTable> list = trainerSchedule1.convertScheduleForTable();
                            ObservableList<TrainerScheduleForTable> observableList = FXCollections.observableArrayList(list);
                            tableViewSchedule.setItems(observableList);
                        } else {
                            TrainerSchedule emptyTrainerSchedule = new TrainerSchedule();
                            List<TrainerScheduleForTable> list = emptyTrainerSchedule .convertScheduleForTable();
                            ObservableList<TrainerScheduleForTable> observableList = FXCollections.observableArrayList(list);
                            tableViewSchedule.setItems(observableList);
                        }

                    } catch (IllegalAccessException | NoSuchFieldException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        stage.show();
    }

    @FXML
    public void buttonRemoveSchedule(ActionEvent actionEvent) throws IOException, NoSuchFieldException, IllegalAccessException {
        if(!tableViewSchedule.getSelectionModel().isEmpty()) {
            TableView.TableViewSelectionModel<TrainerScheduleForTable> selectionModel = tableViewSchedule.getSelectionModel();

            if (selectionModel.getSelectedItems()==null) {
                String day = Constants.weekDays.get(selectionModel.selectedItemProperty().get().getDay());
                tableViewSchedule.getSelectionModel().getSelectedItem().getDay();
                scheduleRepository.delete(trainer.getId(), day);

                TrainerSchedule trainerSchedule = scheduleRepository.get(trainer.getId());
                List<TrainerScheduleForTable> list = trainerSchedule.convertScheduleForTable();
                ObservableList<TrainerScheduleForTable> observableList = FXCollections.observableArrayList(list);
                tableViewSchedule.setItems(observableList);
            } else {
                App.showMessage("Wow!","To delete it, select a not empty row in the table.", Alert.AlertType.INFORMATION);
            }
        }
      else {
          App.showMessage("Wow!","To delete it, select a row in the table.", Alert.AlertType.INFORMATION);
      }
    }

    @FXML
    public void buttonUpdateTrainer(ActionEvent actionEvent) throws IOException {
        trainer.setName(textFieldName.getText());
        trainer.setSurname(textFieldSurname.getText());
        trainer.setPatronymic(textFieldPatronymic.getText());
        trainer.setExperience(Double.parseDouble(textFieldExperience.getText()));
        this.trainerRepository.put(trainer);
        App.showMessage("Success", "the trainer has been successfully update", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void buttonRemoveTrainer(ActionEvent actionEvent) throws IOException {
        this.trainerRepository.delete(trainer.getId());
        App.showMessage("Success", "the trainer has been successfully remove", Alert.AlertType.INFORMATION);
        clearFields();
    }


    public void initData(Trainer trainer) throws IOException {
        try {
            this.trainer = trainer;

            textFieldSurname.setText(trainer.getSurname());
            textFieldName.setText(trainer.getName());
            textFieldExperience.setText(String.valueOf(trainer.getExperience()));
            textFieldPatronymic.setText(trainer.getPatronymic());

                trainerSchedule = scheduleRepository.get(trainer.getId());

                System.out.println(trainerSchedule);

                TableColumn<TrainerScheduleForTable, String> dayOfTheWeek = new TableColumn<>("День недели");
                dayOfTheWeek.setCellValueFactory(new PropertyValueFactory<>("day"));
                TableColumn<TrainerScheduleForTable, LocalTime> timeStart = new TableColumn<>("Время начала работы");
                timeStart.setCellValueFactory(new PropertyValueFactory<>("start"));
                TableColumn<TrainerScheduleForTable, LocalTime> timeEnd = new TableColumn<>("Время окончания работы");
                timeEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
                tableViewSchedule.getColumns().setAll(dayOfTheWeek, timeStart, timeEnd);
                if( trainerSchedule!=null) {
                    List<TrainerScheduleForTable> list = trainerSchedule.convertScheduleForTable();
                    ObservableList<TrainerScheduleForTable> observableList = FXCollections.observableArrayList(list);
                    tableViewSchedule.setItems(observableList);
                }
        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearFields() {
        textFieldSurname.clear();
        textFieldName.clear();
        textFieldPatronymic.clear();
        textFieldExperience.clear();
    }

}
