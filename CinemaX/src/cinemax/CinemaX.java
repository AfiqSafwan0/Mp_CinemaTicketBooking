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

    public static void selectExperience() {
        String[] experiences = {"1. IMAX", "2. Dolby Atmos", "3. Infinity", "4. Standard"};
        JComboBox<String> experienceComboBox = new JComboBox<>(experiences);
        String[] availableDates = {"8pm / 30 Oct", "9pm / 31 Oct", "10pm / 1 Nov"};
        JComboBox<String> dateComboBox = new JComboBox<>(availableDates);

        JPanel experiencePanel = new JPanel();
        experiencePanel.setLayout(new BoxLayout(experiencePanel, BoxLayout.Y_AXIS));
        experiencePanel.add(new JLabel("Select Experience:"));
        experiencePanel.add(experienceComboBox);
        experiencePanel.add(Box.createVerticalStrut(20));
        experiencePanel.add(new JLabel("Select Date/Time:"));
        experiencePanel.add(dateComboBox);

        int option = JOptionPane.showConfirmDialog(null, experiencePanel, "Select Experience and Date/Time", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (option == JOptionPane.OK_OPTION) {
            selectedExperience = (String) experienceComboBox.getSelectedItem();
            selectedDate = (String) dateComboBox.getSelectedItem();

            double ticketPrice = 0.0;
            if (selectedExperience.startsWith("1")) {
                ticketPrice = 25.00;
            } else if (selectedExperience.startsWith("2")) {
                ticketPrice = 17.00;
            } else if (selectedExperience.startsWith("3")) {
                ticketPrice = 15.00;
            } else if (selectedExperience.startsWith("4")) {
                ticketPrice = 12.00;
            }

            enterPeople(ticketPrice);
        } else {
            JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void enterPeople(double ticketPrice) {
        JPanel peoplePanel = new JPanel();
        peoplePanel.setLayout(new GridLayout(2, 1, 5, 5));

        JTextField numAdultsField = new JTextField(5);
        peoplePanel.add(new JLabel("Enter the number of adults:"));
        peoplePanel.add(numAdultsField);

        JTextField numChildrenField = new JTextField(5);
        peoplePanel.add(new JLabel("Enter the number of children:"));
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

        // Display current seating layout
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                seatCheckBoxes[i][j] = new JCheckBox(rowLabels[i] + (j + 1));
                seatCheckBoxes[i][j].setSelected(currentSeats[i][j]);
                seatCheckBoxes[i][j].setEnabled(!currentSeats[i][j]); // Disable already selected seats
                seatPanel.add(seatCheckBoxes[i][j]);
            }
        }

        int option = JOptionPane.showConfirmDialog(null, seatPanel, "Select Seats", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            StringBuilder selectedSeatsBuilder = new StringBuilder();
            int selectedCount = 0; // Track number of selected seats

            // Count selected seats
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    if (seatCheckBoxes[i][j].isSelected()) {
                        currentSeats[i][j] = true;  // Mark as selected
                        selectedSeatsBuilder.append(rowLabels[i]).append(j + 1).append("; ");
                        selectedCount++;  // Increment selected seats count
                    }
                }
            }

            // Debug: Print selectedCount and the total number of people
            System.out.println("Selected seats count: " + selectedCount);
            System.out.println("Total number of people: " + (numAdults + numChildren));

            // Check if the number of selected seats matches the total number of people
            if (selectedCount == (numAdults + numChildren)) {
                selectedSeats = selectedSeatsBuilder.toString();
                seatHistory.put(seatKey, currentSeats); // Update seat history with new selections
                recheckPayment();
            } else {
                // If the selected seats count does not match the total number of people
                JOptionPane.showMessageDialog(null, "The number of selected seats must match the number of people. Please reselect.", "Error", JOptionPane.ERROR_MESSAGE);
                selectSeats(); // Prompt user to reselect seats
            }
        } else {
            JOptionPane.showMessageDialog(null, "Returning to main menu.", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void recheckPayment() {
        String message = "Selected Movie: " + selectedMovie + "\n" +
                         "Experience: " + selectedExperience + "\n" +
                         "Date/Time: " + selectedDate + "\n" +
                         "Seats: " + selectedSeats + "\n" +
                         "Total Cost: RM " + totalCost + "\n" +
                         "Would you like to proceed with the payment?";

        int confirm = JOptionPane.showConfirmDialog(null, message, "Recheck Payment", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null);

        if (confirm == JOptionPane.YES_OPTION) {
            String paymentMethods = "1. Credit/Debit Card\n2. Cash\n3. Return to menu";
            String paymentChoice = JOptionPane.showInputDialog(null, "Select payment method:\n" + paymentMethods, "Payment", JOptionPane.PLAIN_MESSAGE);

            switch (paymentChoice) {
                case "1":
                case "2":
                    JOptionPane.showMessageDialog(null, "Payment successful. Enjoy the movie!", "CinemaX", JOptionPane.INFORMATION_MESSAGE);
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
