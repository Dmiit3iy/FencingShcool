package org.dmiit3iy.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.dmiit3iy.model.Apprentice;
import org.dmiit3iy.model.Trainer;
import org.dmiit3iy.model.TrainerSchedule;
import org.dmiit3iy.model.TrainerScheduleForTable;
import org.dmiit3iy.retrofit.ScheduleRepository;
import org.dmiit3iy.retrofit.TrainerRepository;
import org.dmiit3iy.retrofit.TrainingRepository;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AddTrainingController {
    public TextField textFieldNumberGum;
    public ComboBox comboBoxTime;
    public DatePicker datePicker;
    public ComboBox comboBoxTrainer;
    private ScheduleRepository scheduleRepository = new ScheduleRepository();

    private TrainingRepository trainingRepository = new TrainingRepository();
    private TrainerRepository trainerRepository = new TrainerRepository();
    private Apprentice apprentice;

    public void buttonAddTraining(ActionEvent actionEvent) {
    }

    public void initData(Apprentice apprentice) {
        this.apprentice = apprentice;


    }

    @FXML
    void initialize() {
        try {
            ObservableList<Trainer> trainersList = FXCollections.observableList(trainerRepository.get());
            this.comboBoxTrainer.setItems(trainersList);
            datePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    try {
                        Trainer trainer = (Trainer) comboBoxTrainer.getSelectionModel().getSelectedItem();
                        System.out.println(trainer.getId());
                        if (trainer != null) {
                            TrainerSchedule trainerSchedule = scheduleRepository.get(trainer.getId());
                            super.updateItem(date, empty);

                            if (trainerSchedule != null) {
                                List<TrainerScheduleForTable> trainerScheduleForTableList = trainerSchedule.convertScheduleForTable().
                                        stream().filter(x->x.getDayEng()!=null).collect(Collectors.toList());
                                System.out.println("я сюда попадаю?");
                                List<String> listWD=trainerScheduleForTableList.stream().map(x->x.getDayEng().toUpperCase()).collect(Collectors.toList());
                                System.out.println(listWD);
                                System.out.println(listWD.contains(date.getDayOfWeek().toString()));
                               setDisable(listWD.contains(date.getDayOfWeek().toString()));
                              
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
