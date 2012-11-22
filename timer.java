import java.util.*;

public class timer {
  private static Long linsrttim = new Long(0);
  private static Long linstoptim = new Long(0);
  private static Long indsrttim = new Long(0);
  private static Long indstoptim = new Long(0);
  private static List<Long> lintimes = new ArrayList<Long>();
  private static List<Long> indtimes = new ArrayList<Long>();
  private static Long linmin;
  private static Long linmax;
  private static Long linavg;
  private static Long indmin;
  private static Long indmax;
  private static Long indavg;
  
  
  public static void startLinTimer() {
    Date date = new Date();
    linsrttim = date.getTime();
  }
  public static void stopLinTimer() {
    Date date = new Date();
    linstoptim = date.getTime();
    if (linmin != null) {
      if ((linstoptim-linsrttim) < linmin)
        linmin = linstoptim-linsrttim;
    } else {
      linmin = linstoptim-linsrttim;
    }
    if (linmax != null) {
      if ((linstoptim-linsrttim) > linmax)
        linmax = linstoptim-linsrttim;
    } else {
      linmax = linstoptim-linsrttim;
    }
    lintimes.add(linstoptim-linsrttim);
  }
  
  public static void startIndTimer() {
    Date date = new Date();
    indsrttim = date.getTime();
  }
  public static void stopIndTimer() {
    Date date = new Date();
    indstoptim = date.getTime();
    if (indmin != null) {
      if ((indstoptim-indsrttim) < indmin)
        indmin = indstoptim-indsrttim;
    } else {
      indmin = indstoptim-indsrttim;
    }
    if (indmax != null) {
      if ((indstoptim-indsrttim) > indmax)
        indmax = indstoptim-indsrttim;
    } else {
      indmax = indstoptim-indsrttim;
    }
    lintimes.add(indstoptim-indsrttim);
  }
  public static Long[] getTimeData() {
    long sum = 0;
    Long[] tdata = new Long[6];
    for (int i=0; i<lintimes.size(); i++) 
      sum = sum + lintimes.get(i);
    tdata[2] = sum/lintimes.size();
    sum = 0;
    for (int i=0; i<indtimes.size(); i++)
      sum = sum + indtimes.get(i);
    tdata[5] = sum/indtimes.size();
    
    tdata[0] = linmin;
    tdata[1] = linmax;
    tdata[3] = indmin;
    tdata[4] = indmax;

    return tdata;
  }
}
