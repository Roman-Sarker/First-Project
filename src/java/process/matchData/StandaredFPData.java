
package process.matchData;

import static com.era.AllAgentNo.AllAgentNo.receiveCusNo;
import com.era.FingerCheck.DbConnectivity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static process.matchData.GetFingerData.getByteDataFromBlob;

/**
 *
 * @author root
 */
public class StandaredFPData {
    public static void methodIsoFP(){
        
                String vStatus = "S";
                HashMap<Integer, List<Integer>> mapAgentCustNo = receiveCusNo(vStatus); // Receive all agent no of specific customer from 'AllAgentNo' class which inherit here.
                Connection conn = null;

                List<byte[]> custAvaFP = new ArrayList<byte[]>() {
                };
                try {
                    //ArrayList<String> matchFingers = new ArrayList();
                    HashMap<Integer, List<Integer>> matchFingersData = new HashMap<>();
                    List<byte[]> agentAvaFP = new ArrayList<byte[]>() {
                    };
                    int matchCount = 0;
                    int UnMatchCount = 0;
                    conn = DbConnectivity.getConnection();

                    for (Map.Entry<Integer, List<Integer>> entry : mapAgentCustNo.entrySet()) { // This loop change next customer
                        custAvaFP = new ArrayList<byte[]>() {
                        };
                        if (entry.getValue().size() >= 1) {         // This condition check agent are available or not for specific customer
                            //System.out.println("==================== Customer No : " + entry.getKey() + " ===================");
//                    String cQuery = "SELECT LINDEX,LTHUMB,RINDEX,RTHUMB,LMIDDLE,LRING,LLITTLE,"
//                    + "RMIDDLE,RRING,RLITTLE from  Biotpl.FP_ENROLL where  CUST_NO =" + entry.getKey(); 
                            String cQuery = "SELECT LINDEX,LTHUMB,RINDEX,RTHUMB,LMIDDLE,LRING,LLITTLE,"
                                    + "RMIDDLE,RRING,RLITTLE from  Biotpl.FP_ENROLL where  CUST_NO ="+ entry.getKey(); // 258534
                            PreparedStatement pstmt = conn.prepareStatement(cQuery);
                            ResultSet rs = pstmt.executeQuery();
                            while (rs.next()) {
                                byte[] LINDEX = getByteDataFromBlob(rs.getBlob("LINDEX"));
                                byte[] LTHUMB = getByteDataFromBlob(rs.getBlob("LTHUMB"));
                                byte[] RINDEX = getByteDataFromBlob(rs.getBlob("RINDEX"));
                                byte[] RTHUMB = getByteDataFromBlob(rs.getBlob("RTHUMB"));

                                byte[] LMIDDLE = getByteDataFromBlob(rs.getBlob("LMIDDLE"));
                                byte[] LRING = getByteDataFromBlob(rs.getBlob("LRING"));
                                byte[] LLITTLE = getByteDataFromBlob(rs.getBlob("LLITTLE"));

                                byte[] RMIDDLE = getByteDataFromBlob(rs.getBlob("RMIDDLE"));
                                byte[] RRING = getByteDataFromBlob(rs.getBlob("RRING"));
                                byte[] RLITTLE = getByteDataFromBlob(rs.getBlob("RLITTLE"));
                                
                                 if (RLITTLE != null) {
                                    custAvaFP.add(RLITTLE);
                                }
                                 if (LLITTLE != null) {
                                    custAvaFP.add(LLITTLE);
                                }
                                 if (LRING != null) {
                                    custAvaFP.add(LRING);
                                }
                                 if (LMIDDLE != null) {
                                    custAvaFP.add(LMIDDLE);
                                }
                                 if (RTHUMB != null) {
                                    custAvaFP.add(RTHUMB);
                                }
                                 if (RINDEX != null) {
                                    custAvaFP.add(RINDEX);
                                }
                                 if (LTHUMB != null) {
                                    custAvaFP.add(LTHUMB);
                                }
                                if (LINDEX != null) {
                                    custAvaFP.add(LINDEX);
                                }
                                if (RRING != null) {
                                    custAvaFP.add(RRING);
                                }
                                if (RMIDDLE != null) {
                                    custAvaFP.add(RMIDDLE);
                                }
                                
                               

                                //System.out.println("Total agent of this customer is :" + custAvaFP.size());
//                        for(int r = 0; r<CustListData.size(); r++){
//                            System.out.println(CustListData.get(r)); // System.out.println("buffer : "+Base64.getEncoder().encodeToString(AllData.get(0)));
//                        }
//                        CustListData = new ArrayList<byte[]>() {};
                            }

                            // Agent User finger data --------------------------------------------------------------
                            //System.out.println("------ Agent of " + entry.getKey() + " ------");

                            for (int i = 0; i < entry.getValue().size(); i++) { // This loop change next Agent of specific Customer
                                agentAvaFP = new ArrayList<byte[]>() {
                                };
                                //String aQuery = "SELECT DISTINCT CUST_NO, RLITTLE, LLITTLE, LRING, LMIDDLE, RTHUMB, RINDEX, LTHUMB, LINDEX, RRING, RMIDDLE, STANDARD  from FP_ENROLL where CUST_NO =  " + entry.getValue().get(i); 
                                String aQuery = "SELECT DISTINCT CUST_NO, RLITTLE, LLITTLE, LRING, LMIDDLE, RTHUMB, RINDEX, LTHUMB, LINDEX, RRING, RMIDDLE, STANDARD  from FP_ENROLL where CUST_NO = "+ entry.getValue().get(i); //  257351
                                pstmt = conn.prepareStatement(aQuery);
                                rs = pstmt.executeQuery();

                                while (rs.next()) {
                                    byte[] aRthumb = getByteDataFromBlob(rs.getBlob("RTHUMB"));
                                    byte[] aRindex = getByteDataFromBlob(rs.getBlob("RINDEX"));
                                    byte[] aMiddle = getByteDataFromBlob(rs.getBlob("RMIDDLE"));
                                    byte[] aRring = getByteDataFromBlob(rs.getBlob("RRING"));
                                    byte[] aRlittle = getByteDataFromBlob(rs.getBlob("RLITTLE"));
                                    byte[] aLthumb = getByteDataFromBlob(rs.getBlob("LTHUMB"));
                                    byte[] aLindex = getByteDataFromBlob(rs.getBlob("LINDEX"));
                                    byte[] aLmiddle = getByteDataFromBlob(rs.getBlob("LMIDDLE"));
                                    byte[] aLring = getByteDataFromBlob(rs.getBlob("LRING"));
                                    byte[] aLlittle = getByteDataFromBlob(rs.getBlob("LLITTLE"));

                                    //System.out.println(entry.getValue().get(i)+" - "+ cRthumb);
                                    if (aRlittle != null) {
                                        agentAvaFP.add(aRlittle);
                                    }
                                    if (aLlittle != null) {
                                        agentAvaFP.add(aLlittle);
                                    }
                                    if (aLring != null) {
                                        agentAvaFP.add(aLring);
                                    }
                                    if (aLmiddle != null) {
                                        agentAvaFP.add(aLmiddle);
                                    }
                                    if (aRthumb != null) {
                                        agentAvaFP.add(aRthumb);
                                    }
                                    if (aRindex != null) {
                                        agentAvaFP.add(aRindex);
                                    }
                                    if (aLthumb != null) {
                                        agentAvaFP.add(aLthumb);
                                    }
                                    if (aLindex != null) {
                                        agentAvaFP.add(aLindex);
                                    }
                                    if (aRring != null) {
                                        agentAvaFP.add(aRring);
                                    }
                                    if (aMiddle != null) {
                                        agentAvaFP.add(aMiddle);
                                    }
                                    
                                    //System.out.println("Finger of  :"+ entry.getValue().get(i)+" And Size is : "+ AgentListData.size());

                                }
                                //------ Check Customer and Agent Finger data . Is all data comes correctly or not?
                                   
                                   int customerId = entry.getKey();
                                   int AgentId = entry.getValue().get(i);
//                                   System.out.println("Cust Id :"+customerId);
//                                   System.out.println("Agent Id :"+AgentId);
                                         
                                //--------------------------------------------------------------------------
                                //---------------------------- Start matching Zone--------------------------
                                for (int c = 0; c < custAvaFP.size(); c++) {
                                    for (int a = 0; a < agentAvaFP.size(); a++) {
//                                        System.out.println(agentAvaFP.get(a)); // System.out.println("buffer : "+Base64.getEncoder().encodeToString(AllData.get(0)));
//                                        System.out.println(custAvaFP.get(c));
                                        
                                        byte[] bAgentFP = agentAvaFP.get(a);
                                        byte[] bCustFP = custAvaFP.get(c);
                                        String CustFpNo = Integer.toString(c+1);
                                        String agentFpNo = Integer.toString(a+1);
//                                        System.out.println("CustFPNo = "+ c +"| AgentFpNo = "+ a);
                                        
                                        byte[][] data = new byte[1][];
                                        data[0] = bCustFP;
                                        
                                        // Call Matching function
                                        boolean matchingFlag = FingerMatchIsoToAnsi.fingerPrintIndetify(bAgentFP, data, Integer.toString(customerId));
                                        if(matchingFlag){
//                                            System.out.println("Customer ID = "+ customerId);
//                                            System.out.println("Agent ID = "+ AgentId);
//                                            System.out.println(agentAvaFP.get(a)); 
//                                            System.out.println(custAvaFP.get(c));
//                                            System.out.println("CustFPNo = "+ CustFpNo +"| AgentFpNo = "+ agentFpNo);
//                                            System.out.println("Finger Matched");
                                              byte[] vMatchFingerData = agentAvaFP.get(a);
                                                 //MatchFingerInsert.updateStatus();
                                                 MatchFingerInsert.insertFinger(customerId, AgentId, agentFpNo, CustFpNo, vMatchFingerData, vStatus);
                                                 //System.out.println(entry.getKey()+" - " + entry.getValue().get(i)+" ("+ custFpNum +" - "+agentFpNum+")");
                                                    matchCount = matchCount + 1;
                                        }
                                        else{
                                            UnMatchCount = UnMatchCount + 1;
                                        }
                                  
                                    }
                                    System.out.println("...");
                                }
                                //--------------------------------------------End of matchig zone

                                System.out.println("Another agent");

                            }// End of 2nd for loop
                        }// End of If condition
                        // custFingersCount = custFingersCount + 1; 
                    } // End of 1st for loop
                    System.out.println("Match Finger : " + matchCount);
                    System.out.println("Unmatched Finger : " + UnMatchCount);
                    DbConnectivity.releaseConnection(conn);
                } catch (SQLException ex) {
                    Logger.getLogger(GetFingerData.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
 // Main() method for testing purpose   
//    public static void main(String[] args) {
//        StandaredFPData.methodIsoFP();
//    }
//    
}
