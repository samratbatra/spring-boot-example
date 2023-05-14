package com.burnercode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1")
public class Main {


    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/")
    public String greet() {
        return "Hello";
    }

    @GetMapping("/greet")
    public GreetResponse greetResponse() {
        return new GreetResponse("Hello", List.of("C","C++","Java"),new Person("Samrat", 28, 30000));
    }

    record GreetResponse(
            String greet,
            List<String> favProgrammingLanguages,
            Person person) {
    };
    record Person(String name, int age, double savings) {
    };

    @GetMapping("/customers")
    public List<Customer> getCustomers(){
        return  customerRepository.findAll();
    }

    @PostMapping("/customers")
    public void addCustomer(@RequestBody NewCustomerRequest newCustomerRequest){

        Customer customer = new Customer();
        customer.setAge(newCustomerRequest.age());
        customer.setName(newCustomerRequest.name());
        customer.setEmail(newCustomerRequest.email());

        customerRepository.save(customer);
    }

    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("/customers")
    public void updateCustomer(@RequestBody NewCustomerUpdateRequest newCustomerUpdateRequest){
        Customer customer = new Customer();
        customer.setAge(newCustomerUpdateRequest.age());
        customer.setName(newCustomerUpdateRequest.name());
        customer.setEmail(newCustomerUpdateRequest.email());
        customerRepository.save(customer);
    }

    record NewCustomerRequest(String name, String email, Integer age ){};

    record NewCustomerUpdateRequest(Integer id ,String name, String email, Integer age ){};
}


