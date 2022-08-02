package chat_client;

import com.example.mdp.terminalclientmdp.chat.Observer;
import com.example.mdp.terminalclientmdp.chat.Subject;
import com.example.mdp.terminalclientmdp.model.ContextHolder;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;

public class ChatClient implements Subject {
    private Socket s;
    private BufferedReader br;
    private BufferedWriter bw;
    private volatile boolean isActive = true;
    private Observer observer;
    private Thread t;

    public ChatClient(int port)
    {
        try {
            s = new Socket("localhost",port);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            bw.write("hello#"+ ContextHolder.getInstance().getLoggedInCheckpointID());
            bw.newLine();
            bw.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void register(Observer observer)
    {
        this.observer = observer;
    }

    Runnable listener = () ->{
        try {
            String contactsMessage = br.readLine();
            String[] contacts = contactsMessage.split("#");
            if (contacts.length > 0)
                Platform.runLater(() -> observer.initialiseContacts(contacts));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
      while(isActive){
          try {
              String message = br.readLine();
              Platform.runLater( () ->{
              observer.update(message);});
          }
          catch(IOException e)
          {
              e.printStackTrace();
          }
      }
    };

    public void startClient()
    {
         t = new Thread(listener);
         t.start();
    }

    public void sendMessage(String message) throws IOException
    {
        bw.write(message);
        bw.newLine();
        bw.flush();
    }

    public void stop()
    {
        isActive=false;
        try {
            t.join();
            if (s != null) {
                s.close();
            }
            if (br != null) {
                br.close();
            }
            if (bw != null) {
                bw.close();
            }
        }
        catch(IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
