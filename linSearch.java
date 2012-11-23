import java.util.*;
import java.io.*;

public class linSearch {
  public static ArrayList<Integer> IDs = new ArrayList<Integer>();
  public static ArrayList<Double> Scores = new ArrayList<Double>();
  
  
  public static void linSearch() {
    try {
      FileWriter fwrite = new FileWriter("linearanswers.txt");
      BufferedWriter writer = new BufferedWriter(fwrite);
    
      int id = -1;
      
      for (int i=0; i<Record.numQuery(); i++) {
        //System.out.println("marker");
        timer.startLinTimer();
        
        id = Integer.parseInt(Record.getQuery(i));
        Entry init = SongDatabase.getEntry(id);
        
        for (int j=1; j<=Record.recordSize(); j++) {
          Entry comp = SongDatabase.getEntry(j);
          //System.out.println(init.user.get(0));
          double score = -1.0;
          if (init.songID != comp.songID) 
            score = compareEntry(init, comp);
          if (score >= 0) {
            IDs.add(new Integer(j));
            Scores.add(new Double(score));
            //System.out.println(j + " " + score);
          }
          //System.out.println(score);
          
        }
        timer.stopLinTimer();
        getBest(id, writer);
      }
      writer.close();
    } catch (Exception e) {
      e.getMessage();
    }
  }
  
  public static double compareEntry(Entry entry1, Entry entry2) {
    int iresult = 0;
    double dresult = -1.0;
    int count = 0;
    int indx = -1;
    
    for (int i=0; i<entry1.user.size(); i++) {
      indx = -1;

      for (int j = 0; j < entry2.user.size(); j ++) {
        //System.out.println("users: " + entry1.user.get(i) + " " + entry2.user.get(j));
        if (entry1.user.get(i).equals(entry2.user.get(j))) {
          //System.out.println(entry1.user.get(i));
          indx = j;
          break;
        }
      }
      if (indx != -1) {
        int a = entry1.rating.get(i) - entry2.rating.get(indx);
        iresult = iresult + a*a;
        count++;
        
      }
    }
    if (count > 0) {
      dresult = Math.sqrt((double)iresult);
      dresult = dresult/count;
    }
    return dresult;
  }

  public static void getBest(int id, BufferedWriter writer) {
    Integer bid = new Integer(-1);
    Integer sid = new Integer(-1);
    Integer tid = new Integer(-1);
    Double dmin = new Double(9999);
    int indx = -1;
    for (int i=0; i<Scores.size(); i++) {
      if (Scores.get(i) < dmin) {
        dmin = Scores.get(i);
        bid = IDs.get(i);
        indx = i;
        //System.out.println(dmin);
      }
    }
    dmin = new Double(9999);
    Scores.remove(indx);
    IDs.remove(indx);

    for (int i=0; i<Scores.size(); i++) {
      if (Scores.get(i) < dmin){
        dmin = Scores.get(i);
        sid = IDs.get(i);
        indx = i;
      }
    }
    dmin = new Double(9999);
    Scores.remove(indx);
    IDs.remove(indx);

    for (int i=0; i<Scores.size(); i++) {
      if (Scores.get(i) < dmin){
        dmin = Scores.get(i);
        tid = IDs.get(i);
        indx = i;
      }
    }
    String output = new Integer(id).toString().concat(" ")
      .concat(bid.toString()).concat(" ").concat(sid.toString()).concat(" ").concat(tid.toString().concat("\n"));
    System.out.println("The top similarities to " + id + " are " + bid + " " + sid + " " + tid);
    
    try {
      writer.write(output);
    } catch (Exception e) {
      e.getMessage();
    }
    IDs.clear();
    Scores.clear();
  }
}
