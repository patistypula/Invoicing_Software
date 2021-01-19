package pl.invoicingsoftware.invoice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(){
        long id = 1;
        return userRepository.getUserById(id);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

}
