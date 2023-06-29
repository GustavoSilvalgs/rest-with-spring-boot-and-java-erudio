package br.com.erudio.services;

import br.com.erudio.DTO.v1.PersonDTO;
import br.com.erudio.controllers.PersonController;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;

import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");
        var persons =  DozerMapper.parseListObjects(repository.findAll(), PersonDTO.class);
        persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = DozerMapper.parseObject(entity, PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return dto;
    }

    public PersonDTO create(PersonDTO person) {
        if (person == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var dto = DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public PersonDTO update(PersonDTO person) {
        if (person == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Updating one person!");
        var entity = repository.findById(person.getKey()).orElseThrow(()-> new ResourceNotFoundException(""));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(""));

        repository.delete(entity);
    }
}
