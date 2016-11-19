package cryptobox.dataobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by avishai on 05/11/2016.
 */

public class CursorWrapper {

    public String tableName;
    public String whereClause;
    public String sqlQuery;

    private List<String> lstColumns;

    public CursorWrapper() {
        lstColumns = new ArrayList<>();
    }

    public String[] getColumns() {

        if (lstColumns.size() == 0)
            return new String[0];

        Object[] objColumns = lstColumns.toArray();
        String[] arrColumns = Arrays.copyOf(objColumns, objColumns.length, String[].class);

        return arrColumns;
    }

    public void addValue(String val) {
        lstColumns.add(val);
    }


}
