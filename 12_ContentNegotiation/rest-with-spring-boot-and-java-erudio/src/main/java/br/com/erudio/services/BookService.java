package br.com.erudio.services;

import br.com.erudio.DTO.v1.BookDTO;
import br.com.erudio.controllers.BookController;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    BookRepository repository;

    public List<BookDTO> findAll() {
        logger.info("Finding all book!");
        var books =  DozerMapper.parseListObjects(repository.findAll(), BookDTO.class);
        books.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return books;
    }

    public BookDTO findById(Long id) {
        logger.info("Finding one book!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = DozerMapper.parseObject(entity, BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return dto;
    }

    public BookDTO create(BookDTO book) {
        if (book == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating one book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var dto = DozerMapper.parseObject(repository.save(entity), BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public BookDTO update(BookDTO book) {
        if (book == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Updating one book!");

        var entity = repository.findById(book.getKey()).orElseThrow(()-> new ResourceNotFoundException(""));

        entity.setTitle(book.getTitle());
        entity.setPrice(book.getPrice());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setAuthor(book.getAuthor());

        var dto = DozerMapper.parseObject(repository.save(entity), BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");

        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(""));

        repository.delete(entity);
    }
}
