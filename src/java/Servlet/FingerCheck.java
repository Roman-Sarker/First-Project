package Servlet;

import com.era.AllAgentNo.AllAgentNo;
import com.era.AllCustNo.AllCustNo;
import com.era.FingerCheck.DbConnectivity;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Class.forName;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.era.FingerCheck.DbConnectivity;
import com.era.sQuery.BeanClass;
import process.matchData.GetFingerData;
import process.matchData.MatchFingerInsert;

/**
 *
 * @author roman
 */
@WebServlet(name = "FingerCheck", urlPatterns = {"/FingerCheck"})
public class FingerCheck extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        
        PrintWriter out = res.getWriter();
        Connection con = DbConnectivity.getConnection();
        try {
             out.println("Get-Method");
             
             //BackUp previous data.
             MatchFingerInsert.storeBackup();
             
             // Check for ISO (Standard) data
             GetFingerData.matchFingerData("S");
             out.println("Matched Iso data insert successfully.");
             
             // Check for OLD futronic data
             GetFingerData.matchFingerData("O");
             out.println("Matched Old data insert successfully.");
           } catch (Exception ex) 
            {
                ex.printStackTrace();
                out.println("Error is :" + ex);
            }
 
// Select query for report view     
             try{
            //String sql = "SELECT AGENT_CUST_NO, CUSTOMER_CUST_NO, CUSTOMER_FINGER, AGENT_FINGER, STANDARD FROM FINGER_MATCH_REPORT WHERE STATUS = 'Y'";
            String sql = "SELECT AGENT_CUST_NO, CUSTOMER_CUST_NO, CUSTOMER_FINGER, AGENT_FINGER, STANDARD FROM FINGER_MATCH_REPORT";
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            List <BeanClass> myList = new ArrayList<>();
            
            int count = 0;
             out.println("Customer No, Agent No,  Customer FP No, Agent FP No,  Standard");
                
            while(rs.next())
            {
                BeanClass beanVar= new BeanClass();
                
                beanVar.setCustNo(rs.getInt("CUSTOMER_CUST_NO"));
                beanVar.setAgentNo(rs.getInt("AGENT_CUST_NO"));
                beanVar.setCustFpNo(rs.getString("CUSTOMER_FINGER"));
                beanVar.setAgentFpNo(rs.getString("AGENT_FINGER"));
                beanVar.setStandard(rs.getString("STANDARD"));
                
                myList.add(beanVar);
                  count = count +1;
            }
            out.println("\n\nTotal match :" + count);

            req.setAttribute("myList", myList);
            req.getRequestDispatcher("matchDataReport.jsp").forward(req, res);
             
            
            
        }catch(SQLException ex)
        {
            ex.printStackTrace();
            out.println("Error at retreive match data by SELECT query.");
        }
        finally{
            DbConnectivity.releaseConnection(con);
        }
        
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
