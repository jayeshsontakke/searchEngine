package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.*;


//Annotation
@WebServlet("/Search")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
      String keyword = request.getParameter("keyword");
      System.out.println(keyword);
      //we have to generate a query according search text;
        try {
            //Establish get connection to database
            Connection connection = DatabaseConnection.getConnection();
            //save keyword and link associated with that
            PreparedStatement preparedStatement= connection.prepareStatement("Insert into History values(?,?)");
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/java/"+keyword);
            preparedStatement.executeUpdate();

            //executing a query related to keyword and get result
            ResultSet resultSet = connection.createStatement().executeQuery("select pagetitle,pagelink,(length(lower(pagetext))-length(replace(lower(pagetext),'"+keyword+"','')))/length('"+keyword+"')as countoccurence from pages order by countoccurence desc limit 30");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>();
            //iterate through result and save all elements into result array
            while (resultSet.next()){
             SearchResult searchResult=new SearchResult();
             searchResult.setPageTitle(resultSet.getString("pageTitle"));
                searchResult.setPageLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }
            for(SearchResult searchResult:results){
                System.out.println(searchResult.getPageTitle()+" "+searchResult.getPageLink()+"\n");
            }
            //set attribute of request with requests arraylist
            request.setAttribute("results",results);
            //forward request to search.jsp
            request.getRequestDispatcher("/search.jsp").forward(request,response);
            response.setContentType("text/html");

            PrintWriter out=response.getWriter();
        }
        catch(SQLException | ServletException | IOException sqlException){
            sqlException.printStackTrace();
        }
    }
}
