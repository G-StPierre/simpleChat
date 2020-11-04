import ocsf.server.*;
import client.*;
import common.*;

import java.io.IOException;
import java.util.Scanner;



public class ServerConsole implements ChatIF {
	
	// Instance Variables
	
	EchoServer server;
	
	Scanner fromConsole;
	
	//
	
	// Constructor
	
	public ServerConsole(int port) {
		server = new EchoServer(port, this);
		
		fromConsole = new Scanner(System.in);
	}
	
	//
	
	// Instance methods
	
	public void accept() {
		try {
			
			String message;
			
			while(true){
				message = fromConsole.nextLine();
				server.handleMessageFromServer(message);
			}
			
			
		}catch (Exception e) {
			
		}
	}
	
//	public void handleMessageFromServer(String message) {
//		switch(message) {
//			case("#quit"):
//				System.out.println("Server is quitting.");
//				System.exit(0);
//				break;
//			case("#stop"):
//				System.out.println("Server no longer listening for new clients");
//				server.stopListening();
//				break;
//			case("close"):
//				System.out.println("Server is now closing");
//				try {
//					server.close();
//				} catch (IOException e) {
//					System.out.println("Unable to close server due to error");
//					System.out.println(e);
//				}
//				break;
//			case("#start"):
//				if(server.getStarted() == false) {
//					try {
//						server.listen();
//					} catch (IOException e) {
//						System.out.println("Unable to start server due to error");
//						System.out.println(e);
//					}
//				}
//				break;
//			case("getport"):
//				System.out.println(server.getPort());
//				break;
//		}
//		// Case for set port. 
//		
//		if (message.contains("#setport")){
//			int port = Integer.parseInt(message.substring(message.lastIndexOf(" ")));
//			System.out.println(port);
//			server.setPort(port);
//			System.out.println("Port has been changed to" + port);
//		}
//	}

	public void quit() {
		
	}
	
	public void listen() {
		try {
		server.listen();
	}catch(Exception e) {
		System.out.println("Server unable to listen due to erro");
		System.out.println(e);
	}
		}
	
	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		System.out.println("SERVER MSG>" + message);
		
	}
	
//	public static void main(String args[]) {
//		int port;
//		final int DEFAULT_PORT = 5555;
//		
//
//	    try
//	    {
//	      port = Integer.parseInt(args[0]);
//	    }
//	    catch(Exception e){
//	    	port = DEFAULT_PORT;
//	    }
//		
//		ServerConsole serverChat = new ServerConsole(port);
//		serverChat.accept();
//	}

}
