package GUI;
import team_001.*;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;


public class MainGUI {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String bookFilePath = "src\\team_001\\Inventory.txt";
    private String csvFilePath = "src\\team_001\\UserDatabase.txt"; // Update with your CSV file path
    private String currUser;
    private LibraryFacade libraryfacade = new LibraryFacade(bookFilePath);
    private JComboBox<String> itemComboBox; // Declare JComboBox
    private ArrayList<String> currUserItems = new ArrayList<String>();
    ArrayList<Client> clientList = new ArrayList<>();
    private Client client;
    String type;
    private boolean bookDueSoon = false;
    static int bookOwed =0;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainGUI window = new MainGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainGUI() {
    	
        initialize();
        
        
        
    }
    
    


	private void initialize() {
        frame = new JFrame();
        frame.setBounds(200, 200, 550, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Welcome to our Project, group 001");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(40, 89, 342, 40);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(40, 140, 80, 14);
        frame.getContentPane().add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(130, 137, 150, 20);
        frame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40, 170, 80, 14);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 167, 150, 20);
        frame.getContentPane().add(passwordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(100, 200, 89, 23);
        frame.getContentPane().add(btnLogin);

        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(200, 200, 89, 23);
        frame.getContentPane().add(btnRegister);
        ArrayList<Client> list = new  ArrayList<>();
//        System.out.println("Here 1");
        UserManagement usermangement = new UserManagement(csvFilePath);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String name = currUser;
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String type = usermangement.getType(username);
                if (usermangement.readUsers(username, password)) {
                	currUser = username;
                	if(type.equals("Student")) {
                		System.out.println("inside student if");
              		  client = new Student(name, username,password, new ArrayList<>());
              	  } else if(type.equals("Faculty")) {
              		System.out.println("inside faculty if");
              		  client = new Faculty(name, username,password);
              	  } else if(type.equals("Visitor")) {
              		  client = new visitor(name, username,password);
              	  } else {
              		  client = new NonFacultyStaff(name, username,password);
              	  }
                	libraryfacade.populizer(currUser, currUserItems);
                    openNewScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               openRegistrationPage(usermangement);
               list.add(clientList.get(0)); 
            }
        });
        
      
    
    }

    private boolean checkCredentials(String username, String password) {
        // Implement your login logic here
        return false; // Placeholder, always return false for now
    }

    private void openRegistrationPage(UserManagement m) {
        JFrame registerFrame = new JFrame("Registration");
        registerFrame.setBounds(100, 100, 450, 300);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.getContentPane().setLayout(null);

        JLabel lblRegister = new JLabel("Register");
        lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblRegister.setBounds(180, 20, 100, 30);
        registerFrame.getContentPane().add(lblRegister);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(40, 60, 80, 14);
        registerFrame.getContentPane().add(lblName);

        JTextField nameField = new JTextField();
        nameField.setBounds(130, 57, 250, 20);
        registerFrame.getContentPane().add(nameField);
        nameField.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(40, 90, 80, 14);
        registerFrame.getContentPane().add(lblEmail);

        JTextField emailField = new JTextField();
        emailField.setBounds(130, 87, 250, 20);
        registerFrame.getContentPane().add(emailField);
        emailField.setColumns(10);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40, 120, 80, 14);
        registerFrame.getContentPane().add(lblPassword);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(130, 117, 250, 20);
        registerFrame.getContentPane().add(passwordField);

        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(40, 150, 80, 14);
        registerFrame.getContentPane().add(lblType);

        JComboBox<String> typeComboBox = new JComboBox<String>();
        typeComboBox.setBounds(130, 147, 250, 20);
        typeComboBox.addItem("Student");
        typeComboBox.addItem("Faculty");
        typeComboBox.addItem("Visitor");
        typeComboBox.addItem("Non-Faculty Staff");
        registerFrame.getContentPane().add(typeComboBox);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(180, 200, 89, 23);
        registerFrame.getContentPane().add(btnSubmit);
        
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve values from fields
                String name = nameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                type = (String) typeComboBox.getSelectedItem();

                // Perform registration
                Client c = m.writeUser(name, password, email, type);
                clientList.add(c);
//                System.out.println("Outside if " + c == null);
                if (c != null) {
                    JOptionPane.showMessageDialog(registerFrame, "Registration successful", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Inside if " +type);
                    registerFrame.dispose(); // Close registration frame
                } else {
                    JOptionPane.showMessageDialog(registerFrame, "Registration failed", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerFrame.setVisible(true);
         
    }


    

    private void openNewScreen() {
    	
        frame.dispose(); // Close current frame

        JFrame newFrame = new JFrame();
        newFrame.setBounds(400, 400, 600, 600); // Adjust frame size as needed
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.getContentPane().setLayout(null);
        
        DefaultListModel<String> listModel = new DefaultListModel<>(); // Create a list model
        LocalDate today = LocalDate.now();
        
        
        
        
//       For selecting from Drop down menu
        JLabel lblWelcome = new JLabel("Select an Item:");
        lblWelcome.setBounds(40, 30, 150, 14);
        newFrame.getContentPane().add(lblWelcome);
        
        JButton btnSelect = new JButton("Rent/Subscribe");
        btnSelect.setBounds(250, 50, 89, 23);
        newFrame.getContentPane().add(btnSelect);
        
        itemComboBox = new JComboBox<>(); // ItemComboBox is the drop down menu
        itemComboBox.setBounds(180, 27, 250, 20); // Adjust combo box size as needed
        newFrame.getContentPane().add(itemComboBox);
        for(Item item: libraryfacade.getInventory().values()) {
//        	This is adding values to the drop down menu
    		itemComboBox.addItem(item.getID() + " " +item.getTitle()); 
    	}
        
        btnSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) itemComboBox.getSelectedItem(); // Get selected item
                int id = Integer.parseInt(selectedItem.split(" ")[0]);
                if (selectedItem != null) {
                    // You can store the selected item in a variable or perform any other action here
                	
                	if(bookOwed <= 3)
                	{if (currUserItems.size() <10){
                		 System.out.println(selectedItem);
                         
                         String bookWithDate = selectedItem + ":" + today.toString();
                         if(client.borrowItem(libraryfacade.getInventory().get(id))) {
                         currUserItems.add(bookWithDate);
                         listModel.addElement(selectedItem); // Add to list model
                         JOptionPane.showMessageDialog(newFrame, currUser + " selected: " + selectedItem.split(" ")[1], "Selected Item",
                                 JOptionPane.INFORMATION_MESSAGE);                   
                         
                         libraryfacade.bookKeeping(currUser, currUserItems);}
                         else {
                        	 JOptionPane.showMessageDialog(newFrame, "No more books", "return some",
                                     JOptionPane.ERROR_MESSAGE);
                         }
       
                }else{
                	JOptionPane.showMessageDialog(newFrame, "Renting Limit Reached", "CAN ONLY RENT 10 AT A TIME. RETURN TO PROCEEd",
                        JOptionPane.ERROR_MESSAGE);
                	} }
                	else {
                		JOptionPane.showMessageDialog(newFrame, "You owe too many books", "Return some to continue",
                                JOptionPane.ERROR_MESSAGE);
                		
                	}
                }else {
                
                	
                    JOptionPane.showMessageDialog(newFrame, "Please select an item", "No Item Selected",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        
        
        
        
        
//        for search functionality
        JLabel lblSearch = new JLabel("Search for an Item:");
        lblSearch.setBounds(40, 80, 150, 14);
        newFrame.getContentPane().add(lblSearch);
        
        JTextField searchField = new JTextField();
        searchField.setBounds(180, 80, 250, 20);
        newFrame.getContentPane().add(searchField);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(450, 80, 80, 20);
        newFrame.getContentPane().add(btnSearch);
        
//        Adding books to an array list to search from
        ArrayList<String> bookList = new ArrayList<String>();
        for(Item item: libraryfacade.getInventory().values()) {
    		bookList.add(item.getID() + " " +item.getTitle());
    	}
        
//        This is the box right below the search to show the result
        DefaultListModel<String> searchListModel = new DefaultListModel<>(); // Create a list model for search results
        JList<String> searchList = new JList<>(searchListModel); // Create a JList with the list model for search results
        JScrollPane searchScrollPane = new JScrollPane(searchList); // Add the JList to a scroll pane
        searchScrollPane.setBounds(40, 120, 500, 50); // Adjust scroll pane size as needed
        newFrame.getContentPane().add(searchScrollPane);
        
        JButton btnSelectRent = new JButton("Rent/Subscribe(search)");
        btnSelectRent.setBounds(250, 180, 150, 23);
        newFrame.getContentPane().add(btnSelectRent);
        
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText().toLowerCase();
                searchListModel.clear(); // Clear previous search results
                for (String item : bookList) {
                    if (item.toLowerCase().contains(query)) {
                        searchListModel.addElement(item); // Add matching items to the search list model
                    }
                }
            }
        });
        
        btnSelectRent.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                // Add your return action here
                // This action will be performed when the "Return" button is clicked
                // For example, you can remove the selected item from currUserItems and update the list model
                String selectedItem = searchList.getSelectedValue();               
                if (selectedItem != null) {
                	int id = Integer.parseInt(selectedItem.split(" ")[0]);
                    if(bookOwed <= 3)
                	{if (currUserItems.size() <10){
                		 System.out.println(selectedItem);
                         
                		 
                         String bookWithDate = selectedItem + ":" + today.toString();
//                        the client.borrowItem simultaneously checks if we have enough books in the inventory to rent
//                        and updates it accordingly. which means it substracts one from the book we have
//                         and if we have zero it'll return false and we'll get
                         if(client.borrowItem(libraryfacade.getInventory().get(id))) {
                         currUserItems.add(bookWithDate);
                         listModel.addElement(selectedItem); // Add to list model, which is a box above the return button
                         JOptionPane.showMessageDialog(newFrame, currUser + " selected: " + selectedItem.split(" ")[1], "Selected Item",
                                 JOptionPane.INFORMATION_MESSAGE);                   

//                         This basically updates the booksOwed.txt db, which means it add to whatever book the current logged in user
//                         is renting
                         libraryfacade.bookKeeping(currUser, currUserItems);} 
                         else {
                        	 JOptionPane.showMessageDialog(newFrame, "No more books", "return some",
                                     JOptionPane.ERROR_MESSAGE);
                         }
       
                }else{
                	JOptionPane.showMessageDialog(newFrame, "Renting Limit Reached", "CAN ONLY RENT 10 AT A TIME. RETURN TO PROCEEd",
                        JOptionPane.ERROR_MESSAGE);
                	} }
                	else {
                		JOptionPane.showMessageDialog(newFrame, "You owe too many books", "Return some to continue",
                                JOptionPane.ERROR_MESSAGE);
                		
                	}
                }else {
                
                	
                    JOptionPane.showMessageDialog(newFrame, "Please select an item", "No Item Selected",
                            JOptionPane.ERROR_MESSAGE);
                }
            
            }
        });        
        
        
              
        
        
        
        
//        for Return        
//        This following lines makes the box thats right above the return button
        JList<String> itemList = new JList<>(listModel); 
        JScrollPane scrollPane = new JScrollPane(itemList); 
        scrollPane.setBounds(40, 250, 500, 100); 
        newFrame.getContentPane().add(scrollPane);
        
        JButton btnReturn = new JButton("Return");
        btnReturn.setBounds(250, 370, 80, 20);
        newFrame.getContentPane().add(btnReturn);
        
        
//        here we are reading from 
//        we were populating the currUserItems with items owed by current user from booksOwed
//        but we are doing it in populizer in library.java so I'll comment it out.
//        Leaving this for ref
//        String line;
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(";");
//                if (parts[0].equals(currUser)) {
//                    for (int i = 1; i < parts.length; i++) {
//                        String[] bookAndDate = parts[i].split(":");
//                        String book = bookAndDate[0];
//                        String date = bookAndDate[1];
//                        currUserItems.add(book + " (" + date + ")");
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<String> temp = new ArrayList<String>();
        boolean bookDueSoon = false;
        long daysDifference = 0;
        
//       In the following logic, I am moving stuff from currUserItemt -> temp
//        if any item in currUserItem is overdue, it will be added to temp with the prefix OWED look to line 468
//        then we'll move it back to currUserItem to populate that box right above return button
        for (String item : currUserItems) {
            String[] parts = item.split(":");
            String book = parts[0];
            String date = parts[1];
            LocalDate dateFromDB = LocalDate.parse(date, formatter);
            daysDifference = java.time.temporal.ChronoUnit.DAYS.between(dateFromDB, today);
            if(daysDifference > 15) {
            	continue;
            }
            if(daysDifference>=9) {
            	bookDueSoon = true;
            }
            
            if(daysDifference >=10) {
            	bookOwed++;      
            	temp.add(book + ":" + date + ":Owed");
            }
            else {
            	temp.add(item);
            }
            
        }
        
        // Populate the return item box with currUserItems
        currUserItems = temp;
        
        for(String s:currUserItems) {
        	String[] parts = s.split(":");
        	if(parts.length == 3) {
        		listModel.addElement(parts[0] + " OWED");
        	}
        	else {
        		listModel.addElement(parts[0]);
        	}
        	
        }
        
//        this thing basical removes from currUserItem whatever is selected based on the index
//        and then updates the booksOwed
        btnReturn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add your return action here
                // This action will be performed when the "Return" button is clicked
                // For example, you can remove the selected item from currUserItems and update the list model
                String selectedItem = itemList.getSelectedValue();
                itemList.getSelectedIndex();
                
                if (selectedItem != null) {
                	int id = Integer.parseInt(selectedItem.split(" ")[0]);
                	currUserItems.remove(itemList.getSelectedIndex());
                	
//                    for(String s: currUserItems) {
//                    	System.out.println("btn Return " + s);
//                    }
                    
                    listModel.removeElement(selectedItem);
//                  This basically updates the booksOwed.txt db, which means it add to whatever book the current logged in user
//                  is renting.
                    libraryfacade.bookKeeping(currUser, currUserItems);
                    System.out.println(libraryfacade.getInventory().get(id));
                    client.returnItem(libraryfacade.getInventory().get(id));
                } 
                else {
                    JOptionPane.showMessageDialog(newFrame, "Please select an item to return", "No Item Selected",
                            JOptionPane.ERROR_MESSAGE);
                }
            
            }
        });
        
        newFrame.setVisible(true);
        if(bookDueSoon){
    		JOptionPane.showMessageDialog(newFrame, "YOU HAVE SOME BOOKS OVERDUE SOON", "OVERDUE NOTICE",
                    JOptionPane.ERROR_MESSAGE);
    	}
    }
}

