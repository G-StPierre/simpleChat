// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  boolean serverStarted;
  
  ChatIF serverClient;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI) 
  {
    super(port);
    this.serverClient = serverUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    String username= "";
    
//    if (client.getInfo("loginID") != null) {
//    	username = (String) client.getInfo("loginID");
//    	this.sendToAllClients(username + ">" + msg);
//    }
    
    if(msg.toString().contains("#login")) {
    	if (client.getInfo("loginID") != null) {
        	try {
        		System.out.println("User already logged in, clossing connection");
				client.close();
			} catch (IOException e) {
				System.out.println("An error has occured");
				System.out.println(e);
			}
        }
    	else {
    	username =  msg.toString().substring(msg.toString().lastIndexOf(" ") + 1);
    	client.setInfo("loginID", username);
    	}
    }
    
    if (client.getInfo("loginID") != null) {
    	username = (String) client.getInfo("loginID");
    	this.sendToAllClients(username + " > " + msg);
    }
      	
    
    //this.sendToAllClients(username + ">" + msg);
  }
    
  
  ///
  
  public void handleMessageFromServer(String message) {
	  
	  	if(!message.contains("#")) {
	  		//System.out.println(message);
	  		sendToAllClients(message);
	  		//sendToAllClients(serverClient.display(message));
	  		serverClient.display(message);
	  	}
		switch(message) {
			case("#quit"):
				System.out.println("Server is quitting.");
				System.exit(0);
				break;
			case("#stop"):
				System.out.println("Server no longer listening for new clients");
				stopListening();
				break;
			case("#close"):
				System.out.println("Server is now closing");
				try {
					close();
				} catch (IOException e) {
					System.out.println("Unable to close server due to error");
					System.out.println(e);
				}
				break;
			case("#start"):
				if(getStarted() == false) {
					try {
						listen();
					} catch (IOException e) {
						System.out.println("Unable to start server due to error");
						System.out.println(e);
					}
				}
				break;
			case("#getport"):
				System.out.println(getPort());
				break;
		}
		// Case for set port. 
		
		if (message.contains("#setport")){
			int port = Integer.parseInt(message.substring(message.lastIndexOf(" ") + 1));
			setPort(port);
			System.out.println("Port has been changed to " + port);
		}
	}
  
  
  ///
  
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
    serverStarted = true;
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
    serverStarted = false;
  }
  
  @Override
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("User " + client.getId() + " has connected");
  }
  
  @Override
  protected void clientDisconnected(
		    ConnectionToClient client) {
	  System.out.println("User " + client.getId() + " has disconnected");
  }
  
  //Getter
  
  public boolean getStarted() {return serverStarted;}
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    ServerConsole server = new ServerConsole(port);
    
    try 
    {
      server.listen(); //Start listening for connections
      server.accept();
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
