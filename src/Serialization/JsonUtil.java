package Serialization;

import DBUtil.DataColumn;
import DBUtil.DataRow;
import DBUtil.DataTable;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by Micro on 2016/6/28.
 */
public class JsonUtil {

    public static String BuildJsonString(DataTable table) throws Exception {

        JSONArray array = new JSONArray();


        for (DataRow row :
                table.GetRow()) {
            JSONObject obj = new JSONObject();
            for (DataColumn column :
                    row.GetColumn()) {

                if (column.GetKey().contains("date")) {
                    obj.put(column.GetKey(), column.GetValue().toString());
                } else {
                    obj.put(column.GetKey(), column.GetValue());
                }
            }
            array.add(obj);
        }

        return array.toString();
    }
}
