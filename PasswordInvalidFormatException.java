
/**
 * The PasswordInvalidFormatException class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/07/18
 */
public class PasswordInvalidFormatException extends PasswordInvalidException
{
    
    /**
     * Constructor for objects of class PasswordInvalidFormatException
     */
    public PasswordInvalidFormatException()
    {
        super("Invalid password format.");
    }
    
    public PasswordInvalidFormatException(String msg)
    {
        super(msg);
    }
    
    public String usage()
    {
        return new String("This password is not formatted properly.");
    }
}
