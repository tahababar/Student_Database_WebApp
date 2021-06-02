import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServletStudentData extends HttpServlet { 
   public void doGet(HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter(); 
    out.println("<html>");
    out.println("<head> <title>Student Data</title> </head>");
    out.println("<body>");
    out.println("<p>Here Is The Student Data:");
    Connection conn = null;
    
    try {
        //Driver d = new ClientDriver();
    	Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        String url = "jdbc:derby:/tmp/StudentDB;create=true";
        //conn = d.connect(url, null);
        conn = DriverManager.getConnection(url);
            String qry = "select SId, SName, GradYear,CourseId "
                        + "from STUDENTINFO"; 
            PreparedStatement ps = conn.prepareStatement(qry);  
            ResultSet rs = ps.executeQuery();
         out.println("<P><table border=1>");
         out.println("<tr> <th>Student ID</th> <th>Student Name</th> <th>Grad Year</th> <th>Course ID</th> </tr>"); 
         while (rs.next()) {
            int id = rs.getInt("SId");
            String name = rs.getString("SName");
            int year = rs.getInt("GradYear");
            int courseId = rs.getInt("CourseId");
            out.println("<tr> <td>" + id + "</td> <td>" + name + "</td> <td>"
                    + year + "</td> <td>" + courseId + "</td> </tr>");
}
out.println("</table>");
rs.close();
}
catch(Exception e) {
e.printStackTrace();
out.println("SQL Exception. Execution aborted"); }
finally {
out.println("</body> </html>");
try {
   if (conn != null)
      conn.close();
}
catch (SQLException e) {
   e.printStackTrace();
out.println("Could not close database"); }
}

}
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {    
   String SName=request.getParameter("SName");    
   int GradYear=Integer.parseInt(request.getParameter("GradYear"));    
   int CourseId=Integer.parseInt(request.getParameter("CourseId")); 
   int SId=Integer.parseInt(request.getParameter("SId"));      
   int CourseId2 = Integer.parseInt(request.getParameter("CourseId2"));
   String SName2=request.getParameter("SName2"); 
   int SId2=Integer.parseInt(request.getParameter("SId2"));     
   try    
   {    
	   Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
	   //Driver d = new ClientDriver();    
       String url="jdbc:derby:/tmp/StudentDB;create=true";    
           
       Connection con = DriverManager.getConnection(url);
       String query="Insert into STUDENTINFO(SId, SName, GradYear, CourseId) values (?,?,?,?)";   
       PreparedStatement ps=con.prepareStatement(query);    
       
       ps.setInt(1, SId);    
       ps.setString(2, SName);    
       ps.setInt(3,GradYear);    
       ps.setInt(4, CourseId);
       ps.executeUpdate();
       ps.close();   
  

       String query2 = "Update STUDENTINFO set CourseId = ?" + "where SName = ?";

       PreparedStatement ps2=con.prepareStatement(query2); 
       ps2.setInt(1, CourseId2);    
       ps2.setString(2, SName2); 
       ps2.executeUpdate();
       ps2.close();   
   
       
       String query3 = "Delete from STUDENTINFO where SId = ?";
  
       PreparedStatement ps3=con.prepareStatement(query3); 
       ps3.setInt(1, SId2);   
       ps3.executeUpdate();
       ps3.close();   
   

   } 
   catch(Exception e)    
   {    
           e.printStackTrace();    
   }     
}    
}