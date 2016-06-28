import DBUtil.DBAgent;
import DBUtil.DataTable;
import Serialization.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by Micro on 2016/6/28.
 */
@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    @Override
    public void destroy() {
        super.destroy();
        DBAgent.CloseConn();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        DBAgent.GetConn("sa", "???|||");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            String sql = request.getParameter("sql");
            String ret = null;
            if (sql.startsWith("select")) {
                ret = execSelect(sql);
            } else {
                ret = String.valueOf(execRUD(sql));
            }
            out.println(ret);
        } catch (Exception e) {
            response.getWriter().println("Invalid Parameters</br>");
            response.getWriter().println(request.getParameter("sql") + "</br>");
            response.getWriter().println(e.toString());
        }
    }

    private String execSelect(String sql) throws Exception {
        DataTable table = DBAgent.GetDataTable(sql);

        String ret = JsonUtil.BuildJsonString(table);

        return ret;
    }

    private boolean execRUD(String sql) throws Exception {
        return DBAgent.RUD(sql);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("BAD request");
    }
}
