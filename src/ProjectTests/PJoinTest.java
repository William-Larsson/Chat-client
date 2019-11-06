package ProjectTests;

import ChatService.PDUManagment.PJoinPDU;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class PJoinTest {

    private byte[] temp;

    @Before
    public void setUp() throws IOException{
        PJoinPDU pLeavePDU = new PJoinPDU();
        //temp = pLeavePDU.serializePJoin();
    }

    @Test
    public void testPduLength() {assertEquals(16, temp.length);}

    @Test
    public void getOP() throws IOException {
        PJoinPDU pJoinPDU = new PJoinPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pJoinPDU.deSerializePDU(inputStream);
        assertEquals(16, pJoinPDU.getOP());
    }

    @Test
    public void getIdLength() throws IOException {
        PJoinPDU pJoinPDU = new PJoinPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pJoinPDU.deSerializePDU(inputStream);
        assertEquals(5, pJoinPDU.getIdLength());
    }

    @Test
    public void getTimeStamp() throws IOException {
        PJoinPDU pJoinPDU = new PJoinPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pJoinPDU.deSerializePDU(inputStream);
        int unixTime = (int) Instant.now().getEpochSecond();
        assertEquals(unixTime, pJoinPDU.getTimeStamp());
    }

    @Test
    public void testPrintStr() throws IOException {
        PJoinPDU pJoinPDU = new PJoinPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        pJoinPDU.deSerializePDU(inputStream);
        pJoinPDU.printStr();
    }
}
