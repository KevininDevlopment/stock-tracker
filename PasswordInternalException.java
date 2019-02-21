
/**
 * The PasswordInternalException class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/07/2018
 */
public class PasswordInternalException extends PasswordException
{
   
    /**
     * Constructor for objects of class PasswordInternalException
     */
    public PasswordInternalException(String msg)
    {
        super(msg);
    }

    public String usage()
    {
        return new String("Internal error in the collection "+
                          "containing the password history. This should never occur.");
    }
}
