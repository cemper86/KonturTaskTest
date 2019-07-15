package ru.stairenx.konturtasktest.server;

import org.json.JSONException;
import org.json.JSONObject;

import ru.stairenx.konturtasktest.item.ContactItem;
import ru.stairenx.konturtasktest.item.EducationPeriod;
import ru.stairenx.konturtasktest.item.Temperament;

public class ParserJSON {

    private static final String ID = "id";
    private static final String NAME ="name";
    private static final String PHONE = "phone";
    private static final String HEGHT = "height";
    private static final String BIOGRAPHY = "biography";
    private static final String TEMPERAMENT = "temperament";
    private static final String EDUCATION_PERIOD = "educationPeriod";
    private static final String START = "start";
    private static final String END = "end";

    public static ContactItem parserJSONObject(JSONObject jsonObject) throws JSONException {
        EducationPeriod eduItem = new EducationPeriod(jsonObject.getJSONObject(EDUCATION_PERIOD).getString(START),jsonObject.getJSONObject(EDUCATION_PERIOD).getString(END));
        return new ContactItem(
                jsonObject.getString(ID), jsonObject.getString(NAME), jsonObject.getString(PHONE),
                Float.valueOf(String.valueOf(jsonObject.getDouble(HEGHT))), jsonObject.getString(BIOGRAPHY),
                Temperament.valueOf(jsonObject.getString(TEMPERAMENT)), eduItem);
    }

}
