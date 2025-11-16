package com.d288.vdasi11.bootstrap;

import com.d288.vdasi11.dao.CustomerRepository;
import com.d288.vdasi11.dao.DivisionRepository;
import com.d288.vdasi11.entities.Customer;
import com.d288.vdasi11.entities.Division;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        long count = customerRepository.count();

        log.info(" Existing customer count: " + count);

        if (count <= 1) {  // Only create sample data if DB is mostly empty (1 = default seed maybe)

            Division division = divisionRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No division found."));

            Customer c1 = new Customer();
            c1.setFirstName("Alice");
            c1.setLastName("Smith");
            c1.setAddress("123 Main St");
            c1.setPhone("555-111-2222");
            c1.setPostalCode("11111");
            c1.setDivision(division);

            Customer c2 = new Customer();
            c2.setFirstName("Bob");
            c2.setLastName("Jones");
            c2.setAddress("456 Elm St");
            c2.setPhone("555-222-3333");
            c2.setPostalCode("22222");
            c2.setDivision(division);

            Customer c3 = new Customer();
            c3.setFirstName("Carol");
            c3.setLastName("Taylor");
            c3.setAddress("789 Oak St");
            c3.setPhone("555-333-4444");
            c3.setPostalCode("33333");
            c3.setDivision(division);

            Customer c4 = new Customer();
            c4.setFirstName("David");
            c4.setLastName("Lee");
            c4.setAddress("321 Pine St");
            c4.setPhone("555-444-5555");
            c4.setPostalCode("44444");
            c4.setDivision(division);

            Customer c5 = new Customer();
            c5.setFirstName("Emma");
            c5.setLastName("Wilson");
            c5.setAddress("654 Maple St");
            c5.setPhone("555-555-6666");
            c5.setPostalCode("55555");
            c5.setDivision(division);

            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
            customerRepository.save(c4);
            customerRepository.save(c5);

            log.info(" Sample customers added.");
        } else {
            log.info(" Skipping customer creation. Already present.");
        }
    }
}

