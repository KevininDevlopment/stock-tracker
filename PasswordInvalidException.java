
/**
 * The PasswordInvalidException class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/07/18
 */
public class PasswordInvalidException extends PasswordException
{
    private static int count;
    
    /**
     * Constructor for objects of class PasswordInvalidException
     */
    public PasswordInvalidException()
    {
        super("Invalid password.");
        ++count;
    }

    public PasswordInvalidException(String pswd)
    {
        super(pswd);
        ++count;
    }
    
    public String usage()
    {
        return new String("This password does not match any\n"+
        "value in the password history.");
    }
    
    public final void resetCount()
    {
        count = 0;
    }
    
    public final int getCount()
    {
        return count;
    }
}
