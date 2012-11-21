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
          IDs.add(new String(key.getData()));
          String output = new String(data.getData());
          int indx = output.indexOf(",");
          Users.add(output.substring(0, indx));
          Ratings.add(Integer.parseInt(output.substring(indx+1)));
          key = new DatabaseEntry();
          data = new DatabaseEntry();
        } //database retriever loop end
        //for (int t=0; t<IDs.size(); t++) 
        //System.out.println(IDs.get(t) + " " + Users.get(t) + " " + Ratings.get(t));

        String cid = IDs.get(0);
        int k;
        System.out.println(cid);
        for (int j=IDs.indexOf(id); j<=IDs.lastIndexOf(id); j++) {
          for (k=IDs.indexOf(cid); k<=IDs.lastIndexOf(cid); k++) {
            
          }
          cid = IDs.get(k);
          System.out.println(cid);
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
