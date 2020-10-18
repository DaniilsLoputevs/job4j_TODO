package ajax;

import ahelptools.CustomLog;
import ajax.webhelp.DateTransform;
import ajax.webhelp.JC;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Category;
import models.Task;
import models.User;
import store.CategoryStore;
import store.TaskStore;
import store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("UTF-8");
        String action = req.getParameter("server_action");
//        CustomLog.log("server action", action);
        if ("GET_TABLE".equals(action)) {
            sendTable(req, resp);
        } else if ("GET_CATEGORIES".equals(action)) {
            sendCategories(req, resp);
        }
    }

    private void sendTable(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/json");

        List<Task> taskList = TaskStore.instOf().getAll();

        var list = new ArrayList<String>();
        taskList.forEach(task -> list.add(task.toJson()));
        String tasksJson = JC.wrapList(list);

//        CustomLog.log("tasksJson", tasksJson);

        try (var writer = new PrintWriter(resp.getOutputStream())) {
            writer.write(tasksJson);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCategories(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> categories = CategoryStore.instOf().getAll();
//        CustomLog.log("categories", categories);

        try (var writer = new PrintWriter(resp.getOutputStream())) {
            new ObjectMapper().writeValue(writer, categories);
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
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("server_action");
        if ("ADD_TASK".equals(action)) {
            addTask(req, resp);
        } else if ("UPD_TABLE".equals(action)) {
            updTable(req, resp);
        }
    }

    private void addTask(HttpServletRequest req, HttpServletResponse resp) {
//        CustomLog.log("START AddTask()");
        String desc = req.getParameter("desc");
        String creator = req.getParameter("creator");
        String categoryIds = req.getParameter("categoryIds");

//        CustomLog.log("desc", desc);
//        CustomLog.log("creator", creator);
//        CustomLog.log("categoryIds", categoryIds);

        User user = UserStore.instOf().getByName(creator);
        List<Category> categories = getCategoriesByIds(categoryIds);
//        CustomLog.log("user:" + user);
//        CustomLog.log("categories:" + categories);

        Task temp = new Task(desc, categories, user);
//        CustomLog.log("temp:" + temp);

        TaskStore.instOf().add(temp);
//        CustomLog.log("FINISH AddTask()");
    }

    private static List<Category> getCategoriesByIds(String ids) {
        List<Category> rsl = new ArrayList<>();

        for (String StringId : ids.split("-")) {
            int intId = Integer.parseInt(StringId);
            rsl.add(CategoryStore.instOf().getById(intId));
        }
        return rsl;
    }


    /**
     * very not obvious point.
     * From front we get Task[] BUT without param "creator"(User class).
     * This param with get as well, but only as String[].
     * We need to convert this User.name into User by found this user in DB by Name.
     * Finally, we found all "creators" and we set it in Task[] in forEach.
     *
     * @param req  -
     * @param resp -
     */
    private void updTable(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonTasks = req.getParameter("tasks");
            String jsonCategories = req.getParameter("categories");
            String jsonCreators = req.getParameter("creators");
            String createdDates = req.getParameter("createdDates");


            CustomLog.log("jsonCreators", jsonCreators);
            CustomLog.log("jsonCategories", jsonCategories);

            ObjectMapper objectMapper = new ObjectMapper();
            Task[] tasks = objectMapper.readValue(jsonTasks, Task[].class);
            String[] categories = objectMapper.readValue(jsonCategories, String[].class);
            String[] users = objectMapper.readValue(jsonCreators, String[].class);
            String[] dates = objectMapper.readValue(createdDates, String[].class);

//            CustomLog.log("users", Arrays.toString(users));

            for (int i = 0; i < tasks.length; i++) {
                User creator = UserStore.instOf().getByName(users[i]);
                tasks[i].setCreator(creator);

                List<Category> categoriesRsl = new ArrayList<>();
                for (var temp : categories[i].split(", ")) {
                    categoriesRsl.add(CategoryStore.instOf().getByName(temp));
                }
                tasks[i].setCategory(categoriesRsl);

                tasks[i].setCreated(DateTransform.toBack(dates[i]));
            }

            var log = Arrays.asList(tasks);
            CustomLog.log("taskList", log);

            TaskStore.instOf().updateAll(tasks);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
