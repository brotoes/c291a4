import java.util.*;
import java.io.*;
import com.sleepycat.db.*;

public class linSearch {
  public static void linSearch() {
    try {
      List<String> Users = new ArrayList<String>();
      List<Integer> Ratings = new ArrayList<Integer>();
      List<String> IDs = new ArrayList<String>();

      double fbest = 9999;
      double sbest = 9999;
      double tbest = 9999;

      Cursor ucur = recordDB.userrat_DB.openCursor(null, null);

      for (int i=0; i<Record.numQuery(); i++) {
        String id = Record.getQuery(i);
        
        DatabaseEntry key = new DatabaseEntry();
        DatabaseEntry data = new DatabaseEntry();
        //ucur.getFirst(key, data, null);
        
        while (ucur.getNext(key, data, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
          String output = new String(data.getData());
          //System.out.println(output);
          while (output.charAt(0) == '|' && output.charAt(1) != ']') {
            IDs.add(new String(key.getData()));
            output = output.substring(1);
            int cindx = output.indexOf(",");
            int bindx = output.indexOf("|");
            Users.add(output.substring(0, cindx));
            Ratings.add(Integer.parseInt(output.substring(cindx+1, bindx)));
            output = output.substring(bindx);
            System.out.println(output);
          }
          key = new DatabaseEntry();
          data = new DatabaseEntry();
        } //database retriever loop end
        //for (int t=0; t<IDs.size(); t++) 
        //System.out.println(IDs.get(t) + " " + Users.get(t) + " " + Ratings.get(t));

        String cid = IDs.get(0);
        int k;
        int iresult = 0;
        System.out.println(cid);
        for (int j=IDs.indexOf(id); j<=IDs.lastIndexOf(id); j++) {
          for (k=IDs.indexOf(cid); k<=IDs.lastIndexOf(cid); k++) {
            
            
          }
          cid = IDs.get(k);
          //System.out.println(cid);
        }
        IDs.clear();
        Users.clear();
        Ratings.clear();
      } //Query loop end
    } catch (Exception e) {
      e.getMessage();
    }
  }
}
