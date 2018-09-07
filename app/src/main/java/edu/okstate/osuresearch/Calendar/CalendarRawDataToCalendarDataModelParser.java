package edu.okstate.osuresearch.Calendar;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarRawDataToCalendarDataModelParser {
    public static CalendarDataModel parse(String rawData[]) {
        CalendarDataModel dataModel = new CalendarDataModel();
        dataModel.subject = rawData[RAW_HEADER_FORMAT.subject.ordinal()];
        dataModel.startDate = rawData[RAW_HEADER_FORMAT.startDate.ordinal()];

        try {
            SimpleDateFormat startDateFormat = new SimpleDateFormat("E,dd,MMM,yy,hh:mm a");
            SimpleDateFormat endDateFormat = new SimpleDateFormat("MMM dd yy,hh:mm a");

            Date startDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(rawData[RAW_HEADER_FORMAT.startDate.ordinal()] + " " + rawData[RAW_HEADER_FORMAT.startTimeIn24HourFormat.ordinal()]);
            Date endDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(rawData[RAW_HEADER_FORMAT.endDate.ordinal()] + " " + rawData[RAW_HEADER_FORMAT.endTimeIn24HourFormat.ordinal()]);
            String[] startDateSplitted = startDateFormat.format(startDate).split(",");
            String[] endDateSplitted = endDateFormat.format(endDate).split(",");
            dataModel.startDateWeekDay = startDateSplitted[0];
            dataModel.startDateDay = startDateSplitted[1];
            dataModel.startDateMonth = startDateSplitted[2];
            dataModel.startDateYear = startDateSplitted[3];
            dataModel.startTimeInTwelveHourFormat = startDateSplitted[4];
            dataModel.endDate = endDateSplitted[0];
            dataModel.endTimeInTwelveHourFormat = endDateSplitted[1];

        } catch (ParseException exception) {
            Log.e("DateException", "Error parsing date");
        }

        dataModel.allDayEvent = rawData[RAW_HEADER_FORMAT.allDayEvent.ordinal()];
        dataModel.description = rawData[RAW_HEADER_FORMAT.description.ordinal()];
        dataModel.location = rawData[RAW_HEADER_FORMAT.location.ordinal()];
        dataModel.webLink = rawData[RAW_HEADER_FORMAT.webLink.ordinal()];
        dataModel.eventTemplate = rawData[RAW_HEADER_FORMAT.eventTemplate.ordinal()];
        dataModel.audience = rawData[RAW_HEADER_FORMAT.audience.ordinal()];
        dataModel.building = rawData[RAW_HEADER_FORMAT.building.ordinal()];
        dataModel.campus = rawData[RAW_HEADER_FORMAT.campus.ordinal()];
        dataModel.category = rawData[RAW_HEADER_FORMAT.category.ordinal()];
        dataModel.contactEmail = rawData[RAW_HEADER_FORMAT.contactEmail.ordinal()];
        dataModel.contactName = rawData[RAW_HEADER_FORMAT.contactName.ordinal()];
        dataModel.contactPhoneNumber = rawData[RAW_HEADER_FORMAT.contactPhoneNumber.ordinal()];
        dataModel.cost = rawData[RAW_HEADER_FORMAT.cost.ordinal()];
        dataModel.room = rawData[RAW_HEADER_FORMAT.room.ordinal()];
        dataModel.sponsor = rawData[RAW_HEADER_FORMAT.sponsor.ordinal()];
        dataModel.typeOfEvent = rawData[RAW_HEADER_FORMAT.typeOfEvent.ordinal()];

        dataModel.allData.add(dataModel.description);
        dataModel.allData.add(dataModel.subject);
        dataModel.allData.add(dataModel.startDateMonth + " " + dataModel.startDateDay + " ,20" + dataModel.startDateYear + " " + dataModel.startTimeInTwelveHourFormat);
        String endDateSplitted[] = dataModel.endDate.split(" ");
        dataModel.allData.add(endDateSplitted[0] + " " + endDateSplitted[1] + " ,20" + endDateSplitted[2] + " " + dataModel.endTimeInTwelveHourFormat);
        dataModel.allData.add(dataModel.location);
        dataModel.allData.add(dataModel.category);
        dataModel.allData.add(dataModel.contactName);
        dataModel.allData.add(dataModel.contactPhoneNumber);
        dataModel.allData.add(dataModel.contactEmail);
        dataModel.allData.add(dataModel.sponsor);
        dataModel.allData.add(dataModel.webLink);


        return dataModel;
    }

    enum RAW_HEADER_FORMAT {
        subject,
        startDate,
        startTimeIn24HourFormat,
        endDate,
        endTimeIn24HourFormat,
        allDayEvent,
        description,
        location,
        webLink,
        eventTemplate,
        audience,
        building,
        campus,
        category,
        contactEmail,
        contactName,
        contactPhoneNumber,
        cost,
        //eventImage,
        room,
        sponsor,
        typeOfEvent
    }
}