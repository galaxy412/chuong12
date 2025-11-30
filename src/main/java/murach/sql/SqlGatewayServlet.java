package murach.sql;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
// 1. THÊM DÒNG IMPORT NÀY
import javax.servlet.annotation.WebServlet;

import java.sql.*;

// 2. THÊM DÒNG NÀY ĐỂ MAPPING URL (Cực kỳ quan trọng)
@WebServlet("/sqlGateway")
public class SqlGatewayServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // ... (Phần code bên dưới của bạn giữ nguyên, không cần sửa) ...
        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Lưu ý: Nên dùng com.mysql.cj.jdbc.Driver nếu có warning

            // Database và pass bạn đã sửa đúng rồi
            String dbURL = "jdbc:mysql://localhost:3306/murach11";
            String username = "root";
            String password = "412005";

            Connection connection = DriverManager.getConnection(dbURL, username, password);
            Statement statement = connection.createStatement();

            sqlStatement = sqlStatement.trim();
            if (sqlStatement.length() >= 6) {
                String sqlType = sqlStatement.substring(0, 6);

                if (sqlType.equalsIgnoreCase("select")) {
                    ResultSet resultSet = statement.executeQuery(sqlStatement);
                    // Đảm bảo bạn đã có file SQLUtil.java nhé
                    sqlResult = SQLUtil.getHtmlTable(resultSet);
                    resultSet.close();
                } else {
                    int i = statement.executeUpdate(sqlStatement);
                    if (i == 0) {
                        sqlResult = "<p>The statement executed successfully.</p>";
                    } else {
                        sqlResult = "<p>The statement executed successfully.<br>" + i + " row(s) affected.</p>";
                    }
                }
            }
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            sqlResult = "<p>Error loading the database driver: <br>" + e.getMessage() + "</p>";
        } catch (SQLException e) {
            sqlResult = "<p>Error executing the SQL statement: <br>" + e.getMessage() + "</p>";
        }
        HttpSession session = request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        String url = "/index.jsp";
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
}