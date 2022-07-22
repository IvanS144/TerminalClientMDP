package chat_client;

import com.example.mdp.terminalclientmdp.Observer;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    private Socket s;
    private BufferedReader br;
    private BufferedWriter bw;

    private Observer observer;

    public ChatClient(int port)
    {
        try {
            s = new Socket("localhost",port);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
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
      while(s.isConnected()){
          try {
              String message = br.readLine();
              observer.update(message);
          }
          catch(IOException e)
          {
              e.printStackTrace();
          }
      }
    };

    public void startClient()
    {
        new Thread(listener).start();
    }

    public void sendMessage(String message)
    {
        try {
            bw.write(message);
            bw.newLine();
            bw.flush();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
