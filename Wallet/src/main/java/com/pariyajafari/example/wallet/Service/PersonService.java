package com.pariyajafari.example.wallet.Service;

import com.pariyajafari.example.wallet.Model.Gender;
import com.pariyajafari.example.wallet.Model.Person;
import com.pariyajafari.example.wallet.Repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    //method to create person in person repository
    public Person createPerson(Person person) {
        if (person.getDateOfBirth().isBefore(LocalDate.now().minusYears(18)) && person.getGender() == Gender.MALE) {
            if (person.getMilitaryStatus() == null || person.getMilitaryStatus().isEmpty()) {
                throw new IllegalArgumentException("Military status is required for males over 18 years old.");
            }
        }
        return personRepository.save(person);
    }
    //method to get all persons from person repository
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }
    //method to get one person by id from person repository
    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
    }
    //method to update person in person repository
    public Person updatePerson(Long id, Person updatedPerson) {
        Person existingPerson = getPersonById(id);
        existingPerson.setFirstName(updatedPerson.getFirstName());
        existingPerson.setLastName(updatedPerson.getLastName());
        existingPerson.setMobileNumber(updatedPerson.getMobileNumber());
        existingPerson.setNationalId(updatedPerson.getNationalId());
        existingPerson.setEmail(updatedPerson.getEmail());
        existingPerson.setDateOfBirth(updatedPerson.getDateOfBirth());
        existingPerson.setGender(updatedPerson.getGender());
        existingPerson.setMilitaryStatus(updatedPerson.getMilitaryStatus());
        return personRepository.save(existingPerson);
    }
}

