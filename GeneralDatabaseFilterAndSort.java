import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import PersonalDB.PersonalBook;

public class GeneralDatabaseFilterAndSort {
    private List<PersonalBook> personalBooks; // List to store books

    public GeneralDatabaseFilterAndSort() {
        this.personalBooks = new ArrayList<>(); // Initialize the list in the constructor
    }

    public void addBook(PersonalBook book) {
        personalBooks.add(book); // Add a book to the list
    }

    public List<PersonalBook> getBooks() {
        return personalBooks; // Return the list of books
    }

    public List<PersonalBook> sortBooksByColumn(String column, boolean ascending) {
        Comparator<PersonalBook> comparator = null; // Comparator to define sorting order

        // Determine the comparator based on the provided column name
        switch (column.toLowerCase()) {
            case "title":
                comparator = Comparator.comparing(PersonalBook::getTitle); // Sort by title
                break;
            case "author":
                comparator = Comparator.comparing(PersonalBook::getAuthor); // Sort by author
                break;
            case "reviews":
                comparator = Comparator.comparingInt(book -> book.getUserReviews().size()); // Sort by number of reviews
                break;
            default:
                throw new IllegalArgumentException("Invalid column name"); // Throw exception for invalid column
        }

        if (!ascending) {
            comparator = comparator.reversed(); // Reverse the comparator for descending order
        }

        // Sort the books using the comparator and collect them into a list
        return personalBooks.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<PersonalBook> filterBooks(String keyword) {
        // Filter the books based on the keyword in title or author (case-insensitive)
        // and collect them into a list
        return personalBooks.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase())
                        || book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}