package DBUtil;

/**
 * Created by Micro on 2016/6/28.
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBAgent {
    static Connection _CONN = null;

    private static String user;
    private static String pwd;

    //取得连接
    public static boolean GetConn(String sUser, String sPwd) {
        if (_CONN != null) return true;
        try {
            user = sUser;
            pwd = sPwd;
            String sDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            // String sDBUrl ="jdbc:sqlserver://192.168.0.74;databaseName=wakeup";
            String sDBUrl = "jdbc:sqlserver://localhost;databaseName=tickets";

            Class.forName(sDriverName);
            _CONN = DriverManager.getConnection(sDBUrl, sUser, sPwd);

        } catch (Exception ex) {
            // ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    //关闭连接
    public static void CloseConn() {
        try {
            _CONN.close();
            _CONN = null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            _CONN = null;
        }
    }


    public static DataTable GetDataTable(String sSQL, Object... objParams) throws Exception {
        GetConn(user, pwd);

        DataTable dt = null;

        PreparedStatement ps = _CONN.prepareStatement(sSQL);
        if (objParams != null) {
            for (int i = 0; i < objParams.length; i++) {
                ps.setObject(i + 1, objParams[i]);
            }
        }
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        List<DataRow> row = new ArrayList<DataRow>(); //表所有行集合
        List<DataColumn> col = null; //行所有列集合
        DataRow r = null;// 单独一行
        DataColumn c = null;//单独一列

        String columnName;
        Object value;
        int iRowCount = 0;
        while (rs.next())//开始循环读取，每次往表中插入一行记录
        {
            iRowCount++;
            col = new ArrayList<DataColumn>();//初始化列集合
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columnName = rsmd.getColumnName(i);
                value = rs.getObject(columnName);
                c = new DataColumn(columnName, value);//初始化单元列
                col.add(c); //将列信息加入到列集合
            }
            r = new DataRow(col);//初始化单元行
            row.add(r);//将行信息加入到行集合
        }
        dt = new DataTable(row);
        dt.RowCount = iRowCount;
        dt.ColumnCount = rsmd.getColumnCount();

        return dt;
    }


    public static boolean RUD(String sSQL) throws Exception {
        GetConn(user, pwd);
        boolean result = false;

        PreparedStatement ps = _CONN.prepareStatement(sSQL);
        result = ps.execute();

        return !result;
    }


}