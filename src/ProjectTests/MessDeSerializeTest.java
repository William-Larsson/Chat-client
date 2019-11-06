package ProjectTests;
import ChatService.PDUManagment.MessPDU;
import org.junit.Before;
import java.io.IOException;

/**
 * This class of JUnit tests server the purpose of testing all the features related to the
 * DeSerializing of the PDU. No tests for the Serializing of the PDU can be found here.
 */
public class MessDeSerializeTest {

    private String message = "Welcome";
    private String nickname = "Carl";

    @Before
    public void setup() throws IOException {
        MessPDU messageReceived = new MessPDU();
        MessPDU messPDU = new MessPDU();
        byte[] pduArr = messPDU.serializePDU(message);

    }


}
