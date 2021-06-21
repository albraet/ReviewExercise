package com.servlets;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.DBConnection;

public class Home extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String file = "ShowMe.txt";
    PrintWriter out = response.getWriter();

    try (FileInputStream fis = new FileInputStream(getClass().getClassLoader().getResource(file).getFile());
        BufferedInputStream bis = new BufferedInputStream(fis)) {
      // log user info to DB;
      logToDB(request.getRemoteHost());

      // return content to client
      response.setContentType("text/html");
      out.print("<div>hello</div>");
      out.println("<br/>");

      // read in the file, append the bytes to StringBuilder
      StringBuilder sb = new StringBuilder();
      while (bis.available() > 0) {
        sb.append((char) bis.read());
      }
      // display file content
      out.println("<div>" + sb.toString() + "</div>");
    } catch (Exception e) {
      e.printStackTrace();
    }
    out.close();
  }

  private static Boolean logToDB(String info) {
    String QUERY = "insert into review_exercise_ values(?)";
    try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(QUERY)) {
      stmt.setString(1, info);
      int row = stmt.executeUpdate();
      if (row > 0) {
        return true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return false;
  }
}
