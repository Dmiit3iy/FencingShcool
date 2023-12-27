package org.dmiit3iy.util;

public class Utils {

    public static String convertDaysToRussia(String day){
        String russianDay="";
        switch (day){
            case "monday":
                russianDay ="Понедельник";
                break;
            case "tuesday":
                russianDay ="Вторник";
                break;
            case "wednesday":
                russianDay ="Среда";
                break;
            case "thursday":
                russianDay ="Четверг";
                break;
            case "friday":
                russianDay ="Пятница";
                break;
            case "saturday":
                russianDay ="Суббота";
                break;
            case "sunday":
                russianDay ="Воскресенье";
                break;
        }
        return russianDay;
    }
    public static String convertDaysToEnglish(String day){
        String englishDay="";
        switch (day){
            case "Понедельник":
                englishDay ="monday";
                break;
            case "Вторник":
                englishDay ="tuesday";
                break;
            case "Среда":
                englishDay ="wednesday";
                break;
            case "Четверг":
                englishDay ="thursday";
                break;
            case "Пятница":
                englishDay ="friday";
                break;
            case "Суббота":
                englishDay ="saturday";
                break;
            case "Воскресенье":
                englishDay ="sunday";
                break;
        }
        return englishDay;
    }

}
