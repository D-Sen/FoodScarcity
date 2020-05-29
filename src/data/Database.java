//Name: Domonic Senesi
//Creation Date: 04/19/2020
//Main Document: NotificationLog-Comments.txt

package data;

import logic.Notification;
import presentation.NotificationLog;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Database {
    static public Connection connection = null;
    private static String CONN_STRING = "jdbc:jtds:sqlserver://cisdbss.pcc.edu";
    private static String USERNAME = "234a_Cornucopia";
    private static String PASSWORD = "!@#_234a_Cornucopia_Spr2020";

    private static String SEARCH_SQL;



    /*
    getQuery: Returns the main base query that will be used to search the database.
        Inputs: Integer i
        Outputs: String
    */
    public static String getQuery(int i) {
        if (i == 1) {
            return "SELECT SUBSCRIBER.Name, NOTIFICATION.Title, Message, Count, Target , SubList, \"Date\" " + //TEMPLATE.Name,
                    "FROM NOTIFICATION " +
                    "LEFT JOIN SUBSCRIBER ON NOTIFICATION.UserID = SUBSCRIBER.UserID " +
                    "WHERE Date BETWEEN ? AND ? " +
                    "ORDER BY Date ASC";
        } else {
            return "SELECT SUBSCRIBER.Name, NOTIFICATION.Title, Message, Count, Target , SubList, \"Date\" " + //TEMPLATE.Name,
                    "FROM NOTIFICATION " +
                    "LEFT JOIN SUBSCRIBER ON NOTIFICATION.UserID = SUBSCRIBER.UserID " +
                    "WHERE (Date BETWEEN ? AND ?) " +
                    "AND  lower("+ Notification.getSearchColumn() +") LIKE ? " +
                    "ORDER BY Date ASC";
        }
    }

    /*
    connect - Uses the network connection information and passes the credentials to connect to the database.
        Inputs: None
        Outputs: None
     */
    public static void connect(){
        if (connection != null) {
            return;
        } else {
            try {
                connection = DriverManager.getConnection(CONN_STRING,USERNAME,PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                exit(-1);
            }
        }
    }

    /*
    findNotifications: Creates an array list of notification class instances based from the Notification table.
        Inputs: String eDate, String sDate
        Outputs: ArrayList notifications
    */
    public static ArrayList<Notification> findNotifications(String sDate, String eDate){
        connect();
        ArrayList<Notification> notifications = new ArrayList<Notification>();
        try {
            if ((Notification.getSDate() == null) || (Notification.getEDate() == null)){
                if (Notification.getSDate() == null) {
                    PreparedStatement stmt = connection.prepareStatement("SELECT TOP 1 (DATEADD(DAY, -1, \"Date\"))\"Date\" from NOTIFICATION");
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    Notification.setSDate(rs.getDate("Date").toString());
                }
                if (Notification.getEDate() == null) {
                    Notification.setEDate((LocalDate.now().plusDays(1)).toString());
                }
            }

            if (Notification.getSearchString().equals("")) {
                SEARCH_SQL = getQuery(1);
            } else {
                SEARCH_SQL = getQuery(2);
            }
            PreparedStatement stmt = connection.prepareStatement(SEARCH_SQL);
            stmt.setString(1, Notification.getSDate());
            stmt.setString(2, Notification.getEDate());
            if (!Notification.getSearchString().equals("")) {
                stmt.setString(3, "%" + Notification.getSearchString() + "%");
            }

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                NotificationLog.setTextLabel("No records found.");
            } else {
                NotificationLog.setTextLabel("Searched: " + Notification.getSDate() + " through " + Notification.getEDate() + "       Records found, displaying below.");
            }
            rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new Notification(
                    rs.getString("Name"), //userid
                    rs.getString("Title"),
                    rs.getString("Message"),
                    rs.getString("Count"),
                    rs.getString("Target"),
                    rs.getString("SubList"),
                    rs.getDate("Date")
                        //rs.getString("NotificationID"),
                        // rs.getString("Name"), //templateid

                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }
}
