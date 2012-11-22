import java.util.*;
import java.io.*

public class linSearch {
  public static void linSearch() {
    int bid = 0;
    int sid = 0;
    int tid = 0;
    double bids = 9999;
    double sids = 9999;
    double tids = 9999;
    String id = "-1";
    
    for (int i=0; i<numQueries(); i++) {
      timer.startLinTimer();
      
      id = Record.getQuery(i);
      Entry init = getEntry(id);
      
      for (Integer j=1; j<=Record.recordSize(); j++) {
        Entry comp = getEntry(j.toString());
        double score = -1.0;
        if (init.id != comp.id) 
          score = compareEntry(init, comp);
        int itemp, itemp1;
        double dtemp, dtemp1;

        if (score < bids) {
          dtemp = bids;
          itemp = bid;
          bids = score;
          bid = j;
          if (dtemp < sids) {
            dtemp1 = dtemp;
            itemp1 = itemp;
            sids = dtemp;
            sid = itemp;
            if (dtemp1 < tids) {
              tids = dtemp1;
              tid = itemp1;
            }
          }
        }  
      }
      System.out.println("The top similarities to " + id + " are " + bid + " " + sid + " " + tid);
      try {
        FileWriter fwrite = new FileWriter("linearanswers.txt");
        BufferedWriter writer = new BufferedWriter(fwrite);
        writer.write(id + " " + bid + " " + sid + " " + tid);
      } catch (Exception e) {
        e.getMessage();
      }
    }
    
    timer.stopLinTimer();
  }
  
  public static double compareEntry(Entry entry1, Entry entry2) {
    int iresult = 0;
    double dresult = -1.0;
    int count = 0;
    for (int i=0; i<entry1.user.size(); i++) {
      int indx;
      if (indx = entry2.indexOf(entry1.user.get(i)) != -1) {
        int a = entry1.rating.get(i) - entry2.rating.get(indx);
        iresult = iresult + a*a;
        count++;
      }
    }
    dresult = Math.sqrt(iresult);
    dresult = dresult/count;
    return dresult;
  }
}
