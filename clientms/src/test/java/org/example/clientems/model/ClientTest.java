package org.example.clientems.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

    @Test
    public void testGettersAndSetters() {
        Client client = new Client();

        // Test setPassword and getPassword methods
        String password = "securePassword";
        client.setPassword(password);
        assertEquals(password, client.getPassword(), "Password getter/setter test failed");

        // Test setStatus and getStatus methods
        Boolean status = true;
        client.setStatus(status);
        assertEquals(status, client.getStatus(), "Status getter/setter test failed");
    }
}
