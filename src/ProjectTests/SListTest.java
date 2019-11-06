package ProjectTests;

import ChatService.PDUManagment.SListPDU;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Testing the SList class
 */
public class SListTest {
    // TODO Behöver testa för fler än två servrar, hur ser det ut då?

    private byte[] temp;

    @Before
    public void setUp() throws IOException {
        SListPDU sListPDU = new SListPDU();
        //temp = sListPDU.serializeSList();
    }

    @Test
    public void testGetNumOfServers() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        assertEquals(2, sListPDU.getNumOfServers());
    }

    @Test
    public void testNumOfServersFromList() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        //assertEquals(2, sListPDU.getNumberOfServersFromList());
    }

    @Test
    public void testFirstAddressPrint() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        sListPDU.getServersList().get(0).printAddress();
    }

    @Test
    public void testSecondAddressPrint() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        sListPDU.getServersList().get(1).printAddress();
    }

    @Test
    public void testFirstServerNamePrint() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        sListPDU.getServersList().get(0).printServerInfo();
    }

    @Test
    public void testSecondServerNamePrint() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        sListPDU.getServersList().get(1).printServerInfo();
    }

    @Test
    public void testFirstGetNumOfClients() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        assertEquals(15, sListPDU.getServersList().get(0).getNumOfClients());
    }

    @Test
    public void testSecondGetNumOfClients() throws IOException {
        SListPDU sListPDU = new SListPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        sListPDU.deSerializePDU(inputStream);
        assertEquals(25, sListPDU.getServersList().get(1).getNumOfClients());
    }

}
