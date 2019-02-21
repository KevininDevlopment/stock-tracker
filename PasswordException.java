
/**
 * An abstract class for password exceptions.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/07/2018
 */
public abstract class PasswordException extends Exception
{
    

    /**
     * Constructor for objects of class PasswordException
     */
    public PasswordException()
    {
        super("Password exception");
    }

    public PasswordException(String msg)
    {
        super(msg);
    }
    
    public abstract String usage();
}
