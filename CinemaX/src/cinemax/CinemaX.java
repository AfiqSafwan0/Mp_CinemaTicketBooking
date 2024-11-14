/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cinemax;

/**
 *
 * @author USER
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CinemaX {
    private static final int rows = 5;
    private static final int columns = 5;
    private static String selectedMovie, selectedExperience, selectedDate, selectedSeats;
    private static int numAdults, numChildren;
    private static double totalCost;
    private static String previousMovie = "", previousExperience = "", previousDate = "";
    private static Map<String, boolean[][]> seatHistory = new HashMap<>();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            String[] movies = {"Sheriff: Narko Integriti", "Transformers One", "The Wild Robot"};
            ImageIcon logo1 = new ImageIcon("src/CinemaX/sheriff.jpeg");
            ImageIcon logo2 = new ImageIcon("src/CinemaX/transformers.jpeg");
            ImageIcon logo3 = new ImageIcon("src/CinemaX/wild.jpeg");

            JPanel moviePanel = new JPanel(new GridLayout(1, 3, 10, 10));
            JPanel movie1Panel = new JPanel(new BorderLayout());
            movie1Panel.add(new JLabel(logo1), BorderLayout.CENTER);
            movie1Panel.add(new JLabel("Sheriff: Narko Integriti", JLabel.CENTER), BorderLayout.SOUTH);
            JPanel movie2Panel = new JPanel(new BorderLayout());
            movie2Panel.add(new JLabel(logo2), BorderLayout.CENTER);
            movie2Panel.add(new JLabel("Transformers One", JLabel.CENTER), BorderLayout.SOUTH);
            JPanel movie3Panel = new JPanel(new BorderLayout());
            movie3Panel.add(new JLabel(logo3), BorderLayout.CENTER);
            movie3Panel.add(new JLabel("The Wild Robot", JLabel.CENTER), BorderLayout.SOUTH);

            moviePanel.add(movie1Panel);
            moviePanel.add(movie2Panel);
            moviePanel.add(movie3Panel);

            String choice = (String) JOptionPane.showInputDialog(
                    null,
                    moviePanel,
                    "Welcome to C i n e m a X ☺  -Please select a movie",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    movies,
                    movies[0]
            );

            if (choice != null) {
                selectedMovie = choice;
                previousMovie = selectedMovie;
                selectExperience();
            } else {
                JOptionPane.showMessageDialog(null, "No movie selected. Exiting...", "CinemaX", JOptionPane.WARNING_MESSAGE);
                exit = true;
            }
        }
    }

    //hurin's part
    public static void selectExperience() {
        //define available experiences and dates for selection        
        String[] experiences = {"IMAX / RM25", "Dolby Atmos / RM17", "Infinity / RM15"};
        JComboBox<String> experienceComboBox = new JComboBox<>(experiences); //ComboBox for experiences
        String[] availableDates = {"8pm / 21 Nov", "9pm / 22 Nov", "10pm / 23 Nov"}; //ComboBox for Date/Time
        JComboBox<String> dateComboBox = new JComboBox<>(availableDates);
        
        //panel to hold the components with vertical layout
        JPanel experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.Y_AXIS));
        experiencePanel.add(new JLabel("Select Experience:")); //label for experience selection
        experiencePanel.add(experienceComboBox); //combo box for selecting experience
        experiencePanel.add(Box.createVerticalStrut(20)); //spacer between components
        experiencePanel.add(new JLabel("Select Date/Time:")); //label for date selection
        experiencePanel.add(dateComboBox); //combo box for selecting time/date
        
        //dialog to allow the user to select experience and date
        int option = JOptionPane.showConfirmDialog(null, experiencePanel, "Select Experience and Date/Time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
       
        //user selects 'OK', process the selected experience and date
        if (option == JOptionPane.OK_OPTION) {
            selectedExperience = (String) experienceComboBox.getSelectedItem();
            selectedDate = (String) dateComboBox.getSelectedItem();
             
            //set ticket price based on the selected experience
            double ticketPrice = 0.0;
            if (selectedExperience.startsWith("I")) {
                ticketPrice = 25.00; //IMAX price
            } else if (selectedExperience.startsWith("D")) {
                ticketPrice = 17.00; //Dolby Atmos price
            } else if (selectedExperience.startsWith("I")) {
                ticketPrice = 15.00; //Infinity price
            }
            //call another method to enter the number of people with the determined ticket price
            enterPeople(ticketPrice);
        } else {
            //if the user cancels, display a message and return to the main menu
            JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void enterPeople(double ticketPrice) {
        JPanel peoplePanel = new JPanel();
        peoplePanel.setLayout(new GridLayout(2, 1, 5, 5));

        JTextField numAdultsField = new JTextField(5);
        peoplePanel.add(new JLabel("Number of adults:"));
        peoplePanel.add(numAdultsField);

        JTextField numChildrenField = new JTextField(5);
        peoplePanel.add(new JLabel("Number of children (50% off):"));
        peoplePanel.add(numChildrenField);

        int option = JOptionPane.showConfirmDialog(null, peoplePanel, "Enter Number of People", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            try {
                numAdults = Integer.parseInt(numAdultsField.getText());
                numChildren = Integer.parseInt(numChildrenField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            totalCost = (numAdults * ticketPrice) + (numChildren * (ticketPrice * 0.5));
            selectSeats();
        } else {
            JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void selectSeats() {
    String seatKey = selectedMovie + "-" + selectedExperience + "-" + selectedDate;

    boolean[][] currentSeats;
    if (seatHistory.containsKey(seatKey)) {
        currentSeats = seatHistory.get(seatKey);
    } else {
        currentSeats = new boolean[rows][columns];
        seatHistory.put(seatKey, currentSeats);
    }

    JPanel seatPanel = new JPanel(new GridLayout(rows, columns, 5, 5));
    JCheckBox[][] seatCheckBoxes = new JCheckBox[rows][columns];
    String[] rowLabels = {"A", "B", "C", "D", "E"};

    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
            seatCheckBoxes[i][j] = new JCheckBox(rowLabels[i] + (j + 1));
            seatCheckBoxes[i][j].setSelected(currentSeats[i][j]);
            seatCheckBoxes[i][j].setEnabled(!currentSeats[i][j]);
            seatPanel.add(seatCheckBoxes[i][j]);
        }
    }

    ImageIcon seatsImage = new ImageIcon("src/CinemaX/seats (1).png");
    JLabel imageLabel = new JLabel(seatsImage);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(seatPanel, BorderLayout.CENTER);
    mainPanel.add(imageLabel, BorderLayout.EAST);

    int option = JOptionPane.showConfirmDialog(null, mainPanel, "Select Seats", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (option == JOptionPane.OK_OPTION) {
        StringBuilder selectedSeatsBuilder = new StringBuilder();
        int newSelectedCount = 0;
        boolean[][] tempSeats = new boolean[rows][columns]; // Temporary selection to reset if needed

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (seatCheckBoxes[i][j].isSelected() && !currentSeats[i][j]) {
                    tempSeats[i][j] = true;
                    selectedSeatsBuilder.append(rowLabels[i]).append(j + 1).append(" ");
                    newSelectedCount++;
                }
            }
        }

        if (newSelectedCount == (numAdults + numChildren)) {
            selectedSeats = selectedSeatsBuilder.toString();
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    currentSeats[i][j] = tempSeats[i][j] || currentSeats[i][j]; // Update only with final selections
                }
            }
            seatHistory.put(seatKey, currentSeats);
            recheckPayment();
        } else {
            JOptionPane.showMessageDialog(null, "The number of selected seats must match the number of people. Please reselect.", "Error", JOptionPane.ERROR_MESSAGE);
            selectSeats(); // Recursive call to reset selection if needed
        }
    } else {
        JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
    }
}


    public static void recheckPayment() {
        String message = "Selected Movie: " + selectedMovie + "\n" +
                         "Experience        : " + selectedExperience + "\n" +
                         "Date/Time          : " + selectedDate + "\n" +
                         "Seats                  : " + selectedSeats + "\n" +
                         "Total Cost          : RM " + totalCost + "\n\n" +
                         "Would you like to proceed with the payment?";

        int confirm = JOptionPane.showConfirmDialog(null, message, "Recheck Payment", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (confirm == JOptionPane.YES_OPTION) {
            String paymentMethods = "1. Credit/Debit Card\n2. Cash\n3. Return to menu";
            String paymentChoice = JOptionPane.showInputDialog(null, "Select payment method:\n" + paymentMethods, "Payment", JOptionPane.PLAIN_MESSAGE);

            switch (paymentChoice) {
                case "1":
                    JOptionPane.showMessageDialog(null, "Payment successful. Enjoy the movie!", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null, "Please go to counter to make the payment. Enjoy the movie!", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "3":
                    JOptionPane.showMessageDialog(null, "Returning to menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please select a valid payment method.", "Error", JOptionPane.ERROR_MESSAGE);
                    recheckPayment();
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
