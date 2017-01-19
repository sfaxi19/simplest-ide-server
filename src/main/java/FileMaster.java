import java.io.*;

/**
 * Created by sfaxi19 on 12.10.16.
 */
public class FileMaster {

    public static String loadTextFromFile(String filepath) throws IOException {
        File f = new File(filepath);
        BufferedReader fin = new BufferedReader(new FileReader(f));
        StringBuffer text = new StringBuffer();
        String line;
        while ((line = fin.readLine()) != null) {
            text.append(line);
        }
        fin.close();
        return text.toString();
    }

    public static void saveTextToFile(String filepath, String filename, String data) throws IOException {
        File f = new File(filepath);
        System.out.println("mkdirs: " + f.mkdirs());
        f = new File(filepath + "/" + filename);
        BufferedWriter fout = new BufferedWriter(new FileWriter(f));
        fout.write(data);
        fout.flush();
        fout.close();
        System.out.println("saved - " + data.length());
    }

    public static BufferedReader getFileBufferedReader(String filepath, String filename) throws FileNotFoundException {
        File f = new File(filepath + "/" + filename);
        return new BufferedReader(new FileReader(f));
    }
}
