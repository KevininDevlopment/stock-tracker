
/**
 * A reusable password class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version 04/06/18
 */

import java.util.*;

public class Password
{
    final static int MIN_SIZE = 8;
    final static int MAX_SIZE = 20;
          static int limitExpired = 3;
          static int maxHistory = 4;
          
    private int maxUses = 120;
    private int remainingUses = maxUses;
    private boolean autoExpire = true;
    private boolean expired = false;
    
    private ArrayList pswdHistory;

    /**
     * Constructor for objects of class Password
     */
    public Password(String newPassword) throws PasswordException
    {
        pswdHistory = new ArrayList(maxHistory);
        set(newPassword);
       
    }

    
    public Password(String newPassword, int numMaxUses) throws PasswordException
    {
        pswdHistory = new ArrayList(maxHistory);
        maxUses = numMaxUses;
        remainingUses = numMaxUses;
        set(newPassword);
    }
    
    public Password(String newPassword, boolean pswdAutoExpire) throws PasswordException
    {
        pswdHistory = new ArrayList(maxHistory);
        autoExpire = pswdAutoExpire;
        set(newPassword);
    }
    
    public Password(String newPassword, int numMaxUses, boolean pswdAutoExpire) throws 
    PasswordException
    {
        pswdHistory = new ArrayList(maxHistory);
        maxUses = numMaxUses;
        autoExpire = pswdAutoExpire;
        set(newPassword);
    }
    
    public int getMaxHistory() {
        return maxHistory;
    }
    
    public void setMaxHistory(int newMaxHistory) {
        int overage = 0;
        if(newMaxHistory >= 1 && newMaxHistory <= 10) {
            maxHistory = newMaxHistory;
            overage = getHistorySize() - maxHistory;
            if(overage > 0) {
                do {
                    pswdHistory.remove(0);
                    overage--;
                } while(overage > 0);
                pswdHistory.trimToSize();
            }
        }
        
    }
    
    public boolean getAutoExpire() {
        return autoExpire;
    }
    
    public void setAutoExpire(boolean autoExpire) {
        this.autoExpire = autoExpire;
        if(autoExpire)
        remainingUses = maxUses;
    }
    
    public boolean isExpired() {
        return expired;
    }
    
    public void setExpired(boolean newExpired) {
        expired = newExpired;
    }
    
    public boolean isExpiring() {
        boolean expiring = false;
        if(autoExpire && remainingUses <= limitExpired)
        expiring = true;
        
        return expiring;
    }
    
    public int getlimitExpired() {
        return limitExpired;
    }
    
    public void setlimitExpired(int newLimitExpired)
    {
        if(newLimitExpired >= 2 && newLimitExpired <= 20)
            limitExpired = newLimitExpired;
    }
    
    public int getRemainingUses() {
        return remainingUses;
    }
    
    public int getHistorySize() {
        return pswdHistory.size();
    }
    
    public void set(String pswd) throws PasswordException {
        String encryptPswd;
        boolean pswdAdded = true;
        
        pswd = pswd.trim();
        verifyFormat(pswd);
        encryptPswd = encrypt(pswd);
        
        if(!pswdHistory.contains(encryptPswd)) {
            if(pswdHistory.size() == maxHistory)
            pswdHistory.remove(0);
            
            pswdAdded = pswdHistory.add(encryptPswd);
            
            if(!pswdAdded)
            throw new PasswordInternalException("That password has not been accepted");
            
            if(expired)
                expired = false;
                
            if(autoExpire)
                remainingUses = maxUses;
        }
        else
            throw new PasswordUsedException("That password has been used recently");
        }
        
        public void validate(String pswd) throws PasswordException
        {
            String encryptPswd;
            String currentPswd;
            int currentPswdIndex;
            
            verifyFormat(pswd);
            encryptPswd = encrypt(pswd);
            
            if(!pswdHistory.isEmpty())
            {
                currentPswdIndex = pswdHistory.size()-1;
                currentPswd = (String)pswdHistory.get(currentPswdIndex);
                
                if(!encryptPswd.equals(currentPswd))
                    throw new PasswordInvalidException("That password is invalid");
                    
                if(expired)
                    throw new PasswordExpiredException("That password has expired, please change it");
                    
                if(autoExpire)
                {
                    --remainingUses;
                    if(remainingUses <= 0)
                    expired = true;
                }
            }
            else
                throw new PasswordInvalidException("There is no password on file");
                
            new PasswordInvalidException().resetCount(); //reset invalid exception count
            }
            
            //Verify password format
            private void verifyFormat(String pswd) throws PasswordException
            {
                boolean numFound = false;
                
                if(pswd.length() == 0)
                    throw new PasswordInvalidFormatException("Required password missing!");
                    
                if(pswd.length() < MIN_SIZE)
                    throw new PasswordSizeException("Password minimum size",pswd.length(),
                    MIN_SIZE,MAX_SIZE);
                    
                if(pswd.length() > MAX_SIZE)
                    throw new PasswordSizeException("Password is greater than",pswd.length(),
                    MIN_SIZE,MAX_SIZE);                
                    
                    //check to make sure numbers are added in password
                for(int i=0; i < pswd.length() && !numFound; ++i)
                    if(Character.isDigit(pswd.charAt(i)))
                    numFound = true;
                    
                if(!numFound)
                    throw new PasswordInvalidFormatException("Rejected. Passwords must contain at least one number");
                }
            
                
                
                //Encrypts the password
                private String encrypt(String pswd) {
                    StringBuffer encryptPswd;
                    int pswdSize = 0;
                    int midpoint = 0;
                    int hashCode = 0;
                    
                    //swap the first and last half of password
                    pswdSize = pswd.length();
                    midpoint = pswdSize/2;
                    encryptPswd = new StringBuffer(pswd.substring(midpoint) //get last half of paswd
                    + pswd.substring(0, midpoint));                         //and concatenate first half                                 
                                                                                                
                    encryptPswd.reverse();  //reverse the order of characters in password
                    
                    for(int i=0; i < pswdSize; ++i)
                        encryptPswd.setCharAt(i, (char)(encryptPswd.charAt(i) & pswd.charAt(i)) );
                        
                    hashCode = pswd.hashCode();  //hash code for original password
                    encryptPswd.append(hashCode);
                    
                    return encryptPswd.toString();
                }

}
