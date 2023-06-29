package br.com.erudio.services;

import br.com.erudio.DTO.v1.PersonDTO;
import br.com.erudio.DTO.v2.PersonDTOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;

import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper mapper;

    public List<PersonDTO> findAll() {
        logger.info("Finding all people!");
        return DozerMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one person!");
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return DozerMapper.parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person) {
        logger.info("Creating one person!");
        var entity = DozerMapper.parseObject(person, Person.class);
        var dto = DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
        return dto;
    }

    public PersonDTOV2 createV2(PersonDTOV2 person) {
        logger.info("Creating one person with v2!");
        var entity = mapper.convertDtoToEntity(person);
        var dto = mapper.convertEntityToDto(repository.save(entity));
        return dto;
    }

    public PersonDTO update(PersonDTO person) {
        logger.info("Updating one person!");
        var entity = repository.findById(person.getId()).orElseThrow(()-> new ResourceNotFoundException(""));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = DozerMapper.parseObject(repository.save(entity), PersonDTO.class);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting one person!");

        var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(""));

        repository.delete(entity);
    }
}
