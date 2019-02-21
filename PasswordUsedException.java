
/**
 * The PassWordUsedEception class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/07/2018
 */
public class PasswordUsedException extends PasswordException
{
    
    /**
     * Constructor for objects of class PasswordUsedException
     */
    public PasswordUsedException()
    {
        super("Password recently used");
    }

    public PasswordUsedException(String msg)
    {
        super(msg);
        
    }
    
    public String usage()
    {
        return new String("This password cannot be reused at this time.");
    }
}
