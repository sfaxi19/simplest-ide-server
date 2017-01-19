/**
 * Created by sfaxi19 on 07.01.17.
 */
public class DebugString {

    private String errorData = "";
    private String inputData = "";

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String errorData) {
        this.errorData = errorData;
    }

    public String getInputData() {
        return inputData;
    }

    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public boolean isEmpty(){
        return (inputData.length() == 0) && (errorData.length() == 0);
    }
}
