import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sfaxi19 on 19.01.17.
 */
public class XMLOutputStreamWriter {

    private boolean interrupt = false;

    private boolean isInterrupted() {
        return interrupt;
    }

    public void interrupt() {
        interrupt = true;
    }

    public int writeStreamTag(ConsoleStreams consoleStreams, PrintWriter out, String tag) throws IOException {
        boolean inputStatus = true;
        boolean errorStatus = true;
        int exitValue = -1;
        out.write("\n<" + tag + ">\n");
        while (!isInterrupted()) {
            if (consoleStreams.inputs.ready()) {
                String line = consoleStreams.inputs.readLine();
                System.out.println(line);
                out.write(line + "\n");
                out.flush();
            }
            if (consoleStreams.errors.ready()) {
                String line = consoleStreams.errors.readLine();
                System.out.println(line);
                out.write(line + "\n");
                out.flush();
            }
            try {
                exitValue = consoleStreams.process.exitValue();
                if(!consoleStreams.inputs.ready()&&!consoleStreams.errors.ready()) break;
            } catch(IllegalThreadStateException ex){

            }
        }
        out.write("Process " + tag + " finished with exit code " + exitValue);
        out.write("\n</" + tag + ">\n");
        out.flush();
        System.out.println("Process " + tag + " finished with exit code " + exitValue);
        return exitValue;
    }

    public void writeSimpleTag(PrintWriter out, String tag) {
        out.write("\n<"+ tag + ">\n");
        out.flush();
    }

    public void writeFileToTag(PrintWriter out, String filename, BufferedReader in) throws IOException {
        out.write("\n<get-jar>\n");
        out.write("\n<file-name>\n");
        out.write(filename);
        out.write("\n</file-name>\n");
        out.write("\n<file>\n");
        char[] buffer;
        while(!isInterrupted()) {
            buffer = new char[1024];
            int size = in.read(buffer);
            if(size==-1) break;
            System.out.println(size);
            out.write(buffer);
            out.flush();
        }
        System.out.println("\nits all\n");
        out.write("\n</file>\n");
        out.write("\n</get-jar>\n");
        out.flush();
    }
}
