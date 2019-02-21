
/**
 * The PasswordSizeException class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/07/18
 */
public class PasswordSizeException extends PasswordInvalidFormatException
{
    //instance variables
    private int pswdSize;
    private int minSize;
    private int maxSize;
    
    /**
     * Constructor for objects of class PasswordSizeException
     */
    public PasswordSizeException(String msg, int pswdSize, int minSize, int maxSize)
    {
        super(msg);
        this.pswdSize = pswdSize;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public int getPswdSize()
    {
        return pswdSize;
    }
    
    public int getMinSize()
    {
        return minSize;
    }
    
    public int getMaxSize()
    {
        return maxSize;
    }
}
