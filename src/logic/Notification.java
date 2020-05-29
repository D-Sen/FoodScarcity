//Name: Domonic Senesi
//Creation Date: 04/18/2020
//Main Document: NotificationLog-Comments.txt

package logic;

import data.Database;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class Notification {
    private String userId;
    private String title;
    private String message;
    private String count;
    private String target;
    private String subList;
    private Date date;
    //private String notificationId;
    //private String templateId;


    private static String sDate;
    private static String eDate;

    private static String searchString;
    private static String searchColumn;
    private static String[] columnActualNames = {"SUBSCRIBER.Name","Title","Message","SubList","Count","Target"};



    public Notification(String ui, String t, String m, String c, String ta, String sl, Date d){
        userId = ui;
        title = t;
        message = m;
        count = c;
        target = ta;
        subList = sl;
        date = d;
        //notificationId = ni;
        //templateId = ti;

    }

    public String getUserId() {
        return userId;
    }
    public String getTitle() {
        return title;
    }
    public String getMessage() {
        return message;
    }
    public String getCount() {
        return count;
    }
    public String getTarget() {
        return target;
    }
    public Date getDate() {
        return date;
    }
    public String getSubList() {return subList;}

    /*
    setSDate, setEDate: set the start date and end date which will be used to filter notifications
        Inputs: String N
    */
    public static void setSDate(String N) {
        sDate = N;
    }
    public static void setEDate(String N) {
        eDate = N;
    }

    /*
    getSDate, getEDate: return the start date and end date which will be used to filter notifications
        Outputs: String N
    */
    public static String getSDate() {
        return sDate;
    }
    public static String getEDate() {
        return eDate;
    }

    /*
    findNotifications - Creates an array list of notification class instances based from the Notification table.
        Inputs: String eDate, String sDate
        Outputs: ArrayList notifications
    */
    public static ArrayList<Notification> findNotifications(String startDate, String endDate){
        return Database.findNotifications(startDate, endDate);
    }

    /*
    setSearchString, getSearchString: set and get the search string the user may have entered in the textbox
        Inputs: (setSearchString) String N
        Outputs: (getSearchString) String
    */
    public static void setSearchString(String N) {
        searchString = N;
    }
    public static String getSearchString() {
        return searchString;
    }

    /*
    setSearchColumn, getSearchColumn: sets/gets  the search column chosen by the ComboBox. 'Get' uses a special
            Int parser.
        Inputs: (setSearchColumn) String N
        Outputs: (getSearchColumn) String
    */

    public static void setSearchColumn(String N) {
        searchColumn = N;
    }
    public static String getSearchColumn() {
        return columnActualNames[parseInt(searchColumn)];
    }


}
