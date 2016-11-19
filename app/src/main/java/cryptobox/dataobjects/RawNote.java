package cryptobox.dataobjects;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by avishai on 05/11/2016.
 */

public class RawNote {

    protected Map<String, String> valuesString;

    public RawNote() {
        valuesString = new HashMap<String, String>();
    }

    public void addValue(String key, String value) {
        valuesString.put(key, value);
    }

    public String getValue(String key) {
        return valuesString.get(key);
    }

}
