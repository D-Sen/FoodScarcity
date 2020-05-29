package logic;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {



    @Test
    void testGetUserId() {
        Date date = new Date();
        Notification N  = new Notification("my id",null,null,null,null,null, date);
        assertEquals("my id",N.getUserId());
    }


    @Test
    void testGetTitle() {
        Date date = new Date();
        Notification N  = new Notification(null, "my sharona",null,null,null,null, date);
        assertEquals("my sharona",N.getTitle());
    }

    @Test
    void testGetMessage() {
        Date date = new Date();
        Notification N  = new Notification(null, null, "Test Message",null,null,null, date);
        assertEquals("Test Message",N.getMessage());

    }


    @Test
    void testGetDate() {
        Date date = new Date();
        Notification N  = new Notification(null,null,null,null,null,null, date);
        assertEquals(date,N.getDate());
    }


    @Test
    void testFindNotifications() {
        ArrayList<Notification> notifications = Notification.findNotifications("2020-01-01","2020-12-31");
        assertNotEquals(0,notifications.size());
    }
}