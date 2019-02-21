
/**
 * The PasswordExpiredException class. A class for expired exceptions.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/07/2018
 */
public class PasswordExpiredException extends PasswordException
{
    /**
     * Constructor for objects of class PasswordExpiredException
     */
    public PasswordExpiredException()
    {
       super("Password has expired");
    }

    public PasswordExpiredException(String msg)
    {
        super(msg);
    }
    
    public String usage()
    {
        return new String("This password is set to expire, \n"+
                          "and its number of remaining uses has reached zero");
    }
                        
    
}
