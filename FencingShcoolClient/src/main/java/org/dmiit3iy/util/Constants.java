package org.dmiit3iy.util;

import java.util.HashMap;

public class Constants {
    public static final String URL = "http://localhost:8080/";
    public static final String[] dayWeekEng
            = new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
    public static final String[] dayWeekRus
            = new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    public static final HashMap<String, String> weekDays = new HashMap<>();

    static {
        weekDays.put("Понедельник","monday");
        weekDays.put("Вторник","tuesday");
        weekDays.put("Среда",  "wednesday");
        weekDays.put("Четверг", "thursday");
        weekDays.put("Пятница",  "friday");
        weekDays.put("Суббота",  "saturday");
        weekDays.put("Воскресенье", "sunday");
    }
}
