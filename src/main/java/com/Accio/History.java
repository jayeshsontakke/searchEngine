package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet ("/History")
public class History extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
          try{
              //get connection to my sql;
              Connection connection=DatabaseConnection.getConnection();
              // get result from history table
              ResultSet resultSet=connection.createStatement().executeQuery("Select * from history;");
              ArrayList<HistoryResult>results=new ArrayList<HistoryResult>();
              //store result inside arraylist
              while(resultSet.next()){
                  String keyword=resultSet.getString("keyword");
                  String link=resultSet.getString("link");
                  HistoryResult historyResult=new HistoryResult(keyword,link);
                  results.add(historyResult);

              }
              //set attribute with result arraylist
              request.setAttribute("results",results);
              //forward request to jsp
              request.getRequestDispatcher("/history.jsp").forward(request,response);
              response.setContentType("text/html");
              PrintWriter out=response.getWriter();
          }
          catch (SQLException | ServletException | IOException sqlException){
                sqlException.printStackTrace();
          }
    }
}
