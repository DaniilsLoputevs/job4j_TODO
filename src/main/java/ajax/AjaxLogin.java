package ajax;

import models.User;
import store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * url-pattern: /login.ajax
 */
public class AjaxLogin extends HttpServlet {


    /**
     * Processing ajax POST requests:
     * AUTH_USER.
     *
     * <p>
     * goto: NONE - ajax script.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("server_action");
        if ("AUTH_USER".equals(action)) {
            authorizeUser(req, resp);
        }

    }

    private void authorizeUser(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/json");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        var user = UserStore.instOf().getByEmail(email);

        if (user.getName() != null) {
            if (user.getPassword().equals(password)) {
                writeToResponse(resp, "{\"user\": \"" + user.getName() + "\"}");
            } else {
                writeToResponse(resp, "{\"user\": \"incorrect Password.\"}");
            }
        } else {
            writeToResponse(resp, "{\"user\": \"user Not Founded.\"}");
        }
    }

    private <T> void writeToResponse(HttpServletResponse resp, T string) {
        try (var writer = new PrintWriter(resp.getOutputStream())) {
            writer.write((String) string);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processing all ajax POST requests:
     * REG_USER
     * <p>
     * server_action : String - how to processing this request.
     * name : String - input from Front
     * email : String - input from Front
     * password : String - input from Front
     * <p>
     * goto: NONE - ajax script.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("server_action");
        if ("REG_USER".equals(action)) {
            registerUser(req, resp);
        }
    }

    private void registerUser(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User temp = new User(-1, name, email, password);
        UserStore.instOf().add(temp);
    }
}
