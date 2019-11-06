package ProjectTests;

import ChatService.PDUManagment.PLeavePDU;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class PLeaveTest {

    private byte[] temp;

    @Before
    public void setUp() throws IOException{
        PLeavePDU pLeavePDU = new PLeavePDU();
        //temp = pLeavePDU.serializePLeave();
    }

    @Test
    public void testPduLength() {assertEquals(16, temp.length);}

    @Test
    public void getOP() throws IOException {
        PLeavePDU pLeavePDU = new PLeavePDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pLeavePDU.deSerializePDU(inputStream);
        assertEquals(17, pLeavePDU.getOP());
    }

    @Test
    public void getIdLength() throws IOException {
        PLeavePDU pLeavePDU = new PLeavePDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pLeavePDU.deSerializePDU(inputStream);
        assertEquals(5, pLeavePDU.getIdLength());
    }

    @Test
    public void getTimeStamp() throws IOException {
        PLeavePDU pLeavePDU = new PLeavePDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pLeavePDU.deSerializePDU(inputStream);
        int unixTime = (int) Instant.now().getEpochSecond();
        assertEquals(unixTime, pLeavePDU.getTimeStamp());
    }

    @Test
    public void testPrintStr() throws IOException {
        PLeavePDU pLeavePDU = new PLeavePDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pLeavePDU.deSerializePDU(inputStream);
        pLeavePDU.printStr();
    }
}
