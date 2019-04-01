package com.morgage.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.morgage.common.Const;
import com.morgage.service.PictureService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    public static Date atEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date atStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndDay(Date startDate, int paymentType, int paymentTerm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        if (paymentType == Const.PAYMENT_TYPE.DAY) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + paymentTerm * 1);
        } else if (paymentType == Const.PAYMENT_TYPE.WEEK) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + paymentTerm * 7);
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + paymentTerm * 1);
        }
        return calendar.getTime();
    }

    public Boolean insertPicturesToDB(PictureService pictureService, String pictures, int ObjId, int type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList array = mapper.readValue(pictures, ArrayList.class);
            if (array.size() != 0) {
                for (int i=0; i<array.size(); i++) {
                    HashMap<String, String> map = (HashMap<String, String>) array.get(i);
                    String picUrlX = "";
                    String idCloud= "";
                    String deleteHash= "";
                    for(Map.Entry<String, String> entry : map.entrySet()) {
                        if (entry.getKey().equals("pictureUrl")) {
                            picUrlX = entry.getValue();
                        }
                        if (entry.getKey().equals("idCloud")) {
                            idCloud = entry.getValue();
                        }
                        if (entry.getKey().equals("deleteHash")) {
                            deleteHash = entry.getValue();
                        }
                        pictureService.savePicture(picUrlX, ObjId, type, idCloud, deleteHash);
                    }

                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return false;
    }
}
