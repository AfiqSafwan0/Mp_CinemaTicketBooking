/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cinemax;

/**
 *
 * @author USER
 */
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CinemaX {
    private static final int rows = 5;
    private static final int columns = 5;
    private static String selectedmovie, selectedexp, selecteddatetime, selectedseats;
    private static int numAdults, numChildren;
    private static double totalCost;
    private static String prevmovies = "", prevexp = "", prevdatetime = "";
    private static Map<String, boolean[][]> Seats = new HashMap<>(); //to store seats

    public static void main(String[] args) {
        //Set UI Manager properties for consistent styling
        UIManager.put("OptionPane.background", new Color(75, 0, 130)); //Dark purple background
        UIManager.put("Panel.background", new Color(75, 0, 130)); //Dark purple for all panels
        UIManager.put("OptionPane.messageForeground", Color.WHITE); //White text
        UIManager.put("OptionPane.font", new Font("Georgia", Font.BOLD, 16)); //Font style
        UIManager.put("Button.background", new Color(58, 14, 102)); //Darker purple for buttons
        UIManager.put("Button.foreground", Color.WHITE); //White button text

        boolean exit = false;

        while (!exit) {
            //import movie image from src
            String[] movies = {"Sheriff: Narko Integriti", "Transformers One", "The Wild Robot"};
            ImageIcon logo1 = new ImageIcon("src/CinemaX/sheriff.jpeg");
            ImageIcon logo2 = new ImageIcon("src/CinemaX/transformers.jpeg");
            ImageIcon logo3 = new ImageIcon("src/CinemaX/wild.jpeg");

            JPanel moviePanel = new JPanel(new GridLayout(1, 3, 10, 10));
            moviePanel.setBackground(new Color(75, 0, 130)); //Dark purple background
            
            //set panel to display movie image
            JPanel movie1Panel = createMoviePanel("Sheriff: Narko Integriti", logo1);
            JPanel movie2Panel = createMoviePanel("Transformers One", logo2);
            JPanel movie3Panel = createMoviePanel("The Wild Robot", logo3);

            moviePanel.add(movie1Panel);
            moviePanel.add(movie2Panel);
            moviePanel.add(movie3Panel);
            
            //create JComboBox for each movie name
            JComboBox<String> movieComboBox = new JComboBox<>(movies);
            movieComboBox.setFont(new Font("Georgia", Font.PLAIN, 16));
            movieComboBox.setBackground(new Color(230, 230, 250)); //Light lavender
            movieComboBox.setForeground(Color.BLACK); //Black text
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(75, 0, 130)); //Dark purple
            mainPanel.add(moviePanel, BorderLayout.CENTER);
            mainPanel.add(movieComboBox, BorderLayout.SOUTH);

            int option = JOptionPane.showConfirmDialog(
                    null,
                    mainPanel,
                    "Welcome to CinemaX - Please select a movie",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                selectedmovie = (String) movieComboBox.getSelectedItem();
                prevmovies = selectedmovie;
                selectExperience();
            } else {
                JOptionPane.showMessageDialog(null, "No movie selected. Exiting...", "CinemaX", JOptionPane.WARNING_MESSAGE);
                exit = true;
            }
        }
    }
    //Set color for movie selection panel
    private static JPanel createMoviePanel(String title, ImageIcon logo) {
        JPanel moviePanel = new JPanel(new BorderLayout());
        moviePanel.setBackground(new Color(58, 14, 102)); // Slightly darker purple
        JLabel movieLabel = new JLabel(title, JLabel.CENTER);
        movieLabel.setFont(new Font("Georgia", Font.BOLD, 14));
        movieLabel.setForeground(Color.WHITE); // White text
        moviePanel.add(new JLabel(logo), BorderLayout.CENTER);
        moviePanel.add(movieLabel, BorderLayout.SOUTH);
        return moviePanel;
    }

    public static void selectExperience() {
    // Set combobox for experience
    String[] experiences = {"IMAX / RM25", "Dolby Atmos / RM17", "Infinity / RM15"};
    JComboBox<String> expComboBox = new JComboBox<>(experiences);

    // Create a JDateChooser to allow the user to pick a date
    JDateChooser dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("dd MMM yyyy"); // Format for the selected date
    dateChooser.getDateEditor().getUiComponent().setForeground(Color.BLACK);
    dateChooser.getDateEditor().getUiComponent().setBackground(Color.WHITE); // Change to white for contrast

    // Reset calendar appearance to default (do not customize UI component styles)
    JComponent editor = dateChooser.getDateEditor().getUiComponent();
    if (editor instanceof JTextField) {
        editor.setFont(new Font("Georgia", Font.PLAIN, 14)); // Use a neutral font
        editor.setBackground(UIManager.getColor("TextField.background")); // Default background
        editor.setForeground(UIManager.getColor("TextField.foreground")); // Default text color
    }

    // Customize calendar popup background
    dateChooser.getJCalendar().setBackground(Color.LIGHT_GRAY); // Set calendar background color
    dateChooser.getJCalendar().setForeground(Color.WHITE); // Set calendar text color

    // ComboBox for time selection
    String[] timeSlots = {"10:00 AM", "1:00 PM", "4:00 PM", "7:00 PM", "10:00 PM"};
    JComboBox<String> timeComboBox = new JComboBox<>(timeSlots);
    styleComboBox(timeComboBox); // Style the ComboBox

    // Style the experience ComboBox
    styleComboBox(expComboBox);

    // Set style for panel and labels
    JPanel expPanel = new JPanel();
    expPanel.setLayout(new BoxLayout(expPanel, BoxLayout.Y_AXIS));
    expPanel.setBackground(new Color(75, 0, 130)); // Dark purple

    // Experience selection label
    JLabel expLabel = new JLabel("Select Experience:");
    expLabel.setFont(new Font("Georgia", Font.BOLD, 14));
    expLabel.setForeground(Color.WHITE);
    expPanel.add(expLabel);
    expPanel.add(expComboBox);
    expPanel.add(Box.createVerticalStrut(20));

    // Date selection label
    JLabel dateLabel = new JLabel("Select Date:");
    dateLabel.setFont(new Font("Georgia", Font.BOLD, 14));
    dateLabel.setForeground(Color.WHITE);
    expPanel.add(dateLabel);
    expPanel.add(dateChooser);
    expPanel.add(Box.createVerticalStrut(20));

    // Time selection label
    JLabel timeLabel = new JLabel("Select Time:");
    timeLabel.setFont(new Font("Georgia", Font.BOLD, 14));
    timeLabel.setForeground(Color.WHITE);
    expPanel.add(timeLabel);
    expPanel.add(timeComboBox);

    int option = JOptionPane.showConfirmDialog(null, expPanel, "Select Experience, Date, and Time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (option == JOptionPane.OK_OPTION) {
        selectedexp = (String) expComboBox.getSelectedItem();
        Date selectedDate = dateChooser.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        selecteddatetime = dateFormat.format(selectedDate) + " at " + (String) timeComboBox.getSelectedItem();

        // Set different price for each experience
        double ticketPrice = 0.0;
        if (selectedexp.equals("IMAX / RM25")) {
            ticketPrice = 25.00;
        } else if (selectedexp.equals("Dolby Atmos / RM17")) {
            ticketPrice = 17.00;
        } else if (selectedexp.equals("Infinity / RM15")) {
            ticketPrice = 15.00;
        }
        // Call another method to enter the number of people with the determined ticket price
        enterPeople(ticketPrice);
    } else {
        // If the user cancels, display a message and return to the main menu
        JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
    }
}

    //set style for combobox
    private static void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Georgia", Font.PLAIN, 16));
        comboBox.setBackground(new Color(230, 230, 250)); //Light lavender
        comboBox.setForeground(Color.BLACK); //Black text
    }

    public static void enterPeople(double ticketPrice) {
        JPanel peoplePanel = new JPanel();
        peoplePanel.setLayout(new GridLayout(2, 1, 5, 5));
        peoplePanel.setBackground(new Color(75, 0, 130)); //Dark purple background
        
        //create textfield for user input
        JTextField numAdultsField = new JTextField(5);
        JTextField numChildrenField = new JTextField(5);
        
        //create jlabel
        JLabel adultsLabel = new JLabel("Number of adults:");
        adultsLabel.setForeground(Color.WHITE);
        JLabel childrenLabel = new JLabel("Number of children (50% off):");
        childrenLabel.setForeground(Color.WHITE);
        //set font style for jlabel
        adultsLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        childrenLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        
        peoplePanel.add(adultsLabel);
        peoplePanel.add(numAdultsField);
        peoplePanel.add(childrenLabel);
        peoplePanel.add(numChildrenField);

        int option = JOptionPane.showConfirmDialog(null, peoplePanel, "Enter Number of People", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //call another method to enter the number of people with the determined ticket price
        if (option == JOptionPane.OK_OPTION) {
            try {
                numAdults = Integer.parseInt(numAdultsField.getText()); //get num of adult
                numChildren = Integer.parseInt(numChildrenField.getText()); //get num of children
                totalCost = (numAdults * ticketPrice) + (numChildren * (ticketPrice * 0.5)); //count total ticket price
                selectSeats();
            } catch (NumberFormatException e) {
                //error if user input alphabet in the textfield
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                enterPeople(ticketPrice);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
        }
    }

        public static void selectSeats() {
        //set key to store seat number based on select movie, exp and date/time
        String seats = selectedmovie + "-" + selectedexp + "-" + selecteddatetime;
        
        //check if the combination of the selected are already saved
        boolean[][] currentSeats;
        if (Seats.containsKey(seats)) {
            //if already save, retrieve them
            currentSeats = Seats.get(seats);
        } else {
            //if no seats are saved, create new boolean array to store data
            currentSeats = new boolean[rows][columns];
            Seats.put(seats, currentSeats);
        }
        //create jpanel and set style for checkboxes
        JPanel seatPanel = new JPanel(new GridLayout(rows, columns, 5, 5));
        seatPanel.setBackground(new Color(75, 0, 130)); //Dark purple background
        JCheckBox[][] seatCheckBoxes = new JCheckBox[rows][columns];
        String[] rowLabels = {"A", "B", "C", "D", "E"};
        Font font = new Font("Georgia", Font.PLAIN, 16);
        
        //loop for all row and column to create checkboxes
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seatCheckBoxes[i][j] = new JCheckBox(rowLabels[i] + (j + 1));
                seatCheckBoxes[i][j].setFont(font);
                seatCheckBoxes[i][j].setBackground(new Color(230, 230, 250)); //Light lavender
                seatCheckBoxes[i][j].setForeground(Color.BLACK); //Black text
                seatCheckBoxes[i][j].setSelected(currentSeats[i][j]);
                seatCheckBoxes[i][j].setEnabled(!currentSeats[i][j]);
                seatPanel.add(seatCheckBoxes[i][j]);
            }
        }
        //import seating plan image
        ImageIcon logo4 = new ImageIcon("src/CinemaX/seats (1).png");
        JLabel imageLabel = new JLabel(logo4);
        
        //set style for seat panel
        JPanel seatsPanel = new JPanel(new BorderLayout());
        seatsPanel.setBackground(new Color(75, 0, 130)); //Dark purple
        seatsPanel.add(seatPanel, BorderLayout.CENTER);
        seatsPanel.add(imageLabel, BorderLayout.EAST);

        int option = JOptionPane.showConfirmDialog(null, seatsPanel, "Select Seats", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        //if user press ok seat data will be pick
        if (option == JOptionPane.OK_OPTION) {
            StringBuilder selectedseatsBuilder = new StringBuilder();
            int newSelectedCount = 0;
            //temp array to hold selected seats
            boolean[][] tempSeats = new boolean[rows][columns]; // Temporary selection
            //loop for all seats to check if they are selected
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    //if the seat is selected and it was not already selected
                    if (seatCheckBoxes[i][j].isSelected() && !currentSeats[i][j]) {
                        tempSeats[i][j] = true; //mark this seat as selected in the temp array
                        selectedseatsBuilder.append(rowLabels[i]).append(j + 1).append(" ");
                        newSelectedCount++; //increment the count of newly selected seats
                    }
                }
            }
            //check if the number of selected seats matches the required number of people
            if (newSelectedCount == (numAdults + numChildren)) {
                selectedseats = selectedseatsBuilder.toString();
                //update the current seat states with the newly selected seats
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        // Mark seat as selected in currentSeats
                        currentSeats[i][j] = tempSeats[i][j] || currentSeats[i][j];
                    }
                }
                Seats.put(seats, currentSeats); //save the updated seat
                recheckPayment(); //proceed to rechek and payment
            } else {
                JOptionPane.showMessageDialog(null, "The number of selected seats must match the number of people. Please reselect.", "Error", JOptionPane.ERROR_MESSAGE);
                selectSeats(); //recursive call
            }
        } else {
            JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
        }
    }

        public static void recheckPayment() {
    // Panel to hold the styled message and input field
    JPanel panel = new JPanel();
    panel.setBackground(new Color(75, 0, 130));
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical alignment

    // Format the total cost to two decimal places
    String totalcost = String.format("%.2f", totalCost);

    // Message with required styling
    JTextArea messageArea = new JTextArea("Selected Movie: " + selectedmovie + "\n" +
                                          "Experience        : " + selectedexp + "\n" +
                                          "Date                   : " + selecteddatetime + "\n" +
                                          "Seats                  : " + selectedseats + "\n\n" +
                                          "Total Cost          : RM " + totalcost + "\n\n" +
                                          "Please select your payment method below.\n" +
                                          "1. Credit/Debit Card\n" +
                                          "2. Cash\n" +
                                          "3. FPX/Online Banking");
    messageArea.setFont(new Font("Georgia", Font.BOLD, 16));
    messageArea.setForeground(Color.WHITE);
    messageArea.setBackground(new Color(75, 0, 130));
    messageArea.setEditable(false); 
    messageArea.setBorder(null); 

    // Add the message area to the panel
    panel.add(messageArea);

    // Input field for payment choice
    JTextField paymentField = new JTextField();
    paymentField.setMaximumSize(new Dimension(200, 30)); // Set size for the text field
    panel.add(paymentField);

    // Show the panel in a dialog
    int confirm = JOptionPane.showConfirmDialog(null, panel, "Recheck and Payment", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (confirm == JOptionPane.OK_OPTION) {
        String paymentChoice = paymentField.getText().trim(); // Get user input

        switch (paymentChoice) {
            case "1":
                JOptionPane.showMessageDialog(null, "Payment successful. Enjoy the movie!", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "2":
                JOptionPane.showMessageDialog(null, "Please go to counter to make the payment. Enjoy the movie!", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "3":
                JOptionPane.showMessageDialog(null, "Payment successful. Enjoy the movie!", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid option. Please select a valid payment method.", "Error", JOptionPane.ERROR_MESSAGE);
                recheckPayment(); // Retry on invalid input
                break;
        }
    } else {
        // If the user presses "Cancel", reset the seats
        resetselectseats();
        JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
    }
}

    //this method resets the selected seats
    public static void resetselectseats() {
        String seats = selectedmovie + "-" + selectedexp + "-" + selecteddatetime;

        //get the current seats and reset them
        boolean[][] currentSeats = Seats.get(seats);
        if (currentSeats != null) {
            //reset all selected seats (set them to false, meaning available)
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    currentSeats[i][j] = false;  //mark seats as available
                }
            }
            //put the updated seats back into the map
            Seats.put(seats, currentSeats);
        }
    }
}