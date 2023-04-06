
# RCPC: Remote Control for Personal Computer

What is RCPC?

     Remote Control for Personal Computer (RCPC) system allows a user to control a computer remotely from an Android device. This can be useful in situations where the computer is located far away, or where it is not convenient or safe for the user to interact with the computer directly with the same network.

    The Server is a Java program that sets up a server socket and listens for incoming connections from a client. Once a client connects, the program retrieves the client's IP address and updates the GUI waiting label to indicate that a connection has been established. The program also enables the disconnect button, which when clicked, closes the client and server sockets and updates the waiting label to indicate that the connection has been disconnected. The program uses a Robot instance to simulate mouse and keyboard actions, based on the commands received from the client through the input stream. This program is likely used to receive commands from the Android app and control the mouse and keyboard of the server machine accordingly.

    The Client is an Android app that establishes a socket connection with a server at a given IP address and port. It does this by creating an instance of the Connection class, which extends the AsyncTask class to perform network operations in the background thread. The doInBackground method of this class creates a socket connection with the given IP address and port, and the onPostExecute method updates a boolean flag to indicate whether the connection was successful or not. This app is likely used to control the mouse, keyboard of the server Computer remotely.


# Presented By:
    Flores, Richard John
    Nunez, John Andrei
    Montalvo, Regienald
    Mose, Thea Marie


