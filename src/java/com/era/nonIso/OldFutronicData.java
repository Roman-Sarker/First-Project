package com.era.nonIso;

import com.era.AllAgentNo.AllAgentNo;
import com.era.AllCustNo.AllCustNo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import process.matchData.MatchFingerInsert;

public class OldFutronicData {

    public static void methodOldFP() {
        //oldCustomer("O");
        //oldAllAgent("O");
        FingerMatching fingerMatching = new FingerMatching();
        //MatchDataInsertToDB matchDataInsertToDB = new MatchDataInsertToDB();
        try {
            String status = "O";
            int custYcount = 0; // Finger1 and Finger2 column value(Y/N) count. If both column Y then 2 else 1
            int agentYcount = 0;
            FileRead fileRead = new FileRead();
            
            AllAgentNo allAgentNo = new AllAgentNo();
            HashMap<Integer, List<Integer>> mapAgentCustNo = allAgentNo.receiveCusNo(status); // Receive all agent and customer no (agentNo against each customer)
            //System.out.println(mapAgentCustNo);
            FingerData fingerData1 = null;
            FingerData fingerData2 = null;
            for (Map.Entry<Integer, List<Integer>> entry : mapAgentCustNo.entrySet())  // This loop for change next customer
            {   
                if (entry.getValue().size() >= 1){
                    
                    int custNo = entry.getKey();
                    int agentNo ;
                    FingerCheckInDB fingerCheckInDB = new FingerCheckInDB();
                    custYcount = fingerCheckInDB.twoFingerAvailableStatus(custNo); // Check finger Number availibility in directory But match in Database
                    boolean result = false;
                    
                    if(custYcount > 0)
                    {
                        for(int cf = 1; cf<=custYcount; cf++)      // TThis loop for change finger number of specific Customer
                        {   
                            //C1_Template = null;
                            fingerData1 = null;
                            fingerData1 = fileRead.getFingerDataFromFile(custNo, cf); //File Read of customer//258534=[257351]
                            
                            for (int a = 0; a < entry.getValue().size(); a++) {         // This loop for change nex agent
                                agentNo = entry.getValue().get(a);
                                System.out.println(agentNo);
                                agentYcount = fingerCheckInDB.twoFingerAvailableStatus(agentNo);
                                
                                for(int af = 1; af<=agentYcount; af++) // af = agent finger // This loop for change finger number of specific agent
                                {
                                    fingerData2 = null;
                                    fingerData2 = fileRead.getFingerDataFromFile(agentNo, af); // File Read of agent
                                    
                                    
                                    //------- Matching Process Start
                                        //System.out.println(fingerData1+"||"+fingerData2);
                                       result = fingerMatching.matchFingerData(fingerData1, fingerData2);
                                       if(result){
                                           String vCustFpNo = Integer.toString(cf);
                                           String vAgentFpNo = Integer.toString(af);
                                           byte[] fingerDataForInsert = fingerData1.getM_Template(); // here store for insert this match finger data to database
                                           System.out.println("Match Successfully");
                                           System.out.println("Data is :");
                                           System.out.println("cust No :" +custNo+ "/ Agent NO :"+ agentNo+"/ CustFinger No :"+cf+"/ AgentFinger No :"+af+"/ Finger Data :"+fingerData1);
                                           //matchDataInsertToDB.insertMatchValue(custNo, agentNo, vCustFpNo, vAgentFpNo, fingerDataForInsert);
                                           //MatchFingerInsert.updateStatus();
                                           MatchFingerInsert.insertFinger(custNo, agentNo, vAgentFpNo, vCustFpNo, fingerDataForInsert, status);
                                       }
                                       else{
                                           System.out.println("Finger Don't match");
                                       }
                                       
                                    
                                    //-------
                                }
                            }
                        }
                        
                    }
                    else
                    {System.out.println("This customer has no finger in FP_ENROLL table.");}
                    //System.out.println(yCount);

                    if (entry.getValue().size() >= 1) {
                        System.out.println("Cust :" + entry.getKey()+" And Agent : "+entry.getValue());
                      }
                }
                
            
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
   //Main() method for testing purpose  
    public static void main(String[] args) {
        OldFutronicData.methodOldFP();
    }
    /*  
    public static void oldCustomer(String pStatus){
        AllCustNo allCustNo = new AllCustNo();
        List<Integer> listIntCust = allCustNo.custNo(pStatus);
        System.out.println(listIntCust); 
    }

    public static void oldAllAgent(String pStatus){
        AllAgentNo allAgentNo = new AllAgentNo();
        HashMap<Integer, List<Integer>> mapAgentCustNo = allAgentNo.receiveCusNo(pStatus); // Receive all agent no of specific customer from 'AllAgentNo' class which inherit here.
        try{
            System.out.println(mapAgentCustNo);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
     */
}
