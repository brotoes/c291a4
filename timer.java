import java.util.*;

public class timer {
  private long linsrttim = 0;
  private long linstoptim = 0;
  private long indsrttim = 0;
  private long indstoptim = 0;
  private List<Long> lintimes = new ArrayList<Long>();
  private List<Long> indtimes = new ArrayList<Long>();
  private long linmin;
  private long linmax;
  private long linavg;
  private long indmin;
  private long indmax;
  private long indavg;
  
  public static void startLinTimer() {
    linsrttim = Date.getTime();
  }
  public static void stopLinTimer() {
    linstoptim = Date.getTime();
    if (linmin != null) {
      if ((linstoptim-linsrttim) < linmin)
        linmin = linstoptim-linsrttim;
    } else {
      linmin = linstoptim-linsrttim;
    }
    if (linmax != null) {
      if ((linstoptim-linsrttim) > max)
        linmax = linstoptim-linsrttim;
    } else {
      linmax = linstoptim-linsrttim;
    }
    lintimes.add(linstoptim-linsrttim);
  }
  
  public static void startIndTimer() {
    indsrttim = Date.getTime();
  }
  public static void stopIndTimer() {
    indstoptim = Date.getTime();
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
  public static long[] getTimeData() {
    long sum = 0;
    for (int i=0; i<lintimes.size(); i++) 
      sum = sum + lintimes.get(i);
    tdata[2] = sum/lintimes.size();
    sum = 0;
    for (int i=0; i<indtimes.size(); i++)
      sum = sum + indtimes.get(i);
    tdata[5] = sum/indtimes.size();
    
    long[] tdata = new long[6];
    tdata[0] = linmin;
    tdata[1] = linmax;
    tdata[3] = indmin;
    tdata[4] = indmax;

    return tdata;
  }
}
