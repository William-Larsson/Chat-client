package ChatService.SocketAbstraction;
import ChatService.PDUManagment.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * This class will read the incoming data from the server and call the
 * DeSerialize class which will create an appropriate PDU which
 * will added to a queue of PDU within the instance of this class.
 * The queue will later be read by the chat logic.
 *
 * Authors: William Larsson
 *          Johannes Baeckman
 *
 * Date: 21 Oct. 2018.
 */
public class InputQueueThread extends Thread{
    private LinkedBlockingDeque<PDU> inputQueue;
    private DataInputStream dataIn;
    private DeSerialize deSerialize;
    private Thread thread = null;

    /**
     * This constructor is given the input stream coming
     * from the server as well as initialize supporting
     * classes used.
     *
     * @param dataIn = input stream coming from the chat server.
     */
    InputQueueThread(DataInputStream dataIn){
        this.dataIn = dataIn;
        deSerialize = new DeSerialize();
        inputQueue  = new LinkedBlockingDeque<>();
    }


    /**
     * This thread will read the first byte in every new PDU in the
     * stream and call for methods in the DeSerialize-class that will
     * create a PDU. The PDU will later be added to the queue of PDU
     * that has been received but not yet handled by the chat logic.
     */
    @Override
    public void run(){
        while (thread != null) {
            try {
                inputQueue.add(deSerialize.deSerialize(dataIn.readByte(), dataIn));
            } catch (IOException ioe) {
                ioe.getStackTrace();
                // in case the connection is unexpectedly closed.
                if (thread != null){
                    System.err.println("Connection to server unexpectedly closed.");
                    inputQueue.add(new QuitPDU());
                    thread = null;
                }
            }
        }
        System.out.println("\nYou have left the session.");
    }


    /**
     * Simple getter method that return (and removes) the
     * PDU that is first in line in the queue.
     *
     * @return = the PDU first in line.
     * @throws InterruptedException = if there's an issue with the queue.
     */
    PDU getPDUFromQueue() throws InterruptedException {
        return inputQueue.take();
    }


    /**
     * Method that will investigate whether or not the queue of PDUs
     * is empty.
     *
     * @return = true if empty, false if not.
     */
    boolean isInputQueueEmpty() {
        return (inputQueue.size() == 0);
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