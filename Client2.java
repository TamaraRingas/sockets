/**
 * The class Client2 sets up a network client for sending chat messages using the UDP protocol. 
 * The class sets up a datagram socket to send and receive datagram packets through. 
 * It calculates a checksum on the output message and on input data and displays it for data integrity validation.
 * @author RNGTAM002, GBLALI002, RJKRAH001.
 * @date 1st April 2021.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.CheckedInputStream;
import java.util.zip.Adler32;

public class Client2 {
  
  /**
  * Main method: 
  * Gets the IP address of the host.
  * Sets the destination port, which is the server's listening port, to 5298. 
  * Sets the boolean running value to true, where when the clients are finished communicating, this value is changed to false. 
  */ 
  public static void main(String[] args) throws Exception {
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address.
    int messageNum = 2;
    int sendport = 5298; //set destination port
    long sendChecksum;
    boolean running =  true;

    /**
    * Creates the second client's socket with port number 4445. 
    * This client first receives a message sent from client1. 
    */
    try {
      DatagramSocket clientsocket2 = new DatagramSocket(4445); 
      while(running){
        byte[] receivemessage = new byte[1024];
        DatagramPacket receivepacket = new DatagramPacket(receivemessage, receivemessage.length);
        clientsocket2.receive(receivepacket);
        String inputText = new String(receivepacket.getData(), 0, receivepacket.getLength());

        /**
        * If the message received is 'end', communication has finished and the client can leave the application.
        * The socket is then closed. 
        */
        if (inputText.equals("end")){
          running = false;
          clientsocket2.close();
          continue;
        }
        
        /**
        * The message is printed with an indication that it has been received. 
        */
        System.out.println("Received: "+ inputText); 

        /*
        * This is when the client wants to send a message. 
        * The client inputs the message which is then converted into bytes. 
        * The datagram packet of the message is sent to the server with port 5298. 
        */
        byte[] sendmessage = new byte[1024];
        System.out.print("Enter Message: "); //Display on GUI
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        String input = in.readLine();//Get user input

        System.out.println("Sent message " + messageNum + ": "+ input); //Display on GUI
        sendmessage = input.getBytes(); //Convert message string to bytes
        DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length, ip, sendport); 
        clientsocket2.send(sendpacket);
        messageNum += 2;

        /**
        * If the client has finished communicating, they can type end as an input to leave.
        * The socket is then closed. 
        */
        if (input.equals("end")){
          running = false;
          clientsocket2.close();
          continue;
        }

        /**
        * The same Checksum output calculation.
        * This calculates the checksum value based on the message to be sent. 
        * This value is printed for debugging purposes and is then compared to the checksum value calculated on the server side which is also printed. 
        */
       // Server server = new Server();
        ByteArrayInputStream bias = new ByteArrayInputStream(sendmessage);
        CheckedInputStream cis = new CheckedInputStream(bias, new Adler32());
        byte readBuffer[] = new byte[10];
        while(cis.read(readBuffer) >= 0) {
          sendChecksum = cis.getChecksum().getValue();
          //server.setSendChecksum(sendChecksum);
          //System.out.println("Checksum value: " + sendChecksum);
        }
      } 
    }
    
    /** 
    * Catching errors for Timeout exceptions and Input Output exceptions. 
    */
    catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
  }
}