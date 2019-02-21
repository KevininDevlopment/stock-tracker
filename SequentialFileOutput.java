

/**
 * The SequentialFileOutput class.
 *
 * @author Kevin Amaya, Joy Starks
 * @version 04/24/18
 */

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;


public class SequentialFileOutput extends JFrame implements ActionListener
{
    // Declare output and input stream
    DataOutputStream output;
    
    
    //construct a panel for each row
    JPanel firstRow = new JPanel();
    JPanel secondRow = new JPanel();
    JPanel thirdRow = new JPanel();
    JPanel fourthRow = new JPanel();
    JPanel fifthRow = new JPanel();
    JPanel sixthRow = new JPanel();
    JPanel seventhRow = new JPanel();
    JPanel eigthRow = new JPanel();
    
    //Construct a panel for the fields and buttons
    JPanel fieldPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    
    //Construct labels and text boxes
    JLabel idNumLabel = new JLabel("ID Number:                                 ");
        JTextField idNum = new JTextField(10);
    JLabel acctLabel = new JLabel("Account Balance: ");
        JTextField acctBalance = new JTextField(15);
    JLabel firstName = new JLabel("First Name:                       ");
        JTextField firstNameField = new JTextField(15);
    JLabel lastName = new JLabel("Last Name: ");
        JTextField lastNameField = new JTextField(15);
    JLabel addressLabel = new JLabel("Address: "); 
        JTextField address = new JTextField(35);
    JLabel cityLabel = new JLabel("City:                             "); 
        JTextField city = new JTextField(10);
    JLabel stateLabel = new JLabel("State: "); 
        JTextField state = new JTextField(2);
    JLabel zipLabel = new JLabel("Zip: "); 
        JTextField zip = new JTextField(9);
        
    //Construct button
    JButton submitButton = new JButton("Submit");

    
    public static void main(String[] args)
    {
        //set look and feel of the interface
        try
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,
            "The UIManager could not set the Look and Feel for this application.","Error",
            JOptionPane.INFORMATION_MESSAGE);
        }
        SequentialFileOutput f = new SequentialFileOutput();
        f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        f.setSize(450,300);
        f.setTitle("Bank Account Application");
        f.setResizable(false);
        f.setLocation(200,200);
        f.setVisible(true);
    }
    
    public SequentialFileOutput()
    {
        Container c = getContentPane();
        c.setLayout((new BorderLayout()));
        fieldPanel.setLayout(new GridLayout(8,1));
        FlowLayout rowSetup = new FlowLayout(FlowLayout.LEFT,5,3);
        firstRow.setLayout(rowSetup);
        secondRow.setLayout(rowSetup);
        thirdRow.setLayout(rowSetup);
        fourthRow.setLayout(rowSetup);
        fifthRow.setLayout(rowSetup);
        sixthRow.setLayout(rowSetup);
        seventhRow.setLayout(rowSetup);
        eigthRow.setLayout(rowSetup);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        //Add fields to rows
        firstRow.add(idNumLabel);
        firstRow.add(acctLabel);
        
        secondRow.add(idNum);
        secondRow.add(acctBalance);
        
        thirdRow.add(firstName);
        thirdRow.add(lastName);
        
        fourthRow.add(firstNameField);
        fourthRow.add(lastNameField);
        
        fifthRow.add(addressLabel);
        
        sixthRow.add(address);
        
        seventhRow.add(cityLabel);
        seventhRow.add(stateLabel);
        seventhRow.add(zipLabel);
        
        eigthRow.add(city);
        eigthRow.add(state);
        eigthRow.add(zip);
        
        
        //Add rows to panel
        fieldPanel.add(firstRow);
        fieldPanel.add(secondRow);
        fieldPanel.add(thirdRow);
        fieldPanel.add(fourthRow);
        fieldPanel.add(fifthRow);
        fieldPanel.add(sixthRow);
        fieldPanel.add(seventhRow);
        fieldPanel.add(eigthRow);
        
        //Add button to panel
        buttonPanel.add(submitButton);
        
        //Add panels to frame
        c.add(fieldPanel, BorderLayout.CENTER);
        c.add(buttonPanel, BorderLayout.SOUTH);
        
        //Add functionality to buttons
        submitButton.addActionListener(this);
        
        //Get the current date and open the file
        Date today = new Date();
        SimpleDateFormat myFormat = new SimpleDateFormat("MMddyyyy");
        String filename = "accounts" + myFormat.format(today);
        
        try
        {
            output = new DataOutputStream(new FileOutputStream(filename));
        }
        catch(IOException io)
        {
            JOptionPane.showMessageDialog(null,
            "The program could not create a storagelocation. Please check the disk drive and" + " " +
            "then run the program again. " , "Error", JOptionPane.INFORMATION_MESSAGE);
            
            System.exit(1);
        }
        
        
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                int answer = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit and submit the file?", "File Submission",
                JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION)
                    System.exit(0);
                }
            }
        );
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String arg = e.getActionCommand();
        
        if (checkFields())
        {
            try 
            {
                output.writeUTF(idNum.getText());
                output.writeUTF(acctBalance.getText());
                output.writeUTF(firstNameField.getText());
                output.writeUTF(lastNameField.getText());
                output.writeUTF(address.getText());
                output.writeUTF(city.getText());
                output.writeUTF(state.getText());
                output.writeUTF(zip.getText());
                
                JOptionPane.showMessageDialog(null, "The account information has been saved.",
                "Submission Successfull",JOptionPane.INFORMATION_MESSAGE);
            }
            catch(IOException c)
            {
                System.exit(1);
            }
            clearFields();
        }       
    }
    
    public boolean checkFields()
    {
        if((idNum.getText().compareTo("")<1)        ||
           (acctBalance.getText().compareTo("")<1)        ||
           (firstNameField.getText().compareTo("")<1)        ||
           (lastNameField.getText().compareTo("")<1)        ||
           (address.getText().compareTo("")<1)        ||
           (city.getText().compareTo("")<1)        ||
           (state.getText().compareTo("")<1)        ||
           (zip.getText().compareTo("")<1))        
        {
            JOptionPane.showMessageDialog(null, "You must complete all fields.",
            "Data Entry Error",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        else
        {
            return true;
        }  
    }
    
    public void clearFields()
    {
        //Clear fields and reset the focus
        idNum.setText("");
        acctBalance.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        address.setText("");
        city.setText("");
        state.setText("");
        zip.setText("");
        idNum.requestFocus();
    }
    
    
}
        

