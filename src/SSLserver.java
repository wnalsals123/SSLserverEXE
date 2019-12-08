import javax.net.ssl.*;
import java.io.*;
import java.util.Scanner;

public class SSLserver {
    public static void main(String[] arg){
        // 키 경로 입력.
        Scanner scan = new Scanner(System.in);
        System.out.print("Input key path: ");
        String s = scan.nextLine();

        // SSLserver 객체 생성.
        SSLserver SSLs = new SSLserver();

        // 연결 대기.
        SSLs.SSL_Connection(s);
    }
    public void SSL_Connection(String key_path){
        try{
            // KeyStore 툴을 이용해서 만든 파일 설정.
            System.setProperty("javax.net.ssl.keyStore", key_path);
            System.setProperty("javax.net.ssl.keyStorePassword", "12142");
            System.setProperty("javax.net.debug", "ssl");

            // 서버 소켓 팩토리 생성.
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

            // 서버 소켓 생성. 1115는 포트 번호.
            SSLServerSocket sslserversocket = (SSLServerSocket)sslserversocketfactory.createServerSocket(1115);

            // SSL RSA 통신을 통해 서버에 로그인.
            System.out.println("SSL Client Wait...");
            while(true){
                try {
                    SSLSocket socket = (SSLSocket)sslserversocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String fromClient = in.readLine();
                    if(fromClient.equals("OK")) { // 로그인 성공 시
                        System.out.println("SSL 로그인 성공");
                        sslserversocket.close();
                        break;
                    }
                }catch (IOException e){
                    System.out.println(e);
                }
            }

            // 클라이언트와 통신 시작.
            Server s = new Server();
            s.Connection();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
