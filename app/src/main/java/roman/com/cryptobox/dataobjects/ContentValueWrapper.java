package roman.com.cryptobox.dataobjects;

import android.content.ContentValues;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by avishai on 05/11/2016.
 */

public class ContentValueWrapper {

    public String tableName;
    public String whereClause;

    protected Map<String, String> valuesString;
    protected Map<String, Long> valuesLongs;

    public ContentValueWrapper() {
        valuesString = new HashMap<String, String>();
        valuesLongs = new HashMap<String, Long>();
    }

    public void addLongValue(String key, Long value) {valuesLongs.put(key, value); }

    public void addStringValue(String key, String value) {
        valuesString.put(key, value);
    }

    public ContentValues getContentValue() {
        ContentValues cv = new ContentValues();

        if (valuesString.size() > 0) {
            Set stringSet = valuesString.entrySet();
            Iterator i1 = stringSet.iterator();

            while (i1.hasNext()) {
                Map.Entry entry = (Map.Entry) i1.next();
                cv.put((String) entry.getKey(), (String) entry.getValue());
            }
        }

        if (valuesLongs.size() > 0) {
            Set longSet = valuesLongs.entrySet();
            Iterator i2 = longSet.iterator();

            while (i2.hasNext()) {
                Map.Entry entry = (Map.Entry) i2.next();
                cv.put((String) entry.getKey(), (Long) entry.getValue());
            }
        }

        return cv;
    }
}

