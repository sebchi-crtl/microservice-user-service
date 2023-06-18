package com.multree.userservice.controller;

import com.multree.userservice.exception.UserEmailNotValidException;
import com.multree.userservice.entity.User;
import com.multree.userservice.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl customerService;

    private final PasswordEncoder passwordEncoder;


    @DeleteMapping("{customerId}")//api/user/{userId}
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID customerId)
    {
        try{
            customerService.deleteCourse(customerId);
        }
        catch (Exception rse){
            throw new UserEmailNotValidException("user with this id = " + customerId + " not found", rse);
        }
        return new ResponseEntity<>("deletion successful with id = " + customerId,HttpStatus.OK);
    }

    @GetMapping
    public List<User> viewAllCustomer(){

        List<User> allUsers = customerService.findAllCustomers();

        log.info("view all users registered {}", allUsers);

        if(!allUsers.isEmpty()){
            return allUsers;
        }else{
            throw new UserEmailNotValidException("no user stored in database");
        }
    }

    @PutMapping("{planId}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody User user)
    {
        User updateUser = customerService.findById(userId)
                .orElseThrow(() -> new UserEmailNotValidException("User not exist with id: " + userId));

        updateUser.setOrganization_name(user.getOrganization_name());
        updateUser.setPhone_number(user.getPhone_number());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.setUpdatedAt(LocalDateTime.now());
        customerService.updateUser(updateUser);

        return ResponseEntity.ok(updateUser);
    }


//    @PutMapping("change/{role}")
//    public ResponseEntity<?> changeRole(
//            @AuthenticationPrincipal UserPrincipal userPrincipal,
//            @PathVariable RoleUser role)
//    {
//        customerService.changeRole(userPrincipal.getUsername(), role);
//
//
//        return ResponseEntity.ok(true);
//    }


    @GetMapping("{customerId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID user_id)
    {
        log.info("view user id registered {}", user_id);
        System.out.println("user phId = " + user_id + " not valid");
        ResponseEntity ok = ResponseEntity.ok(customerService.findById(user_id)
                .orElseThrow(() -> new UserEmailNotValidException("Customer with " + user_id + " as an id does not exists")));
        return ok;


    }


//    private static final List<Customer> CUSTOMER = Arrays.asList(
//        new Customer(
//                1,
//                "Amina",
//                "Farida",
//                "rina@playnetz.com",
//                List.of(RoleUser.USER),
//                "+2349044294541",
//                "06, buhari elena kano",
//                "vxvxxxuxjbdjjcjcbcvxvxvx",
//                LocalDateTime.now()
//        ),
//        new Customer(
//                2,
//                "James",
//                "Smith",
//                "jath@playnetz.com",
//                List.of(RoleUser.ADMIN),
//                "+2348187654336",
//                "14, erickson london",
//                "xxcxcchgjfycyhxcxcxc",
//                LocalDateTime.now()
//        ),
//        new Customer(
//            3,
//            "Jones",
//            "Nwobi",
//            "onbi@playnetz.com",
//            List.of(RoleUser.PRODUCER),
//            "+2348058654584",
//            "92, ossify midline abuja",
//            "weejrsnxbgufoiu",
//            LocalDateTime.now()
//        ),
//        new Customer(
//                4,
//                "Okafor",
//                "Nwokedi",
//                "Okadi@playnetz.com",
//                List.of(RoleUser.ADMIN),
//                "+2347043943564",
//                "107, lemondry elena lagos",
//                "gjejrsnxbejcefgn",
//                LocalDateTime.now()
//        )
//    );


//    @GetMapping(value = "fake_user/{id}")
//    public Customer findOne(@PathVariable Integer id) {
//        return CUSTOMER.stream()
//                .filter(customer -> id.equals(customer.getId()))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
//    }
}
