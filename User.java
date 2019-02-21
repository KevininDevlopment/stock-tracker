
/**
 * A user class for the password authentication system.
 *
 * @author Kevin Amaya, Michael Mick
 * @date 04/06/18
 */
public class User
{
    // instance variables
    private String name;
    private Password pswd;

    /**
     * Constructor for objects of class User
     */
    public User(String aName, String password) throws PasswordException
    {
        // initialise instance variables
        name = new String(aName);
        pswd = new Password(password);
    }
    
    public User(String aName, String password, int pswdUses) throws PasswordException
    {
        name = new String(aName);
        pswd = new Password(password,pswdUses);
        
    }
    
    public User(String aName, String password, boolean autoExpirePswd) throws
    PasswordException
    {
        name = new String(aName);
        pswd = new Password(password,autoExpirePswd);
    }
    
    public String getName()
    {
        return new String(name);
    }
    
    public boolean pswdAutoExpires()
    {
        return pswd.getAutoExpire();
    }
    
    public boolean pswdIsExpiring()
    {
        return pswd.isExpiring();
    }
    
    public int getPswdUses()
    {
        return pswd.getRemainingUses();
    }
    
    public void validate(String password) throws PasswordException
    {
        pswd.validate(password);
    }
    
    public void changePassword(String oldPassword, String newPassword) throws PasswordException
    {
        try
        {
            pswd.validate(oldPassword);
        }
        catch(PasswordExpiredException ex)
        {}
        pswd.set(newPassword);
    }
}
