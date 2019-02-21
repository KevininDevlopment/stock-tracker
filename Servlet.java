
/**
 * The Servlet class.
 *
 * @author Kevin Amaya
 * @version 05/02/18
 */

import javax.servlet.*;
import javax.servlet.http.*;

public class Servlet extends HttpServlet {

  protected void doGet( HttpServletRequest request,
                        HttpServletResponse response)
        throws ServletException, IOException {

      doPost(request, response);
  }

  protected void doPost( HttpServletRequest request,
                         HttpServletResponse response)
        throws ServletException, IOException {

      response.getWriter().write("GET/POST response");
    }
}

    

