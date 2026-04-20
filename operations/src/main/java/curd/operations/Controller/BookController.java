package curd.operations.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import curd.operations.BookPackage.Book;
import curd.operations.Repository.BookRepository;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> GetAllBooks() {
        try {
            List<Book> books = new ArrayList<>();
            bookRepository.findAll().forEach(books::add);

            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> GetBookById(@PathVariable long id) {
        Optional<Book> book = bookRepository.findById(id);
       
        if (book.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<>(book.get(), HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> AddBook(@RequestBody Book book) {
       Book bookObj = bookRepository.save(book);
       return new ResponseEntity<>(bookObj, HttpStatus.OK);
    }

    @PutMapping("/updateBookById/{id}")
    public ResponseEntity<Book> UpdateBookById(@PathVariable long id, @RequestBody Book newBook) {
        Optional<Book> oldBook = bookRepository.findById(id);

        if (oldBook.isPresent()) {
            Book upDatedBook = oldBook.get();
            upDatedBook.setTitle(newBook.getTitle());
            upDatedBook.setAuthor(newBook.getAuthor());
            upDatedBook.setPrice(newBook.getPrice());

            return new ResponseEntity<>(bookRepository.save(upDatedBook), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
    }
    @DeleteMapping("/deleteBookById/{id}")
    public ResponseEntity<Void> DeleteBookById(@PathVariable long id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
