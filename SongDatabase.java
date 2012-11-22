import java.util.*;
import com.sleepycat.db.*;

public class SongDatabase {
    public static Database primDB;
    public static Database secDB;

    public static int main() {
        return 0;
    }

    public static void init() {
        //Create primary database
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setType(DatabaseType.BTREE);
        dbConfig.setAllowCreate(true);
        //dbConfig.setSortedDuplicates(true);
       
        primDB = new Database("song.db", null, dbConfig);
        secDB = new Database("secSong.db", null, dbConfig);
    }

    //Enter a row of data, if song already exists, will enter new user
    //if user has already rated song, will not enter
    public static void putRow(String songID, ArrayList<String> user, 
                            ArrayList<String> rating) {
        try {
            Cursor primCur = primDB.openCursor(null, null);
            Cursor secCur = secDB.openCursor(null, null);
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();

            key.setData(songID.getBytes());
            key.setSize(songID.length());

            //check if songID does not exist
            if (primCur.getNext(key, data, LockMode.DEFAULT) == 
                                    OperationStatus.NOTFOUND) {            

                String dataString = new String();

                for (int i = 0; i < user.size(); i ++ ) {
                    dataString.concat("(" + user.get(i) + "," + rating.get(i) + ")");
                }

                data.setData(dataString.getBytes());
                data.setSize(dataString.length());

                primDB.put(null, key, data);
            }
            primCur.close();
            secCur.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
    public static Entry getEntry(String user) {
        Entry returnEntry = new Entry();
        return returnEntry;
    }
    public static Entry getEntry(int songID) {
        Entry.Entry returnEntry = new Entry();
        return returnEntry;
    }
}

//public class Entry {
//    public int songID;
//    public ArrayList<String> user = new ArrayList<String>();
//    public ArrayList<Integer> rating = new ArrayList<String>();
//
//    public Entry(int newSongID, String newUser, int newRating) {
//        songID = newSongID;
//        user.add(newUser);
//        Integer integer = new Integer(newRating);
//        rating.add(integer);
//    }
//}
