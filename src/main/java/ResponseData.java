/**
 * Created by sfaxi19 on 19.01.17.
 */
public class ResponseData {

    private String compileMessages;
    private String runningData;
    private String jarFilename;
    private byte[] jarFile;


    public void setCompileMessages(String compileMessages) {
        this.compileMessages = compileMessages;
    }

    public void setRunningData(String runningData) {
        this.runningData = runningData;
    }

    public void setJarFilename(String jarFilename) {
        this.jarFilename = jarFilename;
    }

    public void setJarFile(byte[] jarFile) {
        this.jarFile = jarFile;
    }
}
