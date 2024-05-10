package PersonalDB;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PersonalDatabase {
    private List<PersonalBook> personalBooks;
    private String currentUser;
    private static final String PERSONAL_DATABASE_FILE = "personal_database.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PersonalDatabase() {
        personalBooks = new ArrayList<>();
        currentUser = "";
        loadFromFile();
    }

    public List<PersonalBook> getPersonalBooks() {
        return new ArrayList<>(personalBooks);
    }

    public void setUser(String username) {
        this.currentUser = username;
    }

    public void addPersonalBook(PersonalBook book) {
        personalBooks.add(book);
        saveToFile(); // Save the updated list to file
    }

    public PersonalBook getPersonalBook(String title) {
        return personalBooks.stream()
            .filter(book -> book.getTitle().equalsIgnoreCase(title)) // Case-insensitive title matching
            .findFirst()
            .orElse(null);
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PERSONAL_DATABASE_FILE))) {
            for (PersonalBook book : personalBooks) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s,%s,%s,%s%n", book.getTitle(), book.getStatus(),
                                        formatDateTime(book.getStartTime()), formatDateTime(book.getEndTime())));
                writer.write(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        personalBooks.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(PERSONAL_DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String title = parts[0];
                    String status = parts[1];
                    LocalDateTime startTime = parseDateTime(parts[2]);
                    LocalDateTime endTime = parseDateTime(parts[3]);
                    PersonalBook book = new PersonalBook(title, "");
                    book.setStatus(status);
                    book.setStartTime(startTime);
                    book.setEndTime(endTime);
                    personalBooks.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "Unknown";
        }
        return dateTime.format(formatter);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        if (dateTimeStr.equals("Unknown")) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
}
