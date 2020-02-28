package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckUtilsTest {
    @Test
    void checkNetworkConnection_connected(){
        assertEquals(true,CheckUtils.checkNetworkConnection());
    }

}