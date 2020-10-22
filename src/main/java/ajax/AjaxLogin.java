package ajax;

import models.User;
import store.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ajax.webhelp.ResponseIo.writeToResponseJacksonObjectMapper;

/**
 * url-pattern: /login.ajax
 */
public class AjaxLogin extends HttpServlet {


    /**
     * Processing ajax POST requests:
     * AUTH_USER.
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

        String authEmail = req.getParameter("email");
        String authPassword = req.getParameter("password");

        User user = UserStore.instOf().getByEmail(authEmail);
        String answer;

        if (user.getName() != null) {
            answer = (user.getPassword().equals(authPassword))
                    ? user.getName() : "incorrect Password.";
        } else {
            answer = "user Not Founded.";
        }
        writeToResponseJacksonObjectMapper(resp, answer);
    }

    /**
     * Processing all ajax POST requests:
     * REG_USER
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

        UserStore.instOf().add(new User(-1, name, email, password));
    }
}
