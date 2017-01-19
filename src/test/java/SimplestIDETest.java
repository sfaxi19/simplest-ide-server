import org.junit.Test;

import static org.junit.Assert.*;

public class SimplestIDETest {

    @Test
    public void consoleTest() throws Exception {
        ConsoleStreams consoleStreams = SimplestIDE.console(true, "javach", "-version");
        assertEquals(0, (consoleStreams != null) ? consoleStreams.process.exitValue() : 1);
    }

}