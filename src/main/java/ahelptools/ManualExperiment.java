package ahelptools;

import models.Task;
import store.TaskStore;

import java.util.List;

/**
 * manual tool - not for production.
 */
public class ManualExperiment {


    public static void main(String[] args) {
        List<Task> taskList = TaskStore.instOf().getAll();
//        CustomLog.log("taskList", taskList);

//        try (var writer = new PrintWriter(resp.getOutputStream())) {
//            ObjectMapper objectMapper = new ObjectMapper();


//        try {
//            new ObjectMapper().writeValue(writer, taskList);
//            new ObjectMapper().writeValue(System.out, taskList);

            CustomLog.log("NEW\r\n \r\n");
            CustomLog.log("to Json", taskList.get(0).toJson());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//            writer.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
