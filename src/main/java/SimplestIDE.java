import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by sfaxi19 on 05.01.17.
 */
public class SimplestIDE {


    private ServerSocket serverSocket;
    private int serverPort = 55555;
    private static String PATH = new File("").getAbsolutePath();

    public SimplestIDE(int serverPort) {
        this.serverPort = serverPort;
    }

    public void init() {

    }

    public void run() throws IOException, InterruptedException {
        serverSocket = new ServerSocket(serverPort);
        serverSocket.setSoTimeout(1000);
        System.out.print("Listening...");
        XMLInputStreamParser parser = null;
        while (true) {
            if (Thread.interrupted()) {
                if (parser != null) parser.interrupt();
                closeConnections();
                System.out.println("Server thread interrupted");
                return;
            }

            Socket socket;
            try {
                socket = serverSocket.accept();
                System.out.println("client accept.");
            } catch (SocketTimeoutException ex) {
                continue;
            }

            System.out.println("Address: " + socket.getInetAddress() + "\nPort:" + socket.getPort());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), false);
            parser = new XMLInputStreamParser();

            RequestData requestData = parser.parsingInputStream(in);
            System.out.println(requestData);
            runTasks(requestData, out);
            out.close();
            socket.close();
        }
    }

    private void runTasks(RequestData requestData, PrintWriter out) throws IOException, InterruptedException {
        XMLOutputStreamWriter writerXML = new XMLOutputStreamWriter();
        int compileExitCode=-1;
        int runExitCode=-1;
        if (requestData.doComplie()) {
            //System.out.println(requestData.getCode());
            FileMaster.saveTextToFile(PATH + "/folder/", "clean", "rm *");
            console(true, "bash", "clean");
            FileMaster.saveTextToFile(
                    PATH + "/folder/",
                    requestData.getClassname() + ".java",
                    requestData.getCode());
            FileMaster.saveTextToFile(PATH + "/folder/", "compile", "javac " + requestData.getClassname() + ".java");
            ConsoleStreams consoleStreams = console("bash", "compile");
            compileExitCode = writerXML.writeStreamTag(consoleStreams, out, "compile-messages");
        }
        if (requestData.doRunning()&&(compileExitCode==0)) {
            FileMaster.saveTextToFile(PATH + "/folder/", "run", "java " + requestData.getClassname());
            ConsoleStreams consoleStreams = console("bash", "run");
            runExitCode = writerXML.writeStreamTag(consoleStreams, out, "code-run");
        }
        if (requestData.gettingJarFile()) {
            String filename = requestData.getClassname() + ".jar";
            FileMaster.saveTextToFile(PATH + "/folder/", "manifest",
                    "Manifest-Version: 1.0\n" +
                    "Main-Class: " + requestData.getClassname() + "\n");

            FileMaster.saveTextToFile(PATH + "/folder/", "package",
                    "jar cvfm " + filename + " manifest .");
            console(true, "bash", "package");
            writerXML.writeFileToTag(out, filename,
                    FileMaster.getFileBufferedReader(PATH + "/folder/", filename));

        }
        writerXML.writeSimpleTag(out, "/packet");
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SimplestIDE ide = new SimplestIDE(((args.length != 0) ? Integer.decode(args[0]) : 55555));
        ide.init();
        ide.run();
    }

    private void closeConnections() throws IOException {
        serverSocket.close();
    }

    private static ConsoleStreams console(String... commands) throws InterruptedException {
        return console(false, commands);
    }

    public static ConsoleStreams console(boolean wait, String... commands) throws InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.directory(new File("folder"));
        ConsoleStreams consoleStreams = new ConsoleStreams();
        try {
            consoleStreams.process = builder.start();
            if(wait) consoleStreams.process.waitFor();
            consoleStreams.inputs = new BufferedReader(new InputStreamReader(consoleStreams.process.getInputStream()));
            consoleStreams.errors = new BufferedReader(new InputStreamReader(consoleStreams.process.getErrorStream()));
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error");
            return null;
        }
        return consoleStreams;
    }
}