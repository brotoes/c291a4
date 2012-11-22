import java.util.*;

public class Entry {
    public int songID;
    public ArrayList<String> user;
    public ArrayList<Integer> rating;

    public Entry(int newSongID, String newUser, int newRating) {
        user = new ArrayList<String>();
        rating = new ArrayList<Integer>();

        songID = newSongID;
        user.add(newUser);
        Integer integer = new Integer(newRating);
        rating.add(integer);
    }
}
