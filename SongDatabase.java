import java.util.*;
import com.sleepycat.db.*;

public class SongDatabase {
    public static Database primDB;
    public static Database secDB;

    public static void main(String[] args) {
        init();
        ArrayList<String> user = new ArrayList<String>();
        ArrayList<String> rating = new ArrayList<String>();
        user.add("bob");
        user.add("alice");
        rating.add("5");
        rating.add("7");
        putRow("Hello", user, rating);
        putRow("OtherSong", user, rating);
    }

    public static void init() {
        try {
        //Create primary database
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setType(DatabaseType.BTREE);
        dbConfig.setAllowCreate(true);
        //dbConfig.setSortedDuplicates(true);
       
        primDB = new Database("song.db", null, dbConfig);
        secDB = new Database("secSong.db", null, dbConfig);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //Enter a row of data, if song already exists, will enter new user
    //if user has already rated song, will not enter
    public static void putRow(String songID, ArrayList<String> user, 
                            ArrayList<String> rating) {
        try {
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();

            key.setData(songID.getBytes());
            key.setSize(songID.length());

            String dataString = new String();

            for (int i = 0; i < user.size(); i ++ ) {
                dataString = dataString.concat(
                    "(" + user.get(i) + "," + rating.get(i) + ")");
            }
            
            data.setData(dataString.getBytes());
            data.setSize(dataString.length());

            primDB.put(null, key, data);
            
            //entry into secondary database
            for (int i = 0; i < user.size(); i ++) {
                //Clear Data
                data = new DatabaseEntry();
                //Check if user has already rated other songs
                key.setData(user.get(i).getBytes());
                key.setSize(user.get(i).length());

                if (secDB.get(null, key, data, LockMode.DEFAULT) == 
                                OperationStatus.SUCCESS) {
                    //append songid to old data
                    String line = new String(data.getData()).concat("," +
                                        songID);
                    data.setData(line.getBytes());
                    data.setSize(line.length());
                    //put entry into DB
                    secDB.put(null, key, data);
                } else {
                    //Make new data entry
                    data.setData(songID.getBytes());
                    data.setSize(songID.length());
                    //put entry into DB
                    secDB.put(null, key, data);
                }
            }
            key.setData(new String("alice").getBytes());
            key.setSize(new String("alice").length());
            secDB.get(null, key, data, LockMode.DEFAULT);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public static ArrayList<Entry> getEntry(String user) {
        ArrayList<Entry> returnEntries = new ArrayList<Entry>();
        return returnEntries;
    }
    public static Entry getEntry(int songID) {
        Entry returnEntry = new Entry();
        return returnEntry;
    }
}

//public class Entry {
//    public int songID;
//    public ArrayList<String> user = new ArrayList<String>();
//    public ArrayList<Integer> rating = new ArrayList<String>();
//
//}
