package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import org.dmiit3iy.App;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.model.TrainerScheduleForTable;
import org.dmiit3iy.retrofit.ScheduleRepository;
import org.dmiit3iy.retrofit.TrainerRepository;
import org.dmiit3iy.retrofit.TrainingRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddTrainingController {
    public TextField textFieldNumberGym;
    public ComboBox comboBoxTime;
    public DatePicker datePicker;
    public ComboBox comboBoxTrainer;
    private Trainer trainer;
    private ScheduleRepository scheduleRepository = new ScheduleRepository();

    private TrainingRepository trainingRepository = new TrainingRepository();
    private TrainerRepository trainerRepository = new TrainerRepository();
    private Apprentice apprentice;

    public void buttonAddTraining(ActionEvent actionEvent) {
        try {
            trainingRepository.post(trainer.getId(), apprentice.getId(), Integer.parseInt(textFieldNumberGym.getText()), datePicker.getValue(), (LocalTime) comboBoxTime.getValue());
            App.showMessage("Susses", "The training was successfully added", Alert.AlertType.INFORMATION);
            textFieldNumberGym.clear();
            comboBoxTime.getItems().clear();
            datePicker.setValue(null);
            comboBoxTrainer.getItems().clear();
        } catch (IOException e) {
            App.showMessage("Alyarma!!", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    public void initData(Apprentice apprentice) {
        this.apprentice = apprentice;
    }

    @FXML
    void initialize() {
        try {
            ObservableList<Trainer> trainersList = FXCollections.observableList(trainerRepository.get());
            this.comboBoxTrainer.setItems(trainersList);

            this.datePicker.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 1) {
                        if (comboBoxTrainer.getSelectionModel().getSelectedItem() == null) {
                            App.showMessage("Attention", "First, select the trainer", Alert.AlertType.INFORMATION);
                        }

                    }
                }
            });


            this.textFieldNumberGym.setOnMouseClicked(mouseEvent -> {

                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 1) {
                        LocalDate date = datePicker.getValue();
                        if (date == null) {
                            App.showMessage("Attention", "First, select the date of the training", Alert.AlertType.INFORMATION);
                        }
                    }
                }
            });

            this.comboBoxTime.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 1) {
                        String numGym = textFieldNumberGym.getText();
                        if (!numGym.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
                            App.showMessage("Alyarma!", "Gym number field must be a number!", Alert.AlertType.ERROR);
                            textFieldNumberGym.clear();
                            return;
                        }
                        if (numGym.isEmpty()) {
                            App.showMessage("Attention", "First, select the gym number!", Alert.AlertType.INFORMATION);
                        } else {
                            if (!textFieldNumberGym.getText().isEmpty()) {

                                try {
                                    comboBoxTime.setItems(FXCollections.observableList(trainingRepository.getTime(trainer.getId(), datePicker.getValue(), Integer.parseInt(textFieldNumberGym.getText()))));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            } else {
                                App.showMessage("Info", "First, select the gym number!", Alert.AlertType.INFORMATION);
                            }
                        }
                    }
                }
            });


            datePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    try {
                        trainer = (Trainer) comboBoxTrainer.getSelectionModel().getSelectedItem();
                        if (trainer != null) {
                            TrainerSchedule trainerSchedule = scheduleRepository.get(trainer.getId());
                            super.updateItem(date, empty);

                            if (trainerSchedule != null) {
                                List<TrainerScheduleForTable> trainerScheduleForTableList = trainerSchedule.convertScheduleForTable().
                                        stream().filter(x -> x.getDayEng() != null).collect(Collectors.toList());
                                List<String> listWD = trainerScheduleForTableList.stream().map(x -> x.getDayEng().toUpperCase()).collect(Collectors.toList());
                                System.out.println(listWD);
                                System.out.println(listWD.contains(date.getDayOfWeek().toString()));
                                //   trainingRepository.getTime(trainer.getId(),date,Integer.parseInt(textFieldNumberGym.getText())).isEmpty();
                                //setDisable(!listWD.contains(date.getDayOfWeek().toString()) || date.compareTo(LocalDate.now()) < 0);
                                //||   trainingRepository.getTime(trainer.getId(),date,Integer.parseInt(textFieldNumberGym.getText())).isEmpty()
                                if (!listWD.contains(date.getDayOfWeek().toString()) || date.compareTo(LocalDate.now()) < 0||!trainingRepository.getAnyFreeTime(trainer.getId(),date)) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ff1943;");
                                }
                            }
                        }
                    } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            datePicker.setEditable(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
