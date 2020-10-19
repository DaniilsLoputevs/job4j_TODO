package ajax.webhelp;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseIo {
    public static <T> void writeToResponse(HttpServletResponse resp, T string) {
        try (var writer = new PrintWriter(resp.getOutputStream())) {
            writer.write((String) string);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> void writeToResponseJacksonObjectMapper(HttpServletResponse resp,
                                                              T content) {
        try (var writer = new PrintWriter(resp.getOutputStream())) {
            new ObjectMapper().writeValue(writer, content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
