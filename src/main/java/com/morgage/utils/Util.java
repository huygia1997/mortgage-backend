package com.morgage.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Util {
    /////calculate “time ago”
    public static String getTimeAgo(String time_ago) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String now = dtf.format(LocalDateTime.now());
            Date dateNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(now);
            Date datOld = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(changeStringDate(time_ago));
            long time_elapsed = (dateNow.getTime() - datOld.getTime()) / 1000;
            long seconds = time_elapsed;
            int minutes = Math.round(time_elapsed / 60);
            int hours = Math.round(time_elapsed / 3600);
            int days = Math.round(time_elapsed / 86400);
            int weeks = Math.round(time_elapsed / 604800);
            int months = Math.round(time_elapsed / 2600640);
            int years = Math.round(time_elapsed / 31207680);
            // Seconds
            if (seconds <= 60) {
                return "Vừa Xong";
            }
            //Minutes
            else if (minutes <= 60) {
                return minutes + " Phút trước";
            }
            //Hours
            else if (hours <= 24) {
                return hours + " giờ trước";
            }
            //Days
            else if (days <= 3) {
                if (days == 1) {
                    return "Hôm qua";
                } else {
                    return days + " ngày trước";
                }
            } else {
                String rs = resultStringDate(time_ago);
                return rs;
            }

        } catch (ParseException e) {
            return null;
        }
    }

    private static String changeStringDate(String date) {
        String stringDate = date.replace("-", "/");
        return stringDate.substring(0, 20);
    }

    private static String resultStringDate(String date) {
        String stringDate = date.replace("-", " ");
        stringDate = stringDate.replace(":", " ");
        stringDate = stringDate.replace("/", " ");
        stringDate = stringDate.replace(".", " ");
        String[] listStringDate = stringDate.split(" ");
        return listStringDate[2] + " tháng " + listStringDate[1] + " lúc " + listStringDate[3] + ":" + listStringDate[4];
    }

    public static String getStringDate(String date) {
        String stringDate = date.replace("-", " ");
        stringDate = stringDate.replace(":", " ");
        stringDate = stringDate.replace("/", " ");
        stringDate = stringDate.replace(".", " ");
        String[] listStringDate = stringDate.split(" ");
        return listStringDate[3] + ":" + listStringDate[4] + ", " + listStringDate[2] + " tháng " + listStringDate[1];
    }
}
