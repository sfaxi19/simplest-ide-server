import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by sfaxi19 on 19.01.17.
 */
public class XMLInputStreamParser {

    private boolean interrupt = false;
    private RequestData requestData = new RequestData();

    private boolean isInterrupted() {
        return interrupt;
    }

    public void interrupt() {
        interrupt = true;
    }

    public RequestData parsingInputStream(BufferedReader in) throws IOException {
        while (!isInterrupted()) {
            if (in.ready()) {
                String tmp = in.readLine();
                if (tmp.equals("<code>")) {
                    requestData.setCode(parsingCode(in));
                    continue;
                }
                if (tmp.equals("<classname>")) {
                    requestData.setClassname(parsingClassname(in));
                    continue;
                }
                if (tmp.equals("<tasks>")) {
                    requestData.setTasks(parsingTasks(in));
                    continue;
                }
                if (tmp.equals("</packet>")) {
                    break;
                }
            }
        }
        return requestData;
    }

    private String parsingCode(BufferedReader in) throws IOException {
        StringBuffer codeBuffer = new StringBuffer();
        while (!isInterrupted()) {
            if (in.ready()) {
                String tmp = in.readLine();
                if (tmp.equals("</code>")) {
                    return codeBuffer.toString();
                }
                codeBuffer.append(tmp).append("\n");
            }
        }
        return null;
    }

    private String parsingClassname(BufferedReader in) throws IOException {
        StringBuffer nameBuffer = new StringBuffer();
        while (!isInterrupted()) {
            if (in.ready()) {
                String tmp = in.readLine();
                if (tmp.equals("</classname>")) {
                    return nameBuffer.toString();
                }
                if (tmp.length()>2) nameBuffer.append(tmp);
            }
        }
        return null;
    }

    private char parsingTasks(BufferedReader in) throws IOException {
        char tasks = 0x00;
        boolean tagCompile = false;
        boolean tagRun = false;
        boolean tagGetJar = false;

        while (!isInterrupted()) {
            if (in.ready()) {
                String tmp = in.readLine();
                if (tmp.equals("<compile>")) tagCompile = true;
                if (tmp.equals("</compile>")) tagCompile = false;
                if (tmp.equals("<run>")) tagRun = true;
                if (tmp.equals("</run>")) tagRun = false;
                if (tmp.equals("<get-jar>")) tagGetJar = true;
                if (tmp.equals("</get-jar>")) tagGetJar = false;
                if (tagCompile) tasks |= ((tmp.equals("true")) ? RequestData.COMPILE : 0x00);
                if (tagRun) tasks |= ((tmp.equals("true")) ? RequestData.RUN : 0x00);
                if (tagGetJar) tasks |= ((tmp.equals("true")) ? RequestData.GET_JAR_FILE : 0x00);
                if (tmp.equals("</tasks>")) {
                    return tasks;
                }
            }
        }
        return tasks;
    }

}
