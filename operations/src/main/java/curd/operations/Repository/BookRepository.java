package curd.operations.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import curd.operations.BookPackage.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
