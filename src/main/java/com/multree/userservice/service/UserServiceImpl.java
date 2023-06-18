package com.multree.userservice.service;

import com.multree.userservice.entity.RoleUser;
import com.multree.userservice.repository.UserRepository;
import com.multree.userservice.exception.UserAuthenticationConflictException;
import com.multree.userservice.exception.UserEmailNotValidException;
import com.multree.userservice.entity.UserRegistrationRequest;
import com.multree.userservice.entity.User;
import com.multree.userservice.response.UserRegistrationResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Value("${taken}")
    private static String TAKEN;

    @Value("${insert}")
    private static String INSERT_SUCCESS;

    @Autowired
    public UserServiceImpl(@Value("${taken}") String TAKEN, @Value("${insert}") String INSERT_SUCCESS, UserRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.TAKEN = TAKEN;
        this.INSERT_SUCCESS = INSERT_SUCCESS;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //private static final String INSERT_SUCCESS = "Data successfully stored in the database";

    private final UserRepository customerRepository;
    //private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public Map<String, Object> registerUser(UserRegistrationRequest request) {

        var user = User.builder()
                .organization_name(request.organization_name())
                .email(request.email())
                .role(Collections.singleton(RoleUser.USER))
                .active(true)
                .verify(false)
                .phone_number(request.phone_number())
                .password(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .build();

        //checking if customer email is valid
        String email = request.email();
        String organization_name = request.organization_name();
        String pass = request.password();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(email != null){
            Optional<User> byEmail = customerRepository.findByEmail(user.getEmail());

            if(byEmail.isPresent())
                throw new UserAuthenticationConflictException(TAKEN + email);

            if (pattern.matcher(email).matches()) {
                if(pass.contains(organization_name)){
                    throw new UserEmailNotValidException("password can't contain Organization name");
                }
                //todo: connect to the email service

                customerRepository.saveAndFlush(user);
            }
            else
                throw new UserEmailNotValidException("not a valid email format xxxxx@xx.xx");
        }

        //TODO: check if customer is stored in db
        //TODO: CHECK if customer is fraudster
         UserRegistrationResponse userRegistrationResponse = UserRegistrationResponse.builder()
                .organization_name(user.getOrganization_name())
                .email(user.getEmail())
                .role(user.getRole())
                .phone_number(user.getPhone_number())
                .build();

        Map<String, Object> map = new HashMap<>();
        map.put("message", INSERT_SUCCESS);
        map.put("results", userRegistrationResponse);
        return map;
    }



    @Override
    public List<User> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
//        Customer customer = new Customer();
        Optional<User> customerByEmail = customerRepository.findByEmail(email);
        return customerByEmail;
    }

    @Override
    public Optional<User> findById(UUID user_id) {
        return customerRepository.findById(user_id);
    }

    @Override
    public void deleteCourse(UUID customerId) {
        customerRepository.deleteById(customerId);
    }

    public User updateUser(User updateUser) {

        return customerRepository.save(updateUser);
    }

//    @Override
//    public void changeRole(String username, RoleUser newRole) {
//        customerRepository.updateRoleName(username, newRole);
//    }
}
