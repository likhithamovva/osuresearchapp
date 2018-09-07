package edu.okstate.osuresearch.ResearchWeek;

/*
 * Created by movva on 7/24/2018.
 */


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResearchWeekRawDataToResearchWeekDataModelParser {
    public static ResearchWeekDataModel parse(String[] rawData, ResearchWeekKeyCheck check) {
        ResearchWeekDataModel dataModel = new ResearchWeekDataModel();
        if (check.subject)
            dataModel.subject = rawData[RAW_HEADER_FORMAT.subject.ordinal()];

        try {
            SimpleDateFormat finalDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());
            SimpleDateFormat parseDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.getDefault());

            if (check.startDate && check.startTime) {
                Date startDate = parseDateFormat.parse(rawData[RAW_HEADER_FORMAT.startDate.ordinal()] + " " + rawData[RAW_HEADER_FORMAT.startTimeIn24HourFormat.ordinal()]);
                dataModel.startDate = finalDateFormat.format(startDate);
            }

            if (check.endDate && check.endTime) {
                Date endDate = parseDateFormat.parse(rawData[RAW_HEADER_FORMAT.endDate.ordinal()] + " " + rawData[RAW_HEADER_FORMAT.endTimeIn24HourFormat.ordinal()]);
                dataModel.endDate = finalDateFormat.format(endDate);
            }
        } catch (ParseException ignored) {

        }

        if (check.allDayEvent)
            dataModel.allDayEvent = rawData[RAW_HEADER_FORMAT.allDayEvent.ordinal()];
        if (check.description)
            dataModel.description = rawData[RAW_HEADER_FORMAT.description.ordinal()];
        if (check.location)
            dataModel.location = rawData[RAW_HEADER_FORMAT.location.ordinal()];
        if (check.webLink)
            dataModel.webLink = rawData[RAW_HEADER_FORMAT.webLink.ordinal()];
        if (check.eventTemplate)
            dataModel.eventTemplate = rawData[RAW_HEADER_FORMAT.eventTemplate.ordinal()];
        if (check.audience)
            dataModel.audience = rawData[RAW_HEADER_FORMAT.audience.ordinal()];
        if (check.building)
            dataModel.building = rawData[RAW_HEADER_FORMAT.building.ordinal()];
        if (check.campus)
            dataModel.campus = rawData[RAW_HEADER_FORMAT.campus.ordinal()];
        if (check.category)
            dataModel.category = rawData[RAW_HEADER_FORMAT.category.ordinal()];
        if (check.contactEmail)
            dataModel.contactEmail = rawData[RAW_HEADER_FORMAT.contactEmail.ordinal()];
        if (check.contactName)
            dataModel.contactName = rawData[RAW_HEADER_FORMAT.contactName.ordinal()];
        if (check.contactPhoneNumber)
            dataModel.contactPhoneNumber = rawData[RAW_HEADER_FORMAT.contactPhoneNumber.ordinal()];
        if (check.cost)
            dataModel.cost = rawData[RAW_HEADER_FORMAT.cost.ordinal()];
        if (check.room)
            dataModel.room = rawData[RAW_HEADER_FORMAT.room.ordinal()];
        if (check.sponsor)
            dataModel.sponsor = rawData[RAW_HEADER_FORMAT.sponsor.ordinal()];
        if (check.typeOfEvent)
            dataModel.typeOfEvent = rawData[RAW_HEADER_FORMAT.typeOfEvent.ordinal()];

        dataModel.allData.add(dataModel.description);
        dataModel.allData.add(dataModel.subject);
        dataModel.allData.add(dataModel.startDate);
        dataModel.allData.add(dataModel.endDate);
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
        eventImage,
        room,
        sponsor,
        typeOfEvent
    }
}