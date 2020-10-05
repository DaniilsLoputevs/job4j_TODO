package ajax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Task;
import store.TaskStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

/**
 * url-pattern: /tasks.ajax
 */
public class AjaxQuery extends HttpServlet {

    /**
     * get all tasks.
     * <p>
     * goto: NONE - ajax script.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/json");

        List<Task> seatList = TaskStore.instOf().getAll();
        try (var writer = new PrintWriter(resp.getOutputStream())) {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(writer, seatList);

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processing all ajax POST requests.
     * <p>
     * server_action : String - how to processing this request.
     * desc : String - task description.
     * tasks : (String-JSON) Task[].
     * <p>
     * goto: NONE - ajax script.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("server_action");
        if ("ADD_TASK".equals(action)) {
            addTask(req, resp);
        } else if ("UPD_TABLE".equals(action)) {
            updTable(req, resp);
        }
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp) {
        String desc = req.getParameter("desc");
        Task temp = new Task(-1, desc, new Timestamp(System.currentTimeMillis()), false);
        TaskStore.instOf().add(temp);
    }

    private void updTable(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var jsonArr = req.getParameter("tasks");

            ObjectMapper objectMapper = new ObjectMapper();
            Task[] tasks = objectMapper.readValue(jsonArr, Task[].class);

            TaskStore.instOf().updateAll(tasks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}