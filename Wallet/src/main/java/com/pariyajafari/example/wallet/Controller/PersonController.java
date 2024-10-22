package com.pariyajafari.example.wallet.Controller;

import com.pariyajafari.example.wallet.DTO.PersonDTO;
import com.pariyajafari.example.wallet.Model.Person;
import com.pariyajafari.example.wallet.Service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    //create person request
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@Valid @RequestBody PersonDTO personDTO) {
        //person dto to person entity conversion
        Person person = convertToEntity(personDTO);
        Person savedPerson = personService.createPerson(person);

        //person entity to person dto conversion
        PersonDTO savedPersonDTO = convertToDTO(savedPerson);
        return ResponseEntity.ok(savedPersonDTO);
    }
    //get all persons request
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        List<PersonDTO> personDTOs = persons.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(personDTOs);
    }
    //get person by person id request
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        Person person = personService.getPersonById(id);
        PersonDTO personDTO = convertToDTO(person);
        return ResponseEntity.ok(personDTO);
    }
    //update person request by id
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTO) {
        Person person = convertToEntity(personDTO);
        Person updatedPerson = personService.updatePerson(id, person);
        PersonDTO updatedPersonDTO = convertToDTO(updatedPerson);
        return ResponseEntity.ok(updatedPersonDTO);
    }

    //method for convert person entity to person dto
    private Person convertToEntity(PersonDTO dto) {
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setMobileNumber(dto.getMobileNumber());
        person.setNationalId(dto.getNationalId());
        person.setEmail(dto.getEmail());
        person.setDateOfBirth(dto.getDateOfBirth());
        person.setGender(dto.getGender());
        person.setMilitaryStatus(dto.getMilitaryStatus());
        return person;
    }
    //method for convert person dto to person entity
    private PersonDTO convertToDTO(Person person) {
        PersonDTO dto = new PersonDTO();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setMobileNumber(person.getMobileNumber());
        dto.setNationalId(person.getNationalId());
        dto.setEmail(person.getEmail());
        dto.setDateOfBirth(person.getDateOfBirth());
        dto.setGender(person.getGender());
        dto.setMilitaryStatus(person.getMilitaryStatus());
        return dto;
    }
}


