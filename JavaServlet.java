
/**
 * The JavaServlet class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 05/02/18
 */
 
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;


public class JavaServlet extends HttpServlet
{
    StockTrackerDB db;
    
    public void init() throws ServletException
    {
        try 
        {
            db = new StockTrackerDB();
        }
        catch (SQLException e)
        {
            System.out.println("SQLException creating a new database object.");
            e.printStackTrace();
            System.exit(1);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundException creating new database object.");
            e.printStackTrace();
            System.exit(1);
        }    
    }
    
    public void destroy()
    {
        try
        { 
            db.close();
        }
        catch (IOException e)
        {
            System.out.println("IOException closing database.");
            e.printStackTrace();
            System.exit(1);
        }
        catch (SQLException e)
        {
            System.out.println("SQLException closing databse.");
            e.printStackTrace();
            System.exit(1);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundException "
                                +"closing database.");
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    public void doGet( HttpServletRequest req,
                        HttpServletResponse res)
        throws ServletException, IOException {

      doPost(req, res);
  }

    public void doPost( HttpServletRequest request,
                         HttpServletResponse response)
        throws ServletException, IOException {

      User user = null;
      String userId;
      String password;
      boolean validAction = true;
      
      res.setContentType("text/html");
      PrintWriter out = res.getWriter();
      
      res.setHeader("Expires", "Tues, 01 Jan 1980 00:00:00 GMT");  // no cache allowed
    
      HttpSession session = req.getSession(true);  // get current session
      
      if(logonValidated(req,res,session))
      {
          if(session.getAttribute("uses") != null)  // remove temporary data
            session.removeAttribute("uses");
            
          if(session.getAttribute("userList") != null)  
            session.removeAttribute("userList");
            
          if(session.getAttribute("pswdExpired") != null)
            session.removeAttribute("pswdExpired");
            
          userID = (String)session.getAttribute("userID");
          
          if(req.getParameter("logout") != null &&
            req.getParameter("logout").equals("Log out"))
          {
              session.invalidate();
              res.sendRedirect("/index.html");
          }
          
          else
          {
              try
              { 
                  String action = req.getParameter("WSaction");
                  if(action != null)
                  {
                      if(action.equals("addStock"))
                      {
                        addStock(req, session);
                      }
                      else if(action.equals("addStockDesc"))
                      {
                        addStockDesc(req, session);
                      }
                      else if(action.equals("delStock"))
                      {
                          delStock(req, session);
                      }
                      else if(action.equals("chgPswd"))
                       {
                        chgPswd(req, session);
                       }
                       else if(action.equals("getQuote"))
                       {
                           String stock = req.getParameter("stockSymbol");
                           String quoteRequest = getQuote(stock);
                           session.setAttribute("quote", quoteRequest);
                           session.setAttribute("forwardTo", "mainForm.jsp");
                        }
                        else if(action.equals("clearQuote"))
                        {
                            if(session.getAttribute("quote") != null)
                                session.removeAttribute("quote");
                            session.setAttribute("forwardTo", "mainform.jsp");
                        }
                        else if(action.equals("addUser"))
                        {
                            addUser(req);
                            session.setAttribute("forwardTo", "mainform.jsp");
                        }
                        else if(action.equals("reqDelUser"))
                        {
                            ArrayList userData =
                                getUserData(req.getParameter("delUserId"));
                                if(userData.size() > 0) // the user exists
                                {
                                    session.setAttribute("userData", userData);
                                    session.setAttribute("forwardTo", "delUser.jsp");
                                }
                                else
                                    session.setAttribute("forwardTo", "badUserID.html");
                                }
                                else if(action.equals("delUser"))
                                {
                                    delUser(req.getParameter("delUserID"));
                                    session.setAttribute("forwardTo", "mainform.jsp");
                                }
                                else if (action.equals("reqUpdUser"))
                                {
                                    ArrayList userData =
                                        getUserData(req.getParameter("updUserId"));
                                    if(userData.size() > 0)  // the user exists
                                    {
                                        session.setAttribute("userData", userData);
                                        session.setAttribute("forwardTo", "updUser.jsp");
                                    }
                                    else
                                        session.setAttribute("forwardTo", "badUserID.html");
                                }
                                else if(action.equals("updUser"))
                                {
                                    updUser(req);
                                    session.setAttribute("forwardTo", "mainform.jsp");
                                }
                                else if(action.equals("listUsers"))
                                {
                                   listUsers(session);
                                   session.setAttribute("forwardTo", "listUsers.jsp");
                                }
                                else
                                   validAction = false;
                                if(validAction)  // forwrad to resource
                                {
                                    RequestDispatcher dispatcher =
                                        getServletContext().getRequestDispatcher("/"+
                                            (String)session.getAttribute("forwardTo"));
                                    dispatcher.forward(req, res);
                                }  
                                else  
                                {
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>StockTrader Application Internal Error</title>");
                                    out.println("</head>");
                                    out.println("<body text=\"#824423\" bgcolor=\"white\">");
                                    out.println("<h1>Internal Error</h1>");
                                    out.println("An invalid action was requested: "+action);
                                    out.println("</body>");
                                    out.println("</html>");
                                }
                            } // end of if action != null
                            else
                            {
                                 out.println("<html>");
                                 out.println("<head>");
                                 out.println("<title>StockTrader Application Internal Error</title>");
                                 out.println("</head>");
                                 out.println("<body text=\"#824423\" bgcolor=\"white\">");
                                 out.println("<h1>Internal Error</h1>");
                                 out.println("An null action was requested");
                                 out.println("</body>");
                                 out.println("</html>");
                            }
                        } // end of try
                        catch(PasswordException ex)
                        {
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>StockTrader Application Password Error</title>");
                            out.println("</head>");
                            out.println("<body text=\"#824423\" bgcolor=\"white\">");
                            out.println("<h1>Internal Password</h1>");
                            out.println(ex.getMessage());
                            out.println("</body>");
                            out.println("</html>");
                        }
                        catch(SQLException ex)
                        {
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>StockTrader Application Error</title>");
                            out.println("</head>");
                            out.println("<body text=\"#824423\" bgcolor=\"white\">");
                            out.println("<h1>SQL Exception</h1>");
                            out.println(ex.getMessage());
                            out.println("</body>");
                             out.println("</html>");
                        }
                        catch(IOException ex)
                        {
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>StockTrader Application Error</title>");
                            out.println("</head>");
                            out.println("<body text=\"#824423\" bgcolor=\"white\">");
                            out.println("<h1>IO Exception</h1>");
                            out.println(ex.getMessage());
                            out.println("</body>");
                            out.println("</html>");
                        }
                        catch(ClassNotFoundException ex)
                        {
                            out.println("<html>");
                            out.println("<head>");
                            out.println("<title>StockTrader Application Error</title>");
                            out.println("</head>");
                            out.println("<body text=\"#824423\" bgcolor=\"white\">");
                            out.println("<h1>Class not found</h1>");
                            out.println(ex.getMessage());
                            out.println("</body>");
                            out.println("</html>");
                        }              
                        
                    } //end of if log out else
                } // end of if logon validated
            } // end of doGet()
        
            private boolean logonValidated(HttpServletRequest req, HttpServletResponse res,
                                            HttpSession session)
                                            throws ServletException, IOException
            {
                User user = null;
                String userID;
                String password;
                boolean valid = false;
                int tries = 0;
                
                PrintWriter out = res.getWriter();
                
                // Check if user already logged on for session; if not attempt to do so
                if(session.getAttribute("userName") == null) // not a validated user
                {
                    if(session.getAttribtue("attempt") == null)  // no previous attempts
                        session.setAttribtue("attempt", new Integer(0)); // track attempts
                        
                    tries = ((Integer)(session.getAttribute("attempt"))).intValue();
                    if(tries < 3) // 3 tries
                    {
                        userID = req.getParameter("userId");
                        password = req.getParameter("password");
                        if(userID != null && password != null)
                        {
                            session.setAttribute("userID", userID);
                            try
                            {
                                user = getUser(userID);
                                if(validUserPswd(user, password))
                                {
                                    session.setAttribute("userName", user.getFirstName()
                                                        +" "+user.getLastName());
                                    session.removeAttribute("attempt");
                                    
                                    if(user.pswdISExpiring())
                                        session.setAttribute("uses",
                                                            new Integer(user.getPswdUses()));
                                    if(user.isAdmin())
                                        session.setAttribute("admin", new Boolean(true));
                                        
                                    session.setAttribute("stocks",
                                                        getStockList(userID));
                                    // call general screen
                                    RequestDispatcher dispatcher =
                                        getServletContext().getRequestDispatcher("/mainForm.jsp");
                                    dispatcher.forward(req,res);
                                }
                                else 
                                {
                                    throw new PassInvalidException("Invalid user id");
                                }
                            }
                            catch(PasswordExpiredException ex)
                            { 
                                session.setAttribute("pswdExpired", "yes");
                                session.setAttribute("userName", user.getFirstName()
                                                        +" "+user.getLastName());
                                session.setAttribute("pswd", password);
                                RequestDispatcher dispatcher = 
                                    getSerletContext().getRequestDispatcher("/chgPswd.jsp");
                                dispatcher.forward(req,res);
                            }
                            catch(PasswordInvalidException ex)
                            {
                                session.setAttribute("attmept", new Integer(++tries));
                                ex.resetCount();
     
                                if(tries < 3)
                                {
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>StockTrader Application Logon Error</title>");
                                    out.println("</head>");
                                    out.println("<body text=\"#824423\" bgcolor=\"white\">");
                                    out.println("<h1>Invalid User Id or Password</h1>");
                                    out.println(ex.getMessage());
                                    out.println("</body>");
                                    out.println("</html>");
                                }
                                else            // allows only 3 tries
                                {
                                    out.println("<html>");
                                    out.println("<head>");
                                    out.println("<title>StockTrader Application Logon Error</title>");
                                    out.println("</head>");
                                    out.println("<body text=\"#824423\" bgcolor=\"white\">");
                                    out.println("<h1>Too many attempts to log on!</h1>");
                                    out.println(ex.getMessage());
                                    out.println("</body>");
                                    out.println("</html>");
                                }
                            }
                            catch(PasswordException ex)
                            {
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>StockTrader Application Logon Error</title>");
                                out.println("</head>");
                                out.println("<body text=\"#824423\" bgcolor=\"white\">");
                                out.println("<h1>PasswordException</h1>");
                                out.println(ex.getMessage());
                                out.println("</body>");
                                out.println("</html>");
                            }
                            catch(ClassNotFoundException ex)
                            {
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>StockTrader Application Logon Error</title>");
                                out.println("</head>");
                                out.println("<body text=\"#824423\" bgcolor=\"white\">");
                                out.println("<h1>ClassNotFoundException</h1>");
                                out.println(ex.getMessage());
                                out.println("</body>");
                                out.println("</html>");
                            }
                            catch(SQLException ex)
                            {
                                out.println("<html>");
                                out.println("<head>");
                                out.println("<title>StockTrader Application Logon Error</title>");
                                out.println("</head>");
                                out.println("<body text=\"#824423\" bgcolor=\"white\">");
                                out.println("<h1>SQLException</h1>");
                                out.println(ex.getMessage());
                                out.println("</body>");
                                out.println("</html>");
                        }
                    }
                    else
                    {
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<title>StockTrader Application Logon Error</title>");
                        out.println("</head>");
                        out.println("<body text=\"#824423\" bgcolor=\"white\">");
                        out.println("<h1>Missung User Id or Password</h1>"); 
                        out.println("</body>");
                        out.println("</html>");
                    }
                }
                else
                {
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>StockTrader Application Logon Error</title>");
                    out.println("</head>");
                    out.println("<body text=\"#824423\" bgcolor=\"white\">");
                    out.println("<h1>Session invalidated.</h1><br>"
                            +"<h1>The maximum for lon on attempts was exceeded!<h1>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }
            else
                valid = true;
            return valid;
        }
        private String getQuote(String stockSymbol) throws IOException
        {
            StringBuffer buf = new StringBuffer();
            String dataLine;
            
            try
            {
                // Sends StockSymbol
                // Retrieves s=Symbol l1=price d1=date of last trade t1=time of last trade
                // c1=change (+increase or -decrease) 0=open h=high g=low v=volume
                URL servicePage = new URL("http://quote.yahoo.com/d/quotes.csv?symbols="
                                        +stockSymbol
                                        +"&format=s1d1t1c1ohgv&ext=csv");
                URLConnection conURL = servicePage.openConnection();
                conURL.connect();
                BufferedReader data =
                    new BufferedReader(new InputStreamReader(conURL.getInputStream()));
                
                while ((dataLine = data.readLine()) != null)
                    buf.append(dataLine + "\n");
                    
            }
            catch (MalformedURLException ex)
            {
                System.out.println("Bad URL: " +ex.getMessage());
            }
            return buf.toString();
        }
        
        private void addStock(HttpServletRequest req, HttpSession session)
                throws ClassNotFoundException, IOException, SQLException
        {
            String userID = (String)session.getAttribute("userID");
            String stockSymbol = req.getParameter("stockSymbol");
            String stockDesc = null;
            boolean hasStock = false;
            
            // determine if user already holds the stock
            ArrayList al = (ArrayList)session.getAttribute("stocks");
            for(int i=0; i < al.size(); ++i)
                hasStock = true;
                
            if(hasStock)  // user already holds this stock
                session.setAttribute("forwardTo", "mainForm.jsp"); //send to main scrn
            else
            {       // add stock to user holding
                synchronized (db)
                {
                    stockDesc = db.getStockDesc(stockSymbol);
                }
                if(stockDesc == null) // stock not in DB
                {
                    session.setAttribute("stockSymbol", stockSymbol.toUpperCase());
                    session.setAttribute("forwardTo", "stockDesc.jsp");  // get description
                }
                else
                { 
                    addToUserStocks(userID,stockSymbol);
                    session.setAttribute("stocks", getStockList(userID));
                    session.setAttribute("forwardTo", "mainform.jsp");  // to main scrn
                }
            }
        }  
        private void addStockDesc(HttpServletRequest req, HttpSession session)
                throws ClassNotFoundException, IOException, SQLException
        {
            String userID = (String)session.getAttribute("userID");
            String stockSymbol = (String)session.getAttribute("stockSymbol");
            String stockDesc = req.getParameter("stockDesc");
            
            synchronized (db)
            {
                 db.addStock(stockSymbol,stockDesc);
                 addToUserStocks(userID,stockSymbol);
                 
             }
             session.setAttribute("stocks", getStockList(userID));
             session.setAttribute("forwardTo", "mainform.jsp");
         }    
         private void addToUserStocks(String userId, String stockSymbol)
                        throws SQLException,IOException,ClassNotFoundException
         {
             stockSymbol.trim();  // remove any leading trailing whitespace
             synchronized(db)
        		 db.addUserStocks(userID,stockSymbol.toUpperCase());
        	 } 
         }
         private void delStock(HttpServletRequest req, HttpSession session)
         				throws ClassNotFoundException, IOException, SQLException 
         {
        	 String userID = (String)session.getAttribute("userID");
        	 String stockSymbol = req.getParameter("stockSymbol");
        	 synchronized (db)
        	 {
        		 db.delUserStocks(userID, stockSymbol);
        	 }
        	 session.setAttribute("stocks", getStockList(userId));
        	 session.setAttribute("forwardTo", "mainForm.jsp");
        }
        private void chgPswd(HttpServletRequest req, HttpSession session)
        		throws ClassNotFoundException, IOException, SQLException,
        			PasswordException
        {
        	String pswd1 = req.getParameter("pswd1");
        	String pswd = (String)session.getAttribute("pswd");
        	String userID = (String)session.getAttribute("userID");
        	User user = null;
        	
        	if(session.getAttribute("pswdExpired") != null)
        		session.removeAttribute("pswdExpired");
        	
        	synchronized (db)
        	{
        		user = db.getUser(userID);  // get user object from DB for this ID
        		user.changePassword(pswd, pswd1);
        		db.updUser(user);
        	}
        	session.setAttribute("forwardTo", "mainForm.jsp");
        	
        	if(user.pswdIsExpiring())
        		session.setAttribute("uses", new Integer(user.getPswdUses()));
        	
        	if(user.isAdmin())
        		session.setAttribute("admin", new Boolean(true));
        	
        	session.setAttribute("stocks", getStockList(userID));
        }
        private void addUser(HttpServletRequest req)
        	throws ClassNotFoundException, IOException, SQLException,
        			PasswordException
        {
        	String userID = req.getParameter("userID");
        	String userFirstName = req.getParameter("userFirstName");
        	String userLastName = req.getParameter("userLastName");
        	String newPassword = req.getParameter("pswd1");
        	String autoExpires = req.getParameter("autoExpires");
        	String numUses = req.getParameter("uses");
        	String adminChecked = req.getParameter("isAdmin");
        	
        	boolean isAdmin, autoExp;
        	int uses = 0;
        	
        	if(adminChecked != null && adminChecked.equals("Yes"))
        			isAdmin = true;
        	else
        		isAdmin= false;
        	if(autoExpires.equals("Yes"))
        		autoExp = true;
        	else
        		autoExp = false;
        	
        	if(autoExp && numUses != null)
        		uses = Integer.parseInt(numUses);
        	
        	User user = new User(userId, userFirstName, userLastName,
        				newPassword, autoExp, uses, isAdmin);
        	user.expirePassword(); // cause new user to have to change password
        	
        	synchronized (db)
        	{
        		db.addUser(user);
        	}
        }
        private void delUser(String userID) throws ClassNotFoundException,
        									IOException, SQLException
        {
        	if(userID != null)
        	{
        		synchronized (db)
        		{
        			User user = db.getUser(userID);  // throws exception if user not found
        			db.delUser(user);
        		}
        	}
        }
        private void updUser(HttpServletRequest req)
        		throws ClassNotFoundException, IOException, SQLException,
        				PasswordException
        {
        	// cannot change userID uses left for password
        	String userID = req.getParameter("userID");
        	String userFirstName = req.getParameter("userFirstName");
        	String userLastName = req.getParameter("userLastName");
        	String newPassword = req.getParameter("paswd1");
        	String autoExpires = req.getParameter("autoExpires");
        	String isAdmin = req.getParameter("isAdmin");
        	
        	synchronized (db)
        	{
        		User user = db.getUser(userId);
        		if(newPassword.length() > 0)
        			user.adminChangePassword(newPassword);
        		user.setFirstName(userFirstName);
        		user.setLastName(userLastName);
        		
        		if(isAdmin != null && isAdmin.equals("Yes"))
        			user.setAdmin(true);
        		else
        			user.setAdmin(false);
        		
        		if(autoExpires.equals("Yess"))
        			user.setAutoExpires(true);
        		else
        			user.setAutoExpires(false);
        		
        		db.updUser(user);		
        	}
        }
        private boolean validUserPswd(User user, String password)
        			throws ClassNotFoundException, IOException,
        					SQLException, PasswordException
        {
        	boolean valid = false;
        	
        	if(user != null)
        	{
        		user.validate(password);  // throws PasswordException
        		if(user.pswdAutoExpires())  // if tracking uses
        			synchronized (db)
        			{
        				db.updUser(user);  // update use count in database
        			}	
        				valid = true;
        			}
        		return valid;
        	}
        	private ArrayList getStockList(String userID)
        				throws ClassNotFoundException, IOException, SQLException
        	{
        		ArrayList al = null;
        		synchronized (db)
        		{
        			al = db.listUSerStocks(userID);
        		}
        		return al;
        	}
        	private void listUsers(HttpSession session)
        			throws ClassNotFoundException, IOException, SQLException
            {
        		ArrayList rs;
        		synchronized (db)
        		{
        			rs = db.listUsers();
        		}
        		session.setAttribute("userList", rs);
            }
        	private User getUser(String userID)
        				throws ClassNotFoundException, IOException,
        						SQLException
        	{
        		User user = null;
        		synchronized (db)
        		{
        			user = db.getUser(userID);  // get user object from DB for this ID
        		}
        		return user;
        	}
        	private ArrayList getUserData(String userID)
        				throws ClassNotFoundException, IOException, SQLException
        	{
        		ArrayList<E> userData = new ArrayList();
        		User user = null;
        		
        		synchronized (db)
        		{
        			user = db.getUser(userID);  // get user object from DB for this ID
        		}
        		
        		if(user != null)
        		{
        			userData.add(userID);
        			userData.add(user.getFirstName());
        			userData.add(user.getLastName());
        			if(user.isAdmin())
        				userData.add("Administrator");
        			else
        				userData.add("nonadministrator");
        			
        			if(user.pswdAutoExpires())
        				userData.add("Yes");
        			else
        				userData.add("No");
        			
        			userData.add(String.valueOf(user.getPswdUses()));
        		}
        		
        		return userData;
        	}
        }
    }
        	 
  
          