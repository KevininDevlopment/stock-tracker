
/**
 * The StockFrame class.
 *
 * @author Kevin Amaya, Michael Mick
 * @version (04/08/2018
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.border.TitledBorder;


class StockFrame extends JFrame implements ActionListener, WindowListener
{
        // instance variables 
        String stockSymbol;
        String buyingStock;
        String sellingStock;
        String[] columns;
        Object[][] stockInfo;
        
        JTextField jtfStockSymbol, buyStock, sellStock;
        JButton jbtAddStock, jbtLogout, buyStockButton, sellStockButton, buyStockLogout, sellStockLogout;
        JComboBox jcbStockList, stockBought, stockSold;
        Activator caller;
       
    
    
    /**
     * Constructor for objects of class StockFrame
     */
    public StockFrame(User user, Activator callerObj)
    {
        super("Add Stock Symbols"); // call super (JFrame) constructor
        int width = 500;
        int height = 400;
        caller = callerObj; // save reference caller object
        
        // Stock Array with information
        String[] columns = {"Code", "Name", "High", "Low",
                            "Close", "Volume", "Change", "Change %"};
        Object[][] stockInfo = {
            {"ANDV", "Andeavor", 117.8, 115.4, 117.8, 1150739, 1.3,1.14},
            {"AXR", "Amrep Corp", 7390, 7260, 7390, 3300, 0.040, 0.54},
            {"M", "Macy's Inc", 30.27, 29.40, 29.96, 10639202, 0.55,1.83},
            {"V", "Visa Inc", 124.8, 123.3, 124.2, 7469221, 0.2, 0.19},
            {"F", "Ford Motor Company", 11.03, 10.78, 10.82, 44452188, -0.14, 1.28}
        };

        
        
        // Menu and options
        JMenuBar menubar = new JMenuBar();
        JMenu menu = new JMenu("Stock Market Application");
        JMenuItem stockSymbols = new JMenuItem("Stock Symbols");     
        JMenuItem currentMenu = new JMenuItem("Current Stocks");
        JMenuItem marketMenu = new JMenuItem("Stock Market");
        JMenuItem exitMenu = new JMenuItem("Exit Application");
        
        // Menu panels
        JPanel currentPanel = new JPanel();
        JPanel marketPanel = new JPanel();
        
        // currentPanel1
        JLabel currentLabel1 = new JLabel("Sell Stocks:");
        sellStock = new JTextField(4);
        sellStockButton = new JButton("Sell");
        
        JPanel currentPanel1 = new JPanel();
        currentPanel1.setLayout(new GridLayout(1,3));
        currentPanel1.add(currentLabel1);
        currentPanel1.add(sellStock);
        currentPanel1.add(sellStockButton);
        currentPanel1.setBorder(new TitledBorder("Sell Stocks On The Market"));
        
        // currentPanel2
        JLabel currentLabel2 = new JLabel("Click Arrow To View List");
        stockSold = new JComboBox();
        
        JPanel currentPanel2 = new JPanel();
        currentPanel2.setLayout(new GridLayout(1,2));
        currentPanel2.add(currentLabel2);
        currentPanel2.add(stockSold);
        currentPanel2.setBorder(new TitledBorder("View Stocks Sold"));
        
        // currentPanel3
        JLabel currentLabel3 = new JLabel("User Name: "+user.getName());
        sellStockLogout = new JButton("Log Out");
        
        JPanel currentPanel3 = new JPanel();
        currentPanel3.setLayout(new GridLayout(1,2));
        currentPanel3.add(currentLabel3);
        currentPanel3.add(sellStockLogout);
        currentPanel3.setBorder(new TitledBorder("Current User"));
        
        // currentPanel's last panels
        JPanel currentPanel4 = new JPanel();
        currentPanel4.setLayout(new GridLayout(3,1,10,5)); // rows, cols, hgap, vgap
        currentPanel4.add(currentPanel1);
        currentPanel4.add(currentPanel2);
        currentPanel4.add(currentPanel3);
        
        JPanel currentPanel5 = new JPanel(new BorderLayout(10,10));
        currentPanel5.add(currentPanel4, BorderLayout.WEST);
        JPanel currentPanel6 = new JPanel(new BorderLayout(10,10));
        currentPanel6.add(currentPanel5, BorderLayout.EAST);
       
        
        setContentPane(currentPanel6); 
        
        // end
        
        // marketPanel0 with the stock table arrays
        JPanel marketPanel0 = new JPanel();
        JTable stockTable = new JTable(stockInfo, columns);
        JScrollPane scrollPane = new JScrollPane(stockTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                     JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JLabel stockLabelHead = new JLabel("Stock Quotes");
        stockTable.setFillsViewportHeight(true);
        
        marketPanel0.setLayout(new GridLayout(0,1));
        marketPanel0.add(stockTable);
       
        // marketPanel1
        JLabel marketLabel1 = new JLabel("Buy Stocks:");
        buyStock = new JTextField(4);
        buyStockButton = new JButton("Buy");
        
        JPanel marketPanel1 = new JPanel();
        marketPanel1.setLayout(new GridLayout(1,3));
        marketPanel1.add(marketLabel1);
        marketPanel1.add(buyStock);
        marketPanel1.add(buyStockButton);
        marketPanel1.setBorder(new TitledBorder("Purchase Stocks On The Market"));
        
        // marketPanel2
        JLabel marketLabel2 = new JLabel("Click Arrow To View List");
        stockBought = new JComboBox();
        
        JPanel marketPanel2 = new JPanel();
        marketPanel2.setLayout(new GridLayout(1,2));
        marketPanel2.add(marketLabel2);
        marketPanel2.add(stockBought);
        marketPanel2.setBorder(new TitledBorder("View Purchases"));
        
        // marketPanel3
        JLabel marketLabel3 = new JLabel("User Name: "+user.getName());
        buyStockLogout = new JButton("Log Out");
        
        JPanel marketPanel3 = new JPanel();
        marketPanel3.setLayout(new GridLayout(1,2));
        marketPanel3.add(marketLabel3);
        marketPanel3.add(buyStockLogout);
        marketPanel3.setBorder(new TitledBorder("Current User"));
        
        // marketpanel's last panels
        JPanel marketPanel4 = new JPanel();
        marketPanel4.setLayout(new GridLayout(4,1,10,5)); // rows, cols, hgap, vgap
        marketPanel4.add(marketPanel0);
        marketPanel4.add(marketPanel1);
        marketPanel4.add(marketPanel2);
        marketPanel4.add(marketPanel3);
        
        JPanel marketPanel5 = new JPanel(new BorderLayout(10,10));
        marketPanel5.add(marketPanel4, BorderLayout.WEST);
        JPanel marketPanel6 = new JPanel(new BorderLayout(10,10));
        marketPanel6.add(marketPanel5, BorderLayout.EAST);
        
        
        setContentPane(marketPanel6); 
        
        // end
        
  
        //Define components for panel 1
        JLabel label1 = new JLabel("Symbol:");
        jtfStockSymbol = new JTextField(4);
        jbtAddStock = new JButton("Add Symbol");
        
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1,3));
        p1.add(label1);
        p1.add(jtfStockSymbol);
        p1.add(jbtAddStock);
        p1.setBorder(new TitledBorder("Add Stock Symbols"));
        
        //Define components for panel 2
        JLabel label2 = new JLabel("Click Arrow To View List");
        jcbStockList = new JComboBox();
        
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(1,2));
        p2.add(label2);
        p2.add(jcbStockList);
        p2.setBorder(new TitledBorder("View Stock Symbols"));
        
        //Define components for panel 3
        JLabel label3 = new JLabel("User Name: "+user.getName());
        jbtLogout = new JButton("Log Out");
        
        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(1,2));
        p3.add(label3);
        p3.add(jbtLogout);
        p3.setBorder(new TitledBorder("Current User"));
        
        //Use nested panels for positioning
        JPanel p4 = new JPanel();
        p4.setLayout(new GridLayout(3,1,10,5)); // rows, cols, hgap, vgap
        p4.add(p1);
        p4.add(p2);
        p4.add(p3);
        
        JPanel p5 = new JPanel(new BorderLayout(10,10));
        p5.add(p4, BorderLayout.WEST);
        JPanel p6 = new JPanel(new BorderLayout(10,10));
        p6.add(p5, BorderLayout.EAST);
       
        
        setContentPane(p6); 
        

        //Register Listeners
        addWindowListener(this);
        buyStockButton.addActionListener(this);
        stockBought.addActionListener(this);
        sellStockButton.addActionListener(this);
        stockSold.addActionListener(this);
        jbtAddStock.addActionListener(this);
        jbtLogout.addActionListener(this);
        jcbStockList.addActionListener(this);
        buyStockLogout.addActionListener(this);
        sellStockLogout.addActionListener(this);
        
        
        //Prepare for display
        pack();
        if(width < getWidth())
            width = getWidth();
        if(height < getHeight())
            height = getHeight();
        centerOnScreen(width,height);
        jtfStockSymbol.setText("");
        jtfStockSymbol.requestFocus(); 
        buyStock.setText("");
        buyStock.requestFocus();
        sellStock.setText("");
        sellStock.requestFocus();

        // Menu configuration
        menubar.add(menu);
        menu.add(stockSymbols);
        menu.add(currentMenu);
        menu.add(marketMenu);
        menu.add(exitMenu);
        setJMenuBar(menubar);
        stockSymbols.addActionListener(new MyMenu(p4));
        currentMenu.addActionListener(new MyMenu(currentPanel4));
        marketMenu.addActionListener(new MyMenu(marketPanel4));
        exitMenu.addActionListener((ActionEvent event) -> {
        System.exit(0);
     });
         
       
    }    

    public void centerOnScreen(int width, int height)
    {
        int top, left, x, y;
        
        // Get the screen dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Determine the location for the top left corner of the frame
        x = (screenSize.width - width)/2;
        y = (screenSize.height - height)/2;
        left = (x < 0) ? 0 : x;
        top = (y < 0) ? 0 : y;
        
        this.setBounds(left, top, width, height);   
    } 
    
    public boolean stockInList(String stock)
    {
        boolean inList = false;
        int numItems;
 
        numItems = jcbStockList.getItemCount();
        stock.trim(); // remove any leading, trailing white space

        
    if(numItems > 0) //at least one entry in in list
    {
        for(int i=0; i < numItems && !inList; ++i)
        {
            if(stock.equals((String)jcbStockList.getItemAt(i)))
                inList = true;
        }
    }
    return inList; 
             
} 
    
    public boolean stocksBought(String bought)
    {
          boolean boughtList = false;
          int boughtItems;
          
          boughtItems = stockBought.getItemCount();
          bought.trim();
        
        if(boughtItems > 0)
            {
                for(int i=0; i < boughtItems && !boughtList; ++i)
                {
                     if(bought.equals((String)stockBought.getItemAt(i)))
                boughtList = true;
            }    
                
    }
    return boughtList;
}

    public boolean stocksSold(String sold)
    {
        boolean soldList = false;
        int soldItems;
        
        soldItems = stockSold.getItemCount();
        sold.trim();
        
         if(soldItems > 0)
            {
                for(int i=0; i < soldItems && !soldList; ++i)
                {
                     if(sold.equals((String)stockSold.getItemAt(i)))
                soldList = true;
            }    
                
    }
    return soldList;
}
        
        
    

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == jbtLogout)
        {
            logoutUser();
        }
        else
        if(e.getSource() == jbtAddStock)
        {
            stockSymbol = jtfStockSymbol.getText();
            if(stockSymbol.equals(""))
            JOptionPane.showMessageDialog(this, "Please enter a stock symbol to add.");
            else
            {
                if(!stockInList(stockSymbol))
                jcbStockList.addItem(stockSymbol);
            }
            jtfStockSymbol.setText("");
            jtfStockSymbol.requestFocus();
        }
        else
        if(e.getSource() == jcbStockList)
        {
            stockSymbol = (String)jcbStockList.getSelectedItem();
            if(stockSymbol == null)
                JOptionPane.showMessageDialog(this, "Please add a stock to the list.");
            else
            jtfStockSymbol.setText(stockSymbol);
        }
       
        
        if(e.getSource() == buyStockLogout)
        {
            logoutUser();
        }
        else
        if(e.getSource() == buyStockButton)
        {
            buyingStock = buyStock.getText();
            if(buyingStock.equals(""))
            JOptionPane.showMessageDialog(this, "Please enter a stock to purchase.");
            else 
            {
                if(!stocksBought(buyingStock))
                stockBought.addItem(buyingStock);
            }
            buyStock.setText("");
            buyStock.requestFocus();
        }
        else
        if(e.getSource() == stockBought)
        {
            buyingStock = (String)stockBought.getSelectedItem();
            if(buyingStock == null)
                JOptionPane.showMessageDialog(this, "Please add a stock to the list.");
            else
            buyStock.setText(buyingStock);
        }
       
       
       if(e.getSource() == sellStockLogout)
        {
            logoutUser();
        }
        else
        if(e.getSource() == sellStockButton)
        {
            sellingStock = sellStock.getText();
            if(sellingStock.equals(""))
            JOptionPane.showMessageDialog(this, "Please enter a stock to purchase.");
            else 
            {
                if(!stocksSold(sellingStock))
                stockSold.addItem(sellingStock);
            }
            sellStock.setText("");
            sellStock.requestFocus();
        }
        else
        if(e.getSource() == stockSold)
        {
            sellingStock = (String)stockSold.getSelectedItem();
            if(sellingStock == null)
                JOptionPane.showMessageDialog(this, "Please add a stock to the list.");
            else
            sellStock.setText(sellingStock);
        }
       
    }


    
    private void logoutUser()
    {
        this.setVisible(false);
        dispose();
        caller.activate();    // call activate method of caller object
    }
    
    //Handler for window opened event
    public void windowOpened(WindowEvent event)
    {
        jtfStockSymbol.setText("");
        jtfStockSymbol.requestFocus();
        buyStock.setText("");
        buyStock.requestFocus();
        sellStock.setText("");
        sellStock.requestFocus();
    }
    
    // Handler for window closing event
    public void windowClosing(WindowEvent event)
    {
        logoutUser();
    }
    
    //Handler for window closed event
    public void windowClosed(WindowEvent e)
    {
    }
    
    // Handler for window iconified event
    public void windowIconified(WindowEvent event)
    {
    }
    
    // Handler for window deiconified event
    public void windowDeiconified(WindowEvent event)
    {
    }
    
    // Handler for window activated event
    public void windowActivated(WindowEvent event)
    {
    }
    
    // Handler for window deactivated event
    public void windowDeactivated(WindowEvent event)
    {
    }

    // Listener for the menu options
    private class MyMenu implements ActionListener {

    private JPanel panel;
    private MyMenu(JPanel panel2) {
        this.panel = panel2;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switchPanel(panel);

    }

}

    // switchPanel method
    private void switchPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        update(getGraphics());
    }
  }

   