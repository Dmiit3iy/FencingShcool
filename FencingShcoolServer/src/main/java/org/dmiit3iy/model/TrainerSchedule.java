package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.dmiit3iy.util.Overlapping;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class TrainerSchedule {
    @Id
    @Column(name = "trainer_id")
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime mondayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime mondayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tuesdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tuesdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime wednesdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime wednesdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime thursdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime thursdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime fridayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime fridayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime saturdayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime saturdayEnd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sundayStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime sundayEnd;


    @OneToOne
    @ToString.Exclude
    @NonNull
    @MapsId
    @JoinColumn(name = "trainer_id")
    @JsonIgnore
    private Trainer trainer;

    public void setSchedule(String day, LocalTime start, LocalTime end) {
        try {
            this.getClass().getDeclaredField(day + "Start").set(this, start);
            this.getClass().getDeclaredField(day + "End").set(this, end);
        } catch (IllegalAccessException ignored) {
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public boolean isOverLapping(LocalDate date, LocalTime time) {
        LocalTime timeEndOfTraining = time.plusMinutes(90);
        String day = date.getDayOfWeek().toString().toLowerCase();
        try {
            Field field1 = this.getClass().getDeclaredField(day + "Start");
            field1.setAccessible(true);
            LocalTime start = (LocalTime) field1.get(this);
            Field field2 = this.getClass().getDeclaredField(day + "End");
            field2.setAccessible(true);
            LocalTime end = (LocalTime) field2.get(this);
            if (!Overlapping.isOverlapping(start, end, time, timeEndOfTraining)) {
                throw new IllegalArgumentException("Are you trying to make an appointment outside of business hours");
            }
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
        return false;
    }

    public LocalTime[] getTimePeriod(String day) {
        try {
            Field field1 = this.getClass().getDeclaredField(day + "Start");
            LocalTime start = (LocalTime) field1.get(this);
            Field field2 = this.getClass().getDeclaredField(day + "End");
            LocalTime end = (LocalTime) field2.get(this);
            return new LocalTime[]{start, end};
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException();
        } catch (IllegalAccessException e) {
        }
        return null;
    }
}
