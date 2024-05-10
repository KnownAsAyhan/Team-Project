package GeneralDB;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import PersonalDB.PersonalBook;
import PersonalDB.PersonalDatabase;

public class GeneralDatabaseGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private PersonalDatabase personalDatabase;

    public GeneralDatabaseGUI(GeneralDatabase generalDatabase, PersonalDatabase personalDatabase, boolean isRegularUser) {
        setTitle("General Database");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        this.personalDatabase = personalDatabase; // Store reference to personal database

        // Create the table model with non-editable cells
        tableModel = new DefaultTableModel(new Object[]{"Title", "Author", "Rating", "Reviews"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing entirely
            }
        };

        // Create the table and add key listener to prevent deletion by keypress
        table = new JTable(tableModel);
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
                    e.consume(); // Prevent backspace and delete
                }
            }
        });

        populateTable(generalDatabase.getBooks());

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Add a button for regular users to add to personal library
        if (isRegularUser) {
            JButton addToPersonalLibraryButton = new JButton("Add to Personal Library");
            addToPersonalLibraryButton.addActionListener(e -> addBookToPersonalLibrary());
            bottomPanel.add(addToPersonalLibraryButton);
        }

        // Add the "Add Book" button under the table
        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the selected row
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(GeneralDatabaseGUI.this, "Please select a book to add to your personal library.");
                    return;
                }

                // Retrieve the book details from the selected row
                String title = (String) tableModel.getValueAt(selectedRow, 0);
                String author = (String) tableModel.getValueAt(selectedRow, 1);

                // Create a PersonalBook object with the retrieved details
                PersonalBook personalBook = new PersonalBook(title, author);

                // Add the PersonalBook to the personal database
                personalDatabase.addPersonalBook(personalBook);

                // Display a success message
                JOptionPane.showMessageDialog(GeneralDatabaseGUI.this, "Book added to your personal library.");
            }
        });
        bottomPanel.add(addBookButton);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void populateTable(List<GeneralBook> books) {
        tableModel.setRowCount(0); // Clear existing rows

        for (GeneralBook book : books) {
            double rating = book.getAverageRating();
            String ratingStr = (rating == -1) ? "No rating" : String.format("%.2f (%d)", rating, book.getRatingCount());

            String reviews = book.getReviews().isEmpty() ? "No reviews" : String.join(", ", book.getReviews());

            tableModel.addRow(new Object[]{
                book.getTitle(),
                book.getAuthor(),
                ratingStr,
                reviews
            });
        }
    }

    private void addBookToPersonalLibrary() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to add to your personal library.");
            return;
        }
    
        String title = (String) tableModel.getValueAt(selectedRow, 0);
        String author = (String) tableModel.getValueAt(selectedRow, 1);
    
        // Check if the book already exists in the personal database
        if (personalDatabase.getPersonalBook(title) != null) {
            JOptionPane.showMessageDialog(this, "This book is already in your personal library.");
            return;
        }
    
        // Create a PersonalBook object with the retrieved details
        PersonalBook personalBook = new PersonalBook(title, author);
    
        // Add the PersonalBook to the personal database
        personalDatabase.addPersonalBook(personalBook);
    
        // Display a success message
        JOptionPane.showMessageDialog(this, "Book added to your personal library.");
    }
}