/**
 * Created by sfaxi19 on 19.01.17.
 */
public class RequestData {

    private String code;
    private String classname;
    private boolean compile = false;
    private boolean run = false;
    private boolean getJar = false;
    public final static char COMPILE = 0x01;
    public final static char RUN = 0x02;

    public String getCode() {
        return code;
    }

    public String getClassname() {
        return classname;
    }

    public final static char GET_JAR_FILE = 0x04;

    public void setCode(String code) {
        this.code = code;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setTasks(char tasks) {
        if ((tasks & COMPILE) != 0) {
            setCompile(true);
        }
        if ((tasks & RUN) != 0) {
            setRun(true);
        }
        if ((tasks & GET_JAR_FILE) != 0) {
            setGetJar(true);
        }

    }

    public void setCompile(boolean compile) {
        this.compile = compile;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public void setGetJar(boolean getJar) {
        this.getJar = getJar;
    }

    public boolean doRunning(){
        return run;
    }

    public boolean doComplie(){
        return compile;
    }

    public boolean gettingJarFile(){
        return getJar;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "code='" + code + '\'' +
                ", classname='" + classname + '\'' +
                ", compile=" + compile +
                ", run=" + run +
                ", getJar=" + getJar +
                '}';
    }
}
