//Name: Domonic Senesi
//Creation Date: 04/18/2020
//Main Document: NotificationLog-Comments.txt

package presentation;

import logic.Notification;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class NotificationLog {
    private JPanel rootPanel;
    private JButton Search;
    private JTable notificationTable;
    private JLabel textStatus;
    private JXDatePicker eDatePicker;
    private JXDatePicker sDatePicker;
    private JComboBox columnCombo;
    private JTextField columnSearchField;


    private static String[] columnNames = {"Sent By","Title","Message","Subscriber List","# of Recipients","Type"};


    private static String tempLabel;
    public static void setTextLabel(String N){
        tempLabel = N;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public NotificationLog() {
        createTable();
        createColumnCombo();

        /* Listens for the "Search" button being pressed. Then displays records from the NOTIFICATION table.
            Input: Start Date, End Date (Both selected from their separate calendar panels, or as text directly)
                Search Column, Search String
            Output: None
         */
        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateFormat sysDate = new SimpleDateFormat("yyyy/MM/dd");
                if (sDatePicker.getDate() != null) {
                    Notification.setSDate(sysDate.format(sDatePicker.getDate()));
                }
                if (eDatePicker.getDate() != null) {
                    Notification.setEDate(sysDate.format(eDatePicker.getDate()));
                }
                Notification.setSearchColumn(Integer.toString(columnCombo.getSelectedIndex()));
                Notification.setSearchString(columnSearchField.getText());

                displayNotifications();
                textStatus.setText(tempLabel);
                Notification.setSDate(null);
                Notification.setEDate(null);

            }
        });


        /* Listens for a row in the table being selected via mouse click, then displays a dialog with the title and
            message text with an "OK" button.
            Input: Selected row in the shown table (Title & Message strings)
            Output: None
         */
        notificationTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int index = notificationTable.getSelectedRow();
                TableModel model = notificationTable.getModel();
                String tempTitle = model.getValueAt(index, 2).toString();
                String tempMessage = model.getValueAt(index, 3).toString();
                JOptionPane.showMessageDialog(null, tempTitle +"\n\n" + tempMessage, "Title & Message Display", JOptionPane.PLAIN_MESSAGE);
            }
        });
    }

    /* Creates an array called 'notifications', then grabs the data from each of the notifications classes'
        fields to fill in each element of that array.
        Inputs: None
        Outputs: None
     */
    private void displayNotifications() {
        ArrayList<Notification> notifications = Notification.findNotifications(Notification.getSDate(),Notification.getEDate());
        DefaultTableModel model = (DefaultTableModel)notificationTable.getModel();

        model.setRowCount(0);
        for (Notification notification : notifications) {
            model.addRow(new Object[]{
                    notification.getDate(),
                    notification.getUserId(),
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getSubList(),
                    notification.getCount(),
                    notification.getTarget()

                    //notification.getNotificationId(),
                    //notification.getTemplateId(),

            });
        }
    }


    /*
    createTable: Creates an fills the header information for the displayed java swing table in the GUI.
        Inputs: None
        Outputs: None
    */
    private void createTable() {
        notificationTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Date","Sent By","Title","Message","Subscriber List","# of Recipients","Type"}
        ));
        TableColumnModel columns = notificationTable.getColumnModel();
        columns.getColumn(0).setMinWidth(80);
        columns.getColumn(1).setMinWidth(70);
        columns.getColumn(2).setMinWidth(100);
        columns.getColumn(3).setMinWidth(140);
        columns.getColumn(4).setMinWidth(70);
        columns.getColumn(5).setMinWidth(50);
        columns.getColumn(6).setMinWidth(50);

        notificationTable.setAutoCreateRowSorter(true);
    }

    private void createColumnCombo() {
        columnCombo.setModel(new DefaultComboBoxModel(new String[]{"Sent By","Title","Message","Subscriber List","# of Recipients","Type"}));
    }

}