package com.example.cocoin.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DateUtils {

    public static List<LocalDateTime> getMonthDateList(LocalDateTime parse) {
        List<LocalDateTime> result = new ArrayList<>();
//         포맷 정의
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime sDate = parse.withDayOfMonth(1);

        result.add(sDate);
        result.add(parse);
        return result;
    }

//    public List<Date> getTwoMonthDateList(Date parse) {
//        List<Date> result = new ArrayList<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        Date sDate = new Date();
//        Date beforeSDate = new Date();
//        Date beforeEDate = new Date();
//
//        try {
//            String strEDate = sdf.format(parse);
//            String strSDate = strEDate.substring(0, 7) + "-01";
//            sDate = sdf.parse(strSDate);
//
//            Date parseEDate = sdf.parse(strEDate);
//            cal.set(parseEDate.getYear() + 1900, parseEDate.getMonth(), parseEDate.getDate());
//
//            cal.add(Calendar.MONTH, -1);
//
//            int actualMinimum = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
//            cal.set(Calendar.DAY_OF_MONTH, actualMinimum);
//
//            Date date = cal.getTime();
//            beforeSDate = date;
//
//            int actualMaximum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//            cal.set(Calendar.DAY_OF_MONTH, actualMaximum);
//
//            date = cal.getTime();
//            beforeEDate = date;
//        } catch (ParseException e) {
//            log.error("FortuneService getMonthDateList error", e);
//        }
//
//        result.add(sDate);
//        result.add(parse);
//        result.add(beforeSDate);
//        result.add(beforeEDate);
//        return result;
//    }
}
