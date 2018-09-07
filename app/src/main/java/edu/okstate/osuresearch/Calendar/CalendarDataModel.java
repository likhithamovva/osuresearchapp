package edu.okstate.osuresearch.Calendar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by appcenter on 10/29/17.
 */

public class CalendarDataModel {
    public String subject;
    public String startDate;
    public String startDateMonth;
    public String startDateYear;
    public String startDateDay;
    public String startDateWeekDay;
    public String startTimeInTwelveHourFormat;
    public String endDate;
    public String endTimeInTwelveHourFormat;
    public String allDayEvent;
    public String description;
    public String location;
    public String webLink;
    public String eventTemplate;
    public String audience;
    public String building;
    public String campus;
    public String category;
    public String contactEmail;
    public String contactName;
    public String contactPhoneNumber;
    public String cost;
    public String room;
    public String sponsor;
    public String typeOfEvent;
    public List<String> allData = new ArrayList<>();
}
