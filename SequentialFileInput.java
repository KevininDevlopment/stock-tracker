
/**
 * The SequentialFileInput class.
 *
 * @author Kevin Amaya
 * @version 04/25/2018
 */

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;


public class SequentialFileInput extends JFrame implements ActionListener
{
    DataInputStream input;
    
    JPanel fieldPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    
    JLabel idNumLabel = new JLabel("ID Number");
    JLabel idNum = new JLabel("                                     ");
    JLabel firstNameLabel = new JLabel("First Name");
    JLabel firstNameField = new JLabel("                                     ");
    JLabel lastNameLabel = new JLabel("Last Name");
    JLabel lastNameField = new JLabel("                                     ");  
    JLabel acctBalanceLabel = new JLabel("Account Balance");
    JLabel acctBalance = new JLabel("                                       ");
    JLabel addressLabel = new JLabel("Address");
    JLabel address = new JLabel("                                     ");
    JLabel cityLabel = new JLabel("City");
    JLabel city = new JLabel("                                     ");
    JLabel stateLabel = new JLabel("State");
    JLabel state = new JLabel("                                     ");
    JLabel zipLabel = new JLabel("Zip");
    JLabel zip = new JLabel("                                     ");
    JButton next = new JButton("Next->");
 

    
    public SequentialFileInput()
{
    setForeground(Color.white);
    next.setForeground(Color.black);
    setLayout(new BorderLayout());
    fieldPanel.setLayout(new GridLayout(4,2));
    buttonPanel.setLayout(new FlowLayout());
    fieldPanel.add(idNumLabel);
    fieldPanel.add(idNum);
    fieldPanel.add(acctBalanceLabel);
    fieldPanel.add(acctBalance);
    fieldPanel.add(firstNameLabel);
    fieldPanel.add(firstNameField);
    fieldPanel.add(lastNameLabel);
    fieldPanel.add(lastNameField);
    fieldPanel.add(addressLabel);
    fieldPanel.add(address);
    fieldPanel.add(cityLabel);
    fieldPanel.add(city);
    fieldPanel.add(stateLabel);
    fieldPanel.add(state);
    fieldPanel.add(zipLabel);
    fieldPanel.add(zip);
    buttonPanel.add(next);
    add(fieldPanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);
    next.addActionListener(this);
    
        try
        {
            //Open file
            input = new DataInputStream(new
            FileInputStream("accounts04262018"));
        }
        catch(IOException ex)
        {   
            System.exit(1);
        }
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        
        String arg = e.getActionCommand();
        if(arg == "Next->")
        {
        try
        {
        //Read the data
        idNum.setText(input.readUTF());
        acctBalance.setText(input.readUTF());
        firstNameField.setText(input.readUTF());
        lastNameField.setText(input.readUTF());
        address.setText(input.readUTF());
        city.setText(input.readUTF());
        state.setText(input.readUTF());
        zip.setText(input.readUTF());
        
        }
        catch(IOException io)
        {
            
            //Print end of file message
	    JOptionPane.showMessageDialog(null, "That was the last bank account record");

	    }

	 }
            
     }

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
        SequentialFileInput f = new SequentialFileInput();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(450,300);
        f.setTitle("Bank Account Records");
        f.setResizable(false);
        f.setLocation(200,200);
        f.setVisible(true);
    }
 
}
