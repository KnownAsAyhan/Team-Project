package repository;

import PersonalDB.PersonalBook;

public interface BookRepository {
    PersonalBook findById(String personalBookId);
    void save(PersonalBook personalBooks);
    void delete(PersonalBook personalBooks);
}