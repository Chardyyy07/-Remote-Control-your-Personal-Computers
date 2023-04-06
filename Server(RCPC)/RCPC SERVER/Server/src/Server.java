import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

 
public class Server {
	private static ServerSocket server = null;
	private static Socket client = null;
	private static BufferedReader in = null;
	private static String line;
	private static boolean isConnected=true;
	private static Robot robot;
	private static int SERVER_PORT;
	private static JFrame frame;
	private static JButton disconnectButton;
	private static JLabel portLabel; // new label to display server port number
	private static JLabel ipLabel; // new label to display IP address

	public static void main(String[] args) {
		frame = new JFrame("Server");
		JPanel panel = new JPanel();
		JLabel waitingLabel = new JLabel("Waiting for Connection...", SwingConstants.CENTER);
		disconnectButton = new JButton("Disconnect");
		disconnectButton.setEnabled(false);

		portLabel = new JLabel("", SwingConstants.CENTER); // initialize portLabel
		ipLabel = new JLabel("", SwingConstants.CENTER); // initialize ipLabel

		panel.add(waitingLabel);
		panel.add(ipLabel); // add ipLabel to the panel
		panel.add(portLabel); // add portLabel to the panel
		panel.add(disconnectButton);

		frame.add(panel);
		frame.setSize(240, 150); // increased height to fit the new labels
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		boolean leftpressed=false;
		boolean rightpressed=false;
		System.out.println("Server has started");
		

		try{
			robot = new Robot();
			server = new ServerSocket(0); // bind to an available port automatically
			SERVER_PORT = server.getLocalPort(); // get the actual port number that was bound
			System.out.println("Listening on port " + SERVER_PORT);
			portLabel.setText("Server is listening on port " + SERVER_PORT); // set the text of the portLabel

			InetAddress localHost = InetAddress.getLocalHost(); // get the IP address of the local host
			String ipAddress = localHost.getHostAddress();
			System.out.println("Server IP Address: " + ipAddress);
			ipLabel.setText("Server IP Address: " + ipAddress); // set the text of the ipLabel

			client = server.accept();
			String clientIPAddress = client.getInetAddress().getHostAddress();
			System.out.println("Client is connected from IP Address: " + clientIPAddress);
			waitingLabel.setText("Connected to RCPC APP");
			disconnectButton.setEnabled(true);
			
			disconnectButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						client.close();
						server.close();
						isConnected = false;
						waitingLabel.setText("Connection Disconnected");
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});

			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			System.out.println("Error in opening Socket");
			System.exit(-1);
		} catch (AWTException e) {
			System.out.println("Error in creating robot instance");
            System.exit(-1);
		}
		
		//read input from client while it is connected
	    while(isConnected){
	        try{
			line = in.readLine(); //read input from client
			System.out.println(line); //print whatever we get from client
			String temp = "";

			 if(line.contains(",")){
				float movex=Float.parseFloat(line.split(",")[0]);//extract movement in x direction
				float movey=Float.parseFloat(line.split(",")[1]);//extract movement in y direction
				Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
				float nowx=point.x;
				float nowy=point.y;
				robot.mouseMove((int)(nowx+movex),(int)(nowy+movey));//Move mouse pointer to new location
			}
			//if user taps on mousepad to simulate a left click
			else if(line.contains("left_click")){
				//Simulate press and release of mouse button 1(makes sure correct button is pressed based on user's dexterity)
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			}

				//if user clicks open file explorer
				else if (line.equalsIgnoreCase("fileEx")) {
					try {
						// Windows
						Runtime.getRuntime().exec("explorer.exe /select," + "C:\\");
					} catch (IOException e) {
						e.printStackTrace();
					}


				}
				//if user clicks open control panel
				else if (line.equalsIgnoreCase("openSet")) {
					try {
						// Windows
					 	Runtime.getRuntime().exec("control panel");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				

				//if user clicks click ctrl key
				else if(line.equalsIgnoreCase("ctrl")){
					//Simulate press and release of ctrl key
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					}	

			//if user taps on right click button to simulate a right click
			else if(line.contains("right_click")){
				//Simulate press and release of mouse button 1(makes sure correct button is pressed based on user's dexterity)
				robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			}

			//if user clicks Caps lock
			else if(line.equalsIgnoreCase("caps")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_CAPS_LOCK );
				robot.keyRelease(KeyEvent.VK_CAPS_LOCK );
			}
			

			//if user clicks Delete
			else if(line.equalsIgnoreCase("delete")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_DELETE  );
				robot.keyRelease(KeyEvent.VK_DELETE  );
			}

			//if user clicks Down arrow key
			else if(line.equalsIgnoreCase("arrowDOWNKey")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_DOWN  );
				robot.keyRelease(KeyEvent.VK_DOWN  );
			}

			//if user clicks Down Enter
			else if(line.equalsIgnoreCase("enter")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_ENTER   );
				robot.keyRelease(KeyEvent.VK_ENTER   );
			}

			//if user 
			else if(line.equalsIgnoreCase("clearAll")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_A);
				robot.keyRelease(KeyEvent.VK_A);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_DELETE);
				robot.keyRelease(KeyEvent.VK_DELETE);

			}

			//if user clicks Backspace
			else if(line.equalsIgnoreCase("backspace")){
			//Simulate press and release of Backspace key
			robot.keyPress(KeyEvent.VK_BACK_SPACE);
			robot.keyRelease(KeyEvent.VK_BACK_SPACE);
			}

			//if user clicks ESC
			else if(line.equalsIgnoreCase("esc")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_ESCAPE    );
				robot.keyRelease(KeyEvent.VK_ESCAPE    );
			}


			//if user clicks Insert
			else if(line.equalsIgnoreCase("insert")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_INSERT     );
				robot.keyRelease(KeyEvent.VK_INSERT     );
			}

			//if user clicks Up arrow key
			else if(line.equalsIgnoreCase("arrowUPKey")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_UP  );
				robot.keyRelease(KeyEvent.VK_UP  );
			}

			//if user clicks Left arrow key
			else if(line.equalsIgnoreCase("arrowLEFTKey")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_LEFT  );
				robot.keyRelease(KeyEvent.VK_LEFT  );
			}

			//if user clicks Right arrow key
			else if(line.equalsIgnoreCase("arrowRIGHTKey")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_RIGHT  );
				robot.keyRelease(KeyEvent.VK_RIGHT  );
			}

			//if user clicks Tab
			else if(line.equalsIgnoreCase("tab")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_TAB  );
				robot.keyRelease(KeyEvent.VK_TAB  );
			}

			//if user clicks release shift key
			else if(line.equalsIgnoreCase("shift")){
				try {
					Robot robot = new Robot();
					robot.keyRelease(KeyEvent.VK_SHIFT);
				} catch (AWTException e) {
					// handle the exception
				}
			}

			//if user clicks Windows
			else if(line.equalsIgnoreCase("win")){
				
				robot.keyPress(KeyEvent.VK_WINDOWS  );
				robot.keyRelease(KeyEvent.VK_WINDOWS  );
			}

			//if user clicks ALT
			else if(line.equalsIgnoreCase("alt")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_ALT    );
				robot.keyRelease(KeyEvent.VK_ALT    );
			}

			//if user clicks HOME
			else if(line.equalsIgnoreCase("home")){
				//Simulate press and release of spacebar
				robot.keyPress(KeyEvent.VK_HOME     );
				robot.keyRelease(KeyEvent.VK_HOME     );
			}

			// If user clicks Power off
			else if (line.equalsIgnoreCase("shutDown")) {
				try {
					Runtime.getRuntime().exec("shutdown -s -t 0"); // shutdown immediately
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			//if user clicks lock screen
			else if(line.equalsIgnoreCase("lock_screen")){
				Runtime runtime = Runtime.getRuntime();
				try {
				    Process proc = runtime.exec("C:/WINDOWS/System32/rundll32.exe user32.dll,LockWorkStation");
				} catch (IOException e) {
				    e.printStackTrace();
				}
				
			}

			//if user clicks open calculator
			else if(line.equalsIgnoreCase("calculator")){
				try {
					Runtime.getRuntime().exec("calc.exe");
				} catch (IOException e) {
					// handle the exception
				}
			}

			//if user clicks open web browser
			else if(line.equalsIgnoreCase("webbrowser")){
				try {
					String url = "https://www.google.com/";
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
				} catch (IOException e) {
					// handle the exception
				}
			}

				//if user clicks notepad
			else if(line.equalsIgnoreCase("notepad")){
				try {
					Runtime.getRuntime().exec("notepad.exe");
				} catch (IOException e) {
					// handle the exception
				}
			}


			//if user clicks sleep the pc
			else if(line.equalsIgnoreCase("sleep_pc")){
				
				Runtime.getRuntime().exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");

			}

			//if user clicks restart the pc
			else if(line.equalsIgnoreCase("restart")){
				
				Runtime runtime = Runtime.getRuntime();
				Process proc = runtime.exec("shutdown -r");

			}

			

			//Exit if user ends the connection
			else if(line.equalsIgnoreCase("exit")){
				isConnected=false;
				//Close server and client socket
				server.close();
				client.close();
			}
			

			else {
				
				switch (line) {
			        case "a":
			        robot.keyPress(KeyEvent.VK_A    );
					robot.keyRelease(KeyEvent.VK_A    );
			        break;
			        case "b":
			        robot.keyPress(KeyEvent.VK_B    );
					robot.keyRelease(KeyEvent.VK_B    );
			        break;
			        case "c":
			        robot.keyPress(KeyEvent.VK_C    );
					robot.keyRelease(KeyEvent.VK_C    );
			        break;
			        case "d":
			        robot.keyPress(KeyEvent.VK_D    );
					robot.keyRelease(KeyEvent.VK_D    );
			        break;
			        case "e": 
			        robot.keyPress(KeyEvent.VK_E);
			        robot.keyRelease(KeyEvent.VK_E); 
			        break;
			        case "f": 
			        robot.keyPress(KeyEvent.VK_F);
			        robot.keyRelease(KeyEvent.VK_F); 
			        break;
			        case "g": 
			        robot.keyPress(KeyEvent.VK_G);
			        robot.keyRelease(KeyEvent.VK_G); 
			        break;
			        case "h": 
			        robot.keyPress(KeyEvent.VK_H);
			        robot.keyRelease(KeyEvent.VK_H); 
			        break;
			        case "i": 
			        robot.keyPress(KeyEvent.VK_I);
			        robot.keyRelease(KeyEvent.VK_I); 
			        break;
			        case "j": 
			        robot.keyPress(KeyEvent.VK_J);
			        robot.keyRelease(KeyEvent.VK_J); 
			        break;
			        case "k": 
			        robot.keyPress(KeyEvent.VK_K);
			        robot.keyRelease(KeyEvent.VK_K); 
			        break;
			        case "l": 
			        robot.keyPress(KeyEvent.VK_L);
			        robot.keyRelease(KeyEvent.VK_L); 
			        break;
			        case "m": 
			        robot.keyPress(KeyEvent.VK_M);
			        robot.keyRelease(KeyEvent.VK_M); 
			        break;
			        case "n": 
			        robot.keyPress(KeyEvent.VK_N);
			        robot.keyRelease(KeyEvent.VK_N); 
			        break;
			        case "o": 
			        robot.keyPress(KeyEvent.VK_O);
			        robot.keyRelease(KeyEvent.VK_O); 
			        break;
			        case "p": 
			        robot.keyPress(KeyEvent.VK_P);
			        robot.keyRelease(KeyEvent.VK_P); 
			        break;
			        case "q": 
			        robot.keyPress(KeyEvent.VK_Q);
			        robot.keyRelease(KeyEvent.VK_Q); 
			        break;
			        case "r": 
			        robot.keyPress(KeyEvent.VK_R);
			        robot.keyRelease(KeyEvent.VK_R); 
			        break;
			        case "s": 
			        robot.keyPress(KeyEvent.VK_S);
			        robot.keyRelease(KeyEvent.VK_S); 
			        break;
			        case "t": 
			        robot.keyPress(KeyEvent.VK_T);
			        robot.keyRelease(KeyEvent.VK_T); 
			        break;
			        case "u": 
			        robot.keyPress(KeyEvent.VK_U);
			        robot.keyRelease(KeyEvent.VK_U); 
			        break;
			        case "v": 
			        robot.keyPress(KeyEvent.VK_V);
			        robot.keyRelease(KeyEvent.VK_V); 
			        break;
			        case "w": 
			        robot.keyPress(KeyEvent.VK_W);
			        robot.keyRelease(KeyEvent.VK_W); 
			        break;
			        case "x": 
			        robot.keyPress(KeyEvent.VK_X);
			        robot.keyRelease(KeyEvent.VK_X); 
			        break;
			        case "y": 
			        robot.keyPress(KeyEvent.VK_Y);
			        robot.keyRelease(KeyEvent.VK_Y); 
			        break;
			        case "z": 
			        robot.keyPress(KeyEvent.VK_Z);
			        robot.keyRelease(KeyEvent.VK_Z); 
			        break;

			        case "A":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_A    );
					robot.keyRelease(KeyEvent.VK_A    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "B":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_B    );
					robot.keyRelease(KeyEvent.VK_B    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "C":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_C    );
					robot.keyRelease(KeyEvent.VK_C    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "D":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_D    );
					robot.keyRelease(KeyEvent.VK_D    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "E":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_E   );
					robot.keyRelease(KeyEvent.VK_E    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "F":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_F    );
					robot.keyRelease(KeyEvent.VK_F    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "G":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_G    );
					robot.keyRelease(KeyEvent.VK_G    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "H":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_H    );
					robot.keyRelease(KeyEvent.VK_H    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "I":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_I    );
					robot.keyRelease(KeyEvent.VK_I    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "J":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_J    );
					robot.keyRelease(KeyEvent.VK_J    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "K":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_K    );
					robot.keyRelease(KeyEvent.VK_K    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "L":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_L    );
					robot.keyRelease(KeyEvent.VK_L    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			       case "M":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_M    );
					robot.keyRelease(KeyEvent.VK_M    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "N":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_N    );
					robot.keyRelease(KeyEvent.VK_N    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "O":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_O    );
					robot.keyRelease(KeyEvent.VK_O    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "P":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_P    );
					robot.keyRelease(KeyEvent.VK_P    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "Q":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_Q    );
					robot.keyRelease(KeyEvent.VK_Q    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "R":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_R    );
					robot.keyRelease(KeyEvent.VK_R    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "S":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_S    );
					robot.keyRelease(KeyEvent.VK_S    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "T":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_T    );
					robot.keyRelease(KeyEvent.VK_T    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "U":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_U    );
					robot.keyRelease(KeyEvent.VK_U    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "V":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_V    );
					robot.keyRelease(KeyEvent.VK_V    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "W":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_W    );
					robot.keyRelease(KeyEvent.VK_W    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "X":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_X    );
					robot.keyRelease(KeyEvent.VK_X    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "Y":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_Y    );
					robot.keyRelease(KeyEvent.VK_Y    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "Z":
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_Z    );
					robot.keyRelease(KeyEvent.VK_Z    );
					robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			
			        case "`": 
			        robot.keyPress(KeyEvent.VK_BACK_QUOTE);
			        robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
			        break;

			        case "0": 
			        robot.keyPress(KeyEvent.VK_0);
			        robot.keyRelease(KeyEvent.VK_0);
			        break;
			        case "1": 
			        robot.keyPress(KeyEvent.VK_1);
			        robot.keyRelease(KeyEvent.VK_1);
			        break;
			        case "2": 
			        robot.keyPress(KeyEvent.VK_2);
			        robot.keyRelease(KeyEvent.VK_2);
			        break;
			        case "3": 
			        robot.keyPress(KeyEvent.VK_3);
			        robot.keyRelease(KeyEvent.VK_3);
			        break;
			        case "4": 
			        robot.keyPress(KeyEvent.VK_4);
			        robot.keyRelease(KeyEvent.VK_4);
			        break;
			        case "5": 
			        robot.keyPress(KeyEvent.VK_5);
			        robot.keyRelease(KeyEvent.VK_5);
			        break;
			        case "6": 
			        robot.keyPress(KeyEvent.VK_6);
			        robot.keyRelease(KeyEvent.VK_6);
			        break;
			        case "7": 
			        robot.keyPress(KeyEvent.VK_7);
			        robot.keyRelease(KeyEvent.VK_7);
			        break;
			        case "8": 
			        robot.keyPress(KeyEvent.VK_8);
			        robot.keyRelease(KeyEvent.VK_8);
			        break;
			        case "9": 
			        robot.keyPress(KeyEvent.VK_9);
			        robot.keyRelease(KeyEvent.VK_9);
			        break;

			         case "-": 
			        robot.keyPress(KeyEvent.VK_MINUS);
			        robot.keyRelease(KeyEvent.VK_MINUS);
			        break;

			         case "+": 
			         robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_EQUALS);
			        robot.keyRelease(KeyEvent.VK_EQUALS);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;

			         case "=":
			        robot.keyPress(KeyEvent.VK_EQUALS);
			        robot.keyRelease(KeyEvent.VK_EQUALS);
			        break;

			         case "~": 
			        robot.keyPress(KeyEvent.VK_BACK_QUOTE);
			        robot.keyRelease(KeyEvent.VK_BACK_QUOTE);
			        break;
			         case "!": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_1);
			        robot.keyRelease(KeyEvent.VK_1);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "@": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_2);
			        robot.keyRelease(KeyEvent.VK_2);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "#": 
			         robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_3);
			        robot.keyRelease(KeyEvent.VK_3);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "$": 
			         robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_4);
			        robot.keyRelease(KeyEvent.VK_4);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "%": 
			         robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_5);
			        robot.keyRelease(KeyEvent.VK_5);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "^":
			         robot.keyPress(KeyEvent.VK_SHIFT); 
			        robot.keyPress(KeyEvent.VK_6);
			        robot.keyRelease(KeyEvent.VK_6);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "&":
			         robot.keyPress(KeyEvent.VK_SHIFT); 
			        robot.keyPress(KeyEvent.VK_7);
			        robot.keyRelease(KeyEvent.VK_7);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "*": 
			         robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_8);
			        robot.keyRelease(KeyEvent.VK_8);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "(": 
			         robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_9);
			        robot.keyRelease(KeyEvent.VK_9);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case ")": 
			         
			       robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_0);
			        robot.keyRelease(KeyEvent.VK_0);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        
			        break;
			         case "_":
			         robot.keyPress(KeyEvent.VK_SHIFT); 
			        robot.keyPress(KeyEvent.VK_MINUS);
			        robot.keyRelease(KeyEvent.VK_MINUS);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			         case "\t": 
			        robot.keyPress(KeyEvent.VK_TAB);
			        robot.keyRelease(KeyEvent.VK_TAB);
			        break;
			         case "\n": 
			        robot.keyPress(KeyEvent.VK_ENTER);
			        robot.keyRelease(KeyEvent.VK_ENTER);
			        break;
			         case "[": 
			        robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
			        robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
			        break;
			         case "]": 
			        robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
			        robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
			        break;
			        case "\\": 
			        robot.keyPress(KeyEvent.VK_BACK_SLASH);
			        robot.keyRelease(KeyEvent.VK_BACK_SLASH);
			        break;
			        case "{": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_OPEN_BRACKET);
			        robot.keyRelease(KeyEvent.VK_OPEN_BRACKET);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "}": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
			        robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "|": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_BACK_SLASH);
			        robot.keyRelease(KeyEvent.VK_BACK_SLASH);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case ";": 
			        robot.keyPress(KeyEvent.VK_SEMICOLON);
			        robot.keyRelease(KeyEvent.VK_SEMICOLON);
			        break;
			        case ":": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_CLOSE_BRACKET);
			        robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;
			        case "\'": 
			        robot.keyPress(KeyEvent.VK_QUOTE);
			        robot.keyRelease(KeyEvent.VK_QUOTE);
			        break;

			        case "\"": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_QUOTE);
			        robot.keyRelease(KeyEvent.VK_QUOTE);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;

			        case "<": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_COMMA);
			        robot.keyRelease(KeyEvent.VK_COMMA);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;

			        case ",": 
			        robot.keyPress(KeyEvent.VK_COMMA);
			        robot.keyRelease(KeyEvent.VK_COMMA);
			        break;
			        case ".": 
			        robot.keyPress(KeyEvent.VK_PERIOD);
			        robot.keyRelease(KeyEvent.VK_PERIOD);
			        break;
			        case ">": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_PERIOD);
			        robot.keyRelease(KeyEvent.VK_PERIOD);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;

			        case "/": 
			        robot.keyPress(KeyEvent.VK_SLASH);
			        robot.keyRelease(KeyEvent.VK_SLASH);
			        break;

			        case "?": 
			        robot.keyPress(KeyEvent.VK_SHIFT);
			        robot.keyPress(KeyEvent.VK_SLASH);
			        robot.keyRelease(KeyEvent.VK_SLASH);
			        robot.keyRelease(KeyEvent.VK_SHIFT);
			        break;

			        case " ": 
			        robot.keyPress(KeyEvent.VK_SPACE);
			        robot.keyRelease(KeyEvent.VK_SPACE);
			        break;

			        case "\b": 
			        robot.keyPress(KeyEvent.VK_BACK_SPACE);
			        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
			        break;

			        default:
            // throw new IllegalArgumentException("Cannot type character " + line);
        }
			}
	        } catch (IOException e) {
				System.out.println("Read failed");
				System.exit(-1);
	        }
      	}
	}

	private static byte[] getMacBytes(String macAddress) {
		return null;
	}

}
