package ajax;

import ajax.webhelp.DateTransform;
import ajax.webhelp.JC;
import ajax.webhelp.ResponseIo;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ajax.webhelp.ResponseIo.writeToResponse;

/**
 * url-pattern: /tasks.ajax
 */
public class AjaxQuery extends HttpServlet {

    /**
     *  Processing ajax POST requests:
     *  GET_TABLE
     *  GET_CATEGORIES
     * <p>
     * goto: NONE - ajax script.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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

        writeToResponse(resp, tasksJson);
    }

    private void sendCategories(HttpServletRequest req, HttpServletResponse resp) {
        List<Category> categories = CategoryStore.instOf().getAll();
//        CustomLog.log("categories", categories);

        ResponseIo.writeToResponseJacksonObjectMapper(resp, categories);
    }

    /**
     * Processing ajax POST requests:
     * ADD_TASK
     * UPD_TABLE
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
        String categoryIds = req.getParameter("categoryIds");

//        CustomLog.log("desc", desc);
//        CustomLog.log("creator", creator);
//        CustomLog.log("categoryIds", categoryIds);

        User user = UserStore.instOf().getByName(creator);
        List<Category> categories = getCategoriesByIds(categoryIds);
//        CustomLog.log("user" + user);
//        CustomLog.log("categories" + categories);

        Task temp = new Task(desc, categories, user);
//        CustomLog.log("temp" + temp);

        TaskStore.instOf().add(temp);
    }

    private static List<Category> getCategoriesByIds(String ids) {
        List<Category> rsl = new ArrayList<>();

        for (String stringId : ids.split("-")) {
            int intId = Integer.parseInt(stringId);
            rsl.add(CategoryStore.instOf().getById(intId));
        }
        return rsl;
    }


    /**
     * Very not obvious point.
     *
     * Model sending by parts because for whole Model we need to get whole inner models.
     * From front we get part of inner models(name || id) we need to get whole
     * and set in Task like a fields.
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

//            CustomLog.log("jsonCreators", jsonCreators);
//            CustomLog.log("jsonCategories", jsonCategories);

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
            TaskStore.instOf().updateAll(Arrays.asList(tasks));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}