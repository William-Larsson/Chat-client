package ChatService.SocketAbstraction;
import ChatService.PDUManagment.*;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * This class will be used as a middle hand in the deserialize-ing process.
 * An already read byte from the inputStream will be given (op-number),
 * as well as the remainder of the inputStream. The op-number will then be
 * matched to the correct PDU and the rest of the inputStream will be handed
 * over to the correct PDU that will correctly deserialize the rest of the stream.
 * The "finished" PDU will then be returned.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 11 Oct. 2018.
*/
class DeSerialize {
    /**
     * This message will read the given op-number and redirect the input stream
     * to the correct PDU which will deSerialize the stream further.
     *
     * @param op = the op number corresponding to a specific PDU.
     * @param dataInputStream = the inputStream.
     * @return = the "finished" deSerialized PDU.
     * @throws IOException = in case something goes wrong with the inputStream.
     */
    PDU deSerialize(byte op, DataInputStream dataInputStream) throws IOException {

        switch (op) {
            case 4:// Slist
                SListPDU slPDU = new SListPDU();
                slPDU.deSerializePDU(dataInputStream);
                return slPDU;

            case 10:// Mess
                MessPDU mPDU = new MessPDU();
                mPDU.deserializePDU(dataInputStream);
                return mPDU;

            case 11:// Quit
                QuitPDU qPDU = new QuitPDU();
                qPDU.deSerializePDU(dataInputStream);
                return qPDU;

            case 16:// PJoin
                PJoinPDU pjPDU = new PJoinPDU();
                pjPDU.deSerializePDU(dataInputStream);
                return pjPDU;

            case 17:// PLeave
                PLeavePDU plPdu = new PLeavePDU();
                plPdu.deSerializePDU(dataInputStream);
                return plPdu;

            case 19:// Participants
                ParticipantsPDU parPdu = new ParticipantsPDU();
                parPdu.deSerializePDU(dataInputStream);
                return parPdu;

            default:
                System.err.println("Incoming PDU contains an invalid OP-number.");
                return null;
        }
    }
}
