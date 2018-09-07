package edu.okstate.osuresearch.Model;

import java.util.HashMap;

import edu.okstate.osuresearch.R;

/**
 * Created by krishna on 11/28/2017.
 */

public class ColorCodesModel {
    /*
    "VPR Office And Signature Events",
    "Annual Events",
    "Divison Of Agricutural Sciences & Natural Resources",
    "Arts & Sciences",
    "Business",
    "Education",
    "College of Engineering,Architecture and Technology",
    "Human Sciences",
    "Center for Veternity Health Sciences",
    "Grad College",
    "Library",
    "Technology Development Center",
    "Center for Health Sciences"

     */


    public static HashMap<String, Integer> colorCodesHashMap = new HashMap<String, Integer>() {{
        put("VICE PRESIDENT FOR RESEARCH", R.color.colorCodeVPROffice);
        put("RESEARCH", R.color.colorCodeVPROffice);

        put("BAE", R.color.colorCodeAnnualEvents);
        put("CASNR", R.color.colorCodeAnnualEvents);
        put("DASNR", R.color.colorCodeAnnualEvents);
        put("ESGP", R.color.colorCodeAnnualEvents);
        put("FOOD & AG PRODUCT CENTER", R.color.colorCodeAnnualEvents);
        put("HORTICULTURE AND LANDSCAPE ARCHITECTURE", R.color.colorCodeAnnualEvents);
        put("PLANT AND SOIL SCIENCES", R.color.colorCodeAnnualEvents);
        put("THE BOTANIC GARDEN", R.color.colorCodeAnnualEvents);

        put("ALLIED ARTS", R.color.colorCodeArts);
        put("ART DEPARTMENT", R.color.colorCodeArts);
        put("ARTS & SCIENCES", R.color.colorCodeArts);
        put("BOTANY", R.color.colorCodeArts);
        put("ENGLISH DEPARTMENT", R.color.colorCodeArts);
        put("MUSIC DEPARTMENT", R.color.colorCodeArts);
        put("HISTORY DEPT", R.color.colorCodeArts);
        put("THEATRE", R.color.colorCodeArts);


        put("CENTER FOR EXEC AND PROF DEVE", R.color.colorCodeBusiness);
        put("SPEARS SCHOOL OF BUSINESS", R.color.colorCodeBusiness);

        put("COLLEGE OF EDUCATION", R.color.colorCodeEducation);
        put("COLLEGE OF EDUCATION OUTREACH", R.color.colorCodeEducation);

        put("CEAT", R.color.colorCodeEngineering);

        put("COLLEGE OF HUMAN SCIENCES", R.color.colorCodeHumanSciences);

        put("CENTER FOR VETERINARY HEALTH SCIENCES", R.color.colorCodeVeternity);
        put("OSU CENTER FOR VET HEALTH SCIENCES", R.color.colorCodeVeternity);

        put("GRADUATE COLLEGE", R.color.colorCodeGradCollege);

        put("CENTER FOR HEALTH SYSTEMS INNOVATION", R.color.colorCodeHealthSciences);

        put("LIBRARY", R.color.colorCodeLibrary);


    }};


}
