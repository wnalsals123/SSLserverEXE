import javax.net.ssl.*;
import java.io.*;
import java.util.Scanner;

public class SSLserver {
    public static void main(String[] arg){
        System.out.print("[네트워크 보안 Term Project]SSLserver by keystore\n@@@@@\n@@@@@\n");
        boolean connection = false;
        do{
            String path, password, port;
            Scanner scan = new Scanner(System.in);

            // 키 경로 입력.
            System.out.print("Input key path: ");
            path = scan.nextLine();

            // 키 비밀번호 입력.
            System.out.print("Input key password(default:12142): ");
            password = scan.nextLine();

            // 포트 설정.
            System.out.print("Input port number: ");
            port = scan.nextLine();

            if(path.equals("") || password.equals("") || port.equals(""))
                System.out.print("Wrong Input!\n");
            else{
                // SSLserver 객체 생성.
                SSLserver SSLs = new SSLserver();

                // 클라이언트 연결 대기.
                connection = SSLs.SSL_Connection(path, password, Integer.parseInt(port));
            }
        }while (!connection);
    }
    /**SSL 연결*/
    public boolean SSL_Connection(String key_path, String key_password, int port){
        try{
            // KeyStore 툴을 이용해서 만든 파일 설정.
            System.setProperty("javax.net.ssl.keyStore", key_path);
            System.setProperty("javax.net.ssl.keyStorePassword", key_password);
            System.setProperty("javax.net.debug", "ssl");

            // 서버 소켓 팩토리 생성.
            SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();

            // 서버 소켓 생성. 1115는 포트 번호.
            SSLServerSocket sslserversocket = (SSLServerSocket)sslserversocketfactory.createServerSocket(port);

            // SSL,RSA 통해 서버에 로그인.
            System.out.println("SSL Client Wait...");

            // SSL 소켓 대기
            SSLSocket socket = (SSLSocket)sslserversocket.accept();

            // 입력 스트림 생성.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String fromClient = in.readLine();

            // 로그인 시도.
            if(fromClient.equals("OK")) {
                System.out.println("SSL 로그인 성공");
                sslserversocket.close();
            }else{
                System.out.println("SSL 로그인 오류");
                sslserversocket.close();
                return false;
            }

            // 클라이언트와 통신 시작.
            Server s = new Server();
            s.Connection(port);

            // 연결 성공 시 true 반환.
            return true;
        }catch(Exception e){
            System.out.println(e);

            // 연결 실패 시 false 반환.
            return false;
        }
    }
}
