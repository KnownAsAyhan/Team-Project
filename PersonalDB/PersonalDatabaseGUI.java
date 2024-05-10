package PersonalDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersonalDatabaseGUI extends JFrame {
    private JTable personalTable;
    private DefaultTableModel personalTableModel;
    private PersonalDatabase personalDatabase;

    public PersonalDatabaseGUI(PersonalDatabase database) {
        this.personalDatabase = database;

        setTitle("Personal Database");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        personalTableModel = new DefaultTableModel(new Object[]{
            "Title", "Author", "Status", "Start Time", "End Time", "Time Spent", "User Rating", "User Review"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        personalTable = new JTable(personalTableModel);
        personalTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    e.consume(); 
                }
            }
        });

        populatePersonalTable(personalDatabase.getPersonalBooks());

        JButton rateBookButton = new JButton("Rate Book");
        rateBookButton.addActionListener(e -> rateBook());

        JButton changeStatusButton = new JButton("Change Status");
        changeStatusButton.addActionListener(e -> changeStatus());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(rateBookButton);
        buttonPanel.add(changeStatusButton);

        add(new JScrollPane(personalTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void populatePersonalTable(List<PersonalBook> personalBooks) {
        personalTableModel.setRowCount(0); // Clear existing rows
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (PersonalBook book : personalBooks) {
            double userRating = book.getUserRatings().isEmpty() ? -1 : book.getUserRatings().get(0);
            String userReview = book.getUserReviews().isEmpty() ? "No review" : book.getUserReviews().get(0);

            personalTableModel.addRow(new Object[]{
                book.getTitle(),
                book.getAuthor(),
                book.getStatus(),
                book.getStartTime() == null ? "Unknown" : formatter.format(book.getStartTime()),
                book.getEndTime() == null ? "Unknown" : formatter.format(book.getEndTime()),
                formatTimeSpent(book.getTimeSpent()),
                userRating == -1 ? "No rating" : String.format("%.2f", userRating),
                userReview
            });
        }
    }

    private String formatTimeSpent(int timeSpent) {
        if (timeSpent < 60) {
            return timeSpent + " minutes";
        } else if (timeSpent < 1440) {
            return timeSpent / 60 + " hours";
        } else {
            return timeSpent / 1440 + " days";
        }
    }

    private void rateBook() {
        int selectedRow = personalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to rate.");
            return;
        }
    
        String title = (String) personalTableModel.getValueAt(selectedRow, 0);
        PersonalBook book = personalDatabase.getPersonalBook(title);
    
        if (book == null) {
            JOptionPane.showMessageDialog(this, "Book not found.");
            return;
        }
    
        String ratingStr = JOptionPane.showInputDialog(this, "Enter your rating (1-5):");
        if (ratingStr == null || ratingStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid rating.");
            return;
        }
    
        try {
            double rating = Double.parseDouble(ratingStr);
            if (rating < 1 || rating > 5) {
                throw new NumberFormatException();
            }
            book.rateBook(rating);
            personalDatabase.saveToFile();
            populatePersonalTable(personalDatabase.getPersonalBooks());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid rating (1-5).");
        }
    }
    

    private void changeStatus() {
        int selectedRow = personalTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to change its status.");
            return;
        }

        String[] options = {"Not Started", "Ongoing", "Completed"};
        String selectedStatus = (String) JOptionPane.showInputDialog(
                this,
                "Select Status:",
                "Change Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (selectedStatus != null) {
            String title = (String) personalTableModel.getValueAt(selectedRow, 0);
            PersonalBook book = personalDatabase.getPersonalBook(title);

            if (book == null) {
                JOptionPane.showMessageDialog(this, "Book not found.");
                return;
            }

            if (selectedStatus.equals("Ongoing")) {
                book.setStatus(selectedStatus);
                book.setStartTime(LocalDateTime.now());
            } else if (selectedStatus.equals("Completed")) {
                book.setStatus(selectedStatus);
                book.setEndTime(LocalDateTime.now());
                book.stopTimer();
            } else {
                book.setStatus(selectedStatus);
            }

            personalDatabase.saveToFile();
            populatePersonalTable(personalDatabase.getPersonalBooks());
        }
    }
}