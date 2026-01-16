package project.k_SoolMate.exception;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseSet {

    public static void createResponseSet(HttpServletResponse response, int status, String code, String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setContentType("application/json");

        String body = String.format("""
                
                {
                    "code" : %s,
                    "message" : %s
                }
               
                """,code,message);
        response.getWriter().write(body);
    }
}
