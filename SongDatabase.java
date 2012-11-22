import java.util.*;
import com.sleepycat.db.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SongDatabase {
    public static Database primDB;
    public static Database secDB;

//Compile with SongDatabase.java Entry.java after uncommenting
//this main function to run tests
    /*
    public static void main(String[] args) {
        init();
        ArrayList<String> userList = new ArrayList<String>();
        ArrayList<Integer> ratingList = new ArrayList<Integer>();        

        userList.add("foo");
        userList.add("bar");
        
        ratingList.add(new Integer(5));
        ratingList.add(new Integer(4));

        putRow(10, userList, ratingList);
        
        ArrayList<Entry> entries = getEntry("foo");
        
        for (int i = 0; i < entries.size(); i ++)
            System.out.println(entries.get(i).songID);

        Entry entry = getEntry(10);
        System.out.println(entry.user.get(0));
    }*/

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
            System.err.println("Error in init: " + e.getMessage());
        }
    }

    //overloaded function to allow unparsed data entry
    public static void putRow(int songID, ArrayList<String> user,
                            ArrayList<Integer> rating) {
        String parsedSongID = new Integer(songID).toString();
        ArrayList<String> parsedRating = new ArrayList<String>();        

        for (int i = 0; i < rating.size(); i ++) {
            parsedRating.add(rating.get(i).toString());
        }

        putRow(parsedSongID, user, parsedRating);
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
        } catch (Exception e) {
            System.err.println("Error in putRow(): " + e.getMessage());
        }
    }
    public static ArrayList<Entry> getEntry(String user) {
        ArrayList<Entry> returnEntries = new ArrayList<Entry>();
        //ArrayList<Integer> entryKeys = new ArrayList<Integer>();
        try {
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();

            key.setData(user.getBytes());
            key.setSize(user.length());

            secDB.get(null, key, data, LockMode.DEFAULT);
            
            String regex = "[0-9]+";

            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(new String(data.getData()));

            while (matcher.find()) {
                returnEntries.add(getEntry(Integer.parseInt(matcher.group())));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return returnEntries;
    }
    //reconstructs from primDB a Entry object which corresponds to data
    //contained in key songID
    public static Entry getEntry(int id) {
        //Declarations
        Entry returnEntry = new Entry();
        try {
            Integer idWrapped = new Integer(id);
            returnEntry.songID = id;
            ArrayList<String> user = new ArrayList<String>();
            ArrayList<Integer> userRating = new ArrayList<Integer>();
            
            DatabaseEntry key = new DatabaseEntry();
            DatabaseEntry data = new DatabaseEntry();

            /*
            DatabaseEntry testKey = new DatabaseEntry();
            DatabaseEntry testData = new DatabaseEntry();

            System.out.println("before cursors");
            Cursor cursor = primDB.openCursor(null, null);
            System.out.println("After cursor opening");
            cursor.getNext(testKey, testData, LockMode.DEFAULT);
            System.out.println("key/data: " + new String(testKey.getData())
                    + " " + new String(testData.getData()));
            */

            key.setData(idWrapped.toString().getBytes());
            key.setSize(idWrapped.toString().length());
            
            primDB.get(null, key, data, LockMode.DEFAULT);

            String dataString = new String(data.getData());

            //Parse with regexes
            String userRegex = "[A-Za-z]+";
            String ratingRegex = "[0-9]+";
            
            Pattern userPattern = Pattern.compile(userRegex);
            Pattern ratingPattern = Pattern.compile(ratingRegex);

            Matcher userMatcher = userPattern.matcher(dataString);
            Matcher ratingMatcher = ratingPattern.matcher(dataString);

            while (userMatcher.find()) {
                user.add(userMatcher.group());
            }
            while (ratingMatcher.find()) {
                userRating.add(Integer.parseInt(ratingMatcher.group()));
            }
            returnEntry.user = user;
            returnEntry.rating = userRating;
        } catch (Exception e) {
            System.err.println("Error in getEntry(): " + e.getMessage());
        }

        return returnEntry;
    }
}

//public class Entry {
//    public int songID;
//    public ArrayList<String> user = new ArrayList<String>();
//    public ArrayList<Integer> rating = new ArrayList<String>();
//
//}
