package ProjectTests;

import ChatService.PDUManagment.ParticipantsPDU;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Testing the Participants class
 */

public class ParticipantsTest {

    private byte[] temp;

    @Before
    public void setup() throws IOException {
        ParticipantsPDU participants = new ParticipantsPDU();
        //temp = participants.serializeParticipants();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        participants.deSerializePDU(inputStream);
    }

    @Test
    public void testPduLength(){
        assertEquals(16, temp.length);
    }

    @Test
    public void testGetOp() throws IOException {
        ParticipantsPDU participants = new ParticipantsPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        participants.deSerializePDU(inputStream);
        assertEquals(19, participants.getOP());
    }

    @Test
    public void testGetNumIDs() throws IOException {
        ParticipantsPDU participants = new ParticipantsPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        participants.deSerializePDU(inputStream);
        assertEquals(2, participants.getNumIDs());
    }

    @Test
    public void testGetLength() throws IOException {
        ParticipantsPDU participants = new ParticipantsPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        participants.deSerializePDU(inputStream);
        assertEquals(9, participants.getLength());
    }

    @Test
    public void testPrintParticipantList() throws IOException {
        ParticipantsPDU participants = new ParticipantsPDU();
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(temp));
        participants.deSerializePDU(inputStream);
        participants.printParticipantList();
    }

}
