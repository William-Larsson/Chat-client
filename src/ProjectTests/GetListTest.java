package ProjectTests;
import ChatService.PDUManagment.GetListPDU;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the GetListPDU class.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 3 Oct. 2018.
 */
public class GetListTest {

    private byte[] temp;

    /**
     * Create a GetListPDU before doing any test
     * (This is so that we don't need to make a new GetListPDU in every new test)
     */
    @Before
    public void setup() {
        GetListPDU getListPdu = new GetListPDU();
        temp = getListPdu.serializePDU();
    }


    /**
     * Test that PDU is indeed 4 bytes long as according to spec.
     */
    @Test
    public void testLength() {
        assertEquals(4, temp.length);
    }

    /**
     * Test that OP number is correct.
     */
    @Test
    public void testOp() {
        assertEquals(3, temp[0]);
    }

    /**
     * Makes sure that the rest of the byte[] is indeed all zeros.
     */
    @Test
    public void testOtherBytesEqualToZeros(){
        assertEquals(0, temp[1]);
        assertEquals(0, temp[2]);
        assertEquals(0, temp[3]);
    }
}
