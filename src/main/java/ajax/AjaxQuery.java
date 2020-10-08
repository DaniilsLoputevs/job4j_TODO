package ajax;

import ahelptools.CustomLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Task;
import models.User;
import store.TaskStore;
import store.UserStore;

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
//        resp.setCharacterEncoding("UTF-8");
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
     * Processing ajax POST requests:
     * ADD_TASK
     * UPD_TABLE
     * <p>
     * server_action : String - how to processing this request.
     * user : String - session user.
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
        String creator = req.getParameter("creator");

        CustomLog.log("desc:" + desc);
        CustomLog.log("creator:" + creator);

        User user = UserStore.instOf().getByName(creator);
        CustomLog.log("user:" + user);

        Task temp = new Task(-1, desc, new Timestamp(System.currentTimeMillis()), false, user);

        CustomLog.log("temp:" + temp);
        TaskStore.instOf().add(temp);
    }

    /**
     * very not obvious point.
     *
     * @param req  -
     * @param resp -
     */
    private void updTable(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonTasks = req.getParameter("tasks");
            String jsonCreators = req.getParameter("creators");

//            CustomLog.log("jsonCreators", jsonCreators);

            ObjectMapper objectMapper = new ObjectMapper();
            Task[] tasks = objectMapper.readValue(jsonTasks, Task[].class);
            String[] users = objectMapper.readValue(jsonCreators, String[].class);

//            CustomLog.log("users", Arrays.toString(users));

            for (int i = 0; i < tasks.length; i++) {
                User creator = UserStore.instOf().getByName(users[i]);
                tasks[i].setCreator(creator);
            }

            TaskStore.instOf().updateAll(tasks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
