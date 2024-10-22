package com.pariyajafari.example.wallet.Repository;

import com.pariyajafari.example.wallet.Model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByMobileNumber(String mobileNumber);
    Optional<Person> findByEmail(String email);
}