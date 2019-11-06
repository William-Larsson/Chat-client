package ProjectTests;
import ChatService.PDUManagment.JoinPDU;
import org.junit.Test;
import org.junit.Before;
import java.nio.charset.StandardCharsets;
import static org.junit.Assert.*;

/**
 * Tests for the JoinPDU class.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 3 Oct. 2018.
 */
public class JoinPDUTest {

    private byte[] temp;

    @Before
    public void setup(){
        JoinPDU join = new JoinPDU();
        temp = join.serializePDU("Kalle");
    }

    /**
     * test that the package total length is the same as expected.
     */
    @Test
    public void testIdLength(){
        assertEquals(12, temp.length);
    }

    /**
     * Test that OP number is correct.
     */
    @Test
    public void testOp() {
        assertEquals(12, temp[0]);
    }


    /**
     * Test that the ID length is the same as the length of the given nickname,
     * which in this case is "Kalle".
     */
    @Test
    public void idLengthTest(){
        assertEquals(5, temp[1]);
    }

    /**
     * Make sure there is only padding och position 2 and 3 in the PDU byte[].
     */
    @Test
    public void paddingTest(){
        assertEquals(0, temp[2]);
        assertEquals(0, temp[3]);
    }

    /**
     * Creates a new array containing only the ID name or nickname of the client.
     */
    @Test
    public void idTest(){
        byte[] nickname = new byte[8];
        System.arraycopy(temp, 4, nickname, 0, 8);
        String str = new String(nickname, StandardCharsets.UTF_8).trim();
        assertEquals("Kalle", str);
    }

    @Test
    public void idNameIsFourBytesTest(){
        JoinPDU join = new JoinPDU();
        byte[] temp2 = join.serializePDU("Sara");
        assertEquals(8, temp2.length);

        byte[] nickname = new byte[4];
        System.arraycopy(temp2, 4, nickname, 0, 4);
        String str = new String(nickname, StandardCharsets.UTF_8).trim();
        assertEquals("Sara", str);
    }
}