import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
  public static void main(String[] args) throws Exception {
    InetAddress ip = InetAddress.getLocalHost(); // Get IP address, can get from args instead. 
    byte[] sendmessage = new byte[1024];
    byte[] receivemessage = new byte[1024];
    int sendport = 5298; //set destination port

    try {
      //Add threading here when scaling
      DatagramSocket clientsocket = new DatagramSocket(); //Binds the DatagramSocket to any available local port.
      System.out.print("Enter text: "); //match this with GUI
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
      String input = in.readLine();//Get user input
      System.out.println("Output message: "+input); //match with GUI
      sendmessage = input.getBytes(); //Convert message string to bytes
      //Checksum origin here
      DatagramPacket sendpacket = new DatagramPacket(sendmessage, sendmessage.length, ip, sendport); 
      clientsocket.send(sendpacket);

      
      DatagramPacket receivepacket = new DatagramPacket(receivemessage, receivemessage.length);
      clientsocket.receive(receivepacket);
      String text = new String(receivepacket.getData());
      //Checksum result here
      System.out.println("Received Message: "+ text);
      clientsocket.close();
    }
    catch (SocketTimeoutException ex) {
            System.out.println("Timeout error: " + ex.getMessage());
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
            ex.printStackTrace();
        }
  }
}