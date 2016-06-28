package DBUtil;

/**
 * Created by Micro on 2016/6/28.
 */

import java.util.List;

public class DataTable {
    public int RowCount = 0;
    public int ColumnCount = 0;
    List<DataRow> row;

    public DataTable() {
    }

    public DataTable(List<DataRow> _row) {
        this.row = _row;
    }

    public static void PrintTable(DataTable dt) {
        for (DataRow r : dt.GetRow()) {
            for (DataColumn c : r.GetColumn()) {
                System.out.print(c.GetKey() + ":" + c.GetValue() + "  ");
            }
            System.out.println("");
        }
    }

    public List<DataRow> GetRow() {
        return row;
    }

    public void SetRow(List<DataRow> _row) {
        row = _row;
    }
}