package ChatService.SocketAbstraction;
import ChatService.PDUManagment.JoinPDU;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class will send data in the shape of byte[] to the server in
 * order to perform a certain task. At first it will send a join-
 * -request to the chat server, and later the messages sent will
 * either be messages from the user or a request to exit the
 * chat server (as well as the program as a whole).
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 21 Oct. 2018.
 */
public class OutputQueueThread extends Thread{
    private LinkedBlockingQueue<byte[]> outputQueue;
    private DataOutputStream dataOut;
    private String nickname;
    private Thread thread = null;

    /**
     * Constructor that is given the output stream to the
     * server, as well as the nickname of the chat client.
     * Will also initialize an outgoing queue where
     * outgoing data is stored before being written the the stream.
     *
     * @param nickname = the nickname of the chat client
     * @param dataOut  = output stream towards the server.
     */
    OutputQueueThread(String nickname, DataOutputStream dataOut) {
        this.dataOut = dataOut;
        this.nickname = nickname;
        outputQueue = new LinkedBlockingQueue<>();
    }

    /**
     * The run method, used as a thread that initially will request
     * to join a certain server, and then send messages to said
     * server using an output stream.
     */
    @Override
    public void run() {
        // First thing to do, send join to the server
        JoinPDU joinPDU = new JoinPDU();
        try {
            dataOut.write(joinPDU.serializePDU(nickname));
            dataOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // After joining, write messages or quit.
        while(thread != null){
            if(outputQueue.size()!=0){
                try {
                    dataOut.write(outputQueue.take());
                    dataOut.flush();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * An incoming byte[] that is to be sent. Is added to
     * the queue of outgoing data while waiting to be sent.
     *
     * @param b = the byte[] to be sent.
     */
    void addToOutputQueue(byte[] b) {
        outputQueue.add(b);
    }


    /**
     * Sets the thread to either be the current thread for this class,
     * or null if the thread should no longer execute.
     *
     * @param thread = the thread for this instance of the class.
     */
    void setThread(Thread thread){
        this.thread = thread;
    }
}