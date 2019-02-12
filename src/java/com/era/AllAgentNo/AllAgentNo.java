package com.era.AllAgentNo;

import com.era.AllCustNo.*;
import com.era.FingerCheck.*;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;
import process.matchData.Bean;

public class AllAgentNo {

    public static HashMap<Integer, List<Integer>> receiveCusNo(String pStatus) {  //List<String> // HashMap<Integer, Integer>// HashMap<Integer, TreeSet<Integer>>
        AllCustNo allCustNo = new AllCustNo();
        Connection con = DbConnectivity.getConnection();
        HashMap<Integer, List<Integer>> mapAgentCustNo = new HashMap<>();
        
        List<Integer> listIntCust = allCustNo.custNo(pStatus);  // Receive all Customer No as String
        try {
            for (int i = 0; i < listIntCust.size(); i++) {
                List<Integer> listIntAgent = new ArrayList<Integer>();

                CallableStatement cs = con.prepareCall("begin BIOTPL.GET_CUSTOMER_LIST.GET_AGENT_CUST_NO(?,?,?); end;");
                cs.setInt(1, listIntCust.get(i));  //cs.setString(1, listStrCust.get(i));
                cs.setString(2, pStatus);
                cs.registerOutParameter(3, java.sql.Types.ARRAY, "CUS_NO_LIST");
                cs.execute();

                Array arrAgentNo = cs.getArray(3);
                Map map = con.getTypeMap();
                map.put("CUSTOMER_NO_LIST", Class.forName("com.era.FingerCheck.TestArr"));
                Object[] values = (Object[]) arrAgentNo.getArray();

                //List<String> values = (List<String>) arrAgentNo.getArray();
                for (int k = 0; k < values.length; k++) { // Convert to Integer type by TestArr class
                    TestArr a = (TestArr) values[k];
                    listIntAgent.add(a.attrOne);
                }

                //mapAgentCustNo.put(listIntCust.get(i), (TreeSet<Integer>) listIntAgent);
                mapAgentCustNo.put(listIntCust.get(i), listIntAgent); // Arrays.asList(listIntAgent)
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            DbConnectivity.releaseConnection(con);
        }

        return mapAgentCustNo;
    }
    // Main method for testing this class   
    public static void main(String[] args) {

        HashMap<Integer, List<Integer>> mapAgentCustNo = receiveCusNo("O");
        System.out.println("Customer and Agents (HashMap) :"+ mapAgentCustNo);

          
          for (Map.Entry<Integer, List<Integer>> entry : mapAgentCustNo.entrySet())
            {
                if(entry.getValue().size()>=1){
//                    System.out.println("Agent user size is :"+ entry.getValue().size());
//                    System.out.println(entry.getKey() + ":" + entry.getValue());
                    System.out.println("Key : "+entry.getKey());
                    for(int i=0; i<entry.getValue().size(); i++){
                        System.out.println(entry.getValue().get(i));
                    }
                 }
            }
          
    }
}
//258534