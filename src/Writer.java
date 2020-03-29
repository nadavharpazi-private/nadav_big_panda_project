import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class Writer {

    protected final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

}
