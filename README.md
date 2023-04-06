
# RCPC: Remote Control for Personal Computer

# What is RCPC?
The RCPC (Remote Control Personal Computer)
solution utilizes socket connections to remotely control a personal
computer, allowing for real-time control and monitoring,
including access to files and programs. The solution includes a
server application for the personal computer and a client for the
mobile device, both of which use a socket to communicate securely.
The server listens for connections and creates a new thread for
each client, while the client connects to the server and
communicates through the socket's input and output streams.
This solution offers a convenient way to remotely control personal
computers for various applications such as remote work and
remote access to personal files.
    
    
<h1 align="center">
  <img src="https://github.com/Chardyyy07/RCPC/blob/a5367a31a8cf15767d4f56e1660658dcd05a338e/Server(RCPC)/RCPC%20SERVER/logo.jpg" width="224px"/><br/>
  RCPC: Innovative Remote-Control Solution for Personal Computers
</h1>
<p align="center"><b>You can use your Android phone to remotely control your laptop or computer as long as both devices are connected to the same network.</b></p>

## :bulb: Features
:house: Home Fragment Button
1. Power Off
2. Restart PC
3. Lock PC
4. Shortcut Button for Control Panel
5. Shortcut Button for File Explorer
6. Shortcut Button for Control Panel
7. Shortcut Button for Calculator
8. Shortcut Button for Browser
9. Shortcut Button for Notepad
10. Multiple Buttons like CTRL, CAPS, Delete, ALT, SHIFT, ESC, TAB and Windows



:mouse: Client-Side Mouse Fragment
1. Control Mouse Movement
2. Mouse Left Click, Mouse Right Click

:keyboard: Client-Side Keyboard Fragment
1. Realtime keyboard to Desktop 
2. Enter Button
3. Clear All Button
4. Delete Button


## :warning: How to Use:
###### Step 1 :
Establish a connection between your phone and laptop/computer by using your phone's hotspot and connecting your laptop to it via Wi-Fi.
###### Step 2 :
Run the `RCPC.exe` file (It will create a server and will accept the request from the client android phone)
###### Step 3 :
- Download the 'Client(RCPC).apk' file to your Android device.
- On the Home Screen Fragment, press the 'CONNECT' button (it is looklike a Screenshare).
- You can now remotely control your PC and access additional features using the bottom navigation.


## 📎 📱 Screenshots

Client-Side Home Fragment | Client-Side Home Help Button Fragment
:-------------------------:|:-------------------------:
![](https://github.com/Chardyyy07/RCPC/blob/ce4509c20ffaff1ae51258c00c589652b3f02c01/Screenshots/Client-Side%20Home%20Fragment.jpg)  |  ![](https://github.com/Chardyyy07/RCPC/blob/a5ac518a21a1c4ced2126248d264d6471fd4d8af/Screenshots/Client-Side%20Home%20%20Help%20Button%20Fragment.jpg)

Client-Side Mouse Fragment | Client-Side Keyboard Fragment
:-------------------------:|:-------------------------:
![](https://github.com/Chardyyy07/RCPC/blob/22ce23c84139b4846504b7d826949209cee2c5cc/Screenshots/Client-Side%20Mouse%20Fragment.jpg)  |  ![](https://github.com/Chardyyy07/RCPC/blob/fc14c4161829f2e68b756068b8e97579eb7f1734/Screenshots/Client-Side%20Keyboard%20Fragment.jpg)

The Client is an Android app that establishes a socket connection with a server at a given IP address and port. It does this by creating an instance of the Connection class, which extends the AsyncTask class to perform network operations in the background thread. The doInBackground method of this class creates a socket connection with the given IP address and port, and the onPostExecute method updates a boolean flag to indicate whether the connection was successful or not. This app is likely used to control the mouse, keyboard of the server Computer remotely.



<h1 align="center">
  <img src="Screenshots/Server Side.png" width="224px"/><br/>
</h1>
<p align="center">
The Server is a Java program that sets up a server socket and listens for incoming connections from a client. Once a client connects, the  program retrieves the client's IP address and updates the GUI waiting label to indicate that a connection has been established. The program also enables the disconnect button, which when clicked, closes the client and server sockets and updates the waiting label to indicate that the connection has been disconnected. The program uses a Robot instance to simulate mouse and keyboard actions, based on the commands  received from the client through the input stream. This program is likely used to receive commands from the Android app and control the mouse and keyboard of the server machine accordingly.




# Acknowledgements
We would like to thank our professor, Mr. Utulo , for guiding us throughout the development of this project

# Prepared By:
    Flores, Richard John
    Nunez, John Andrei
    Montalvo, Regienald
    Mose, Thea Marie
