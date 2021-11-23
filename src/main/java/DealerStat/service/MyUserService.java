package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.entity.Role;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService {

    private final MyUserRepository myUserRepository;


    private final PasswordEncoder passwordEncoder;

    public MyUser createUser(MyUserDto myUserDto) {
        MyUser myUser = new MyUser();
        myUser.setFirstName(myUserDto.getFirstName());
        myUser.setLastName(myUserDto.getLastName());
        myUser.setPassword(passwordEncoder.encode(myUserDto.getPassword()));
        myUser.setEmail(myUserDto.getEmail());
        myUser.setRoles(Collections.singleton(Role.USER));
        return myUserRepository.save(myUser);
    }

    public MyUser approveUser(Long userId) {
        MyUser myUser = myUserRepository.findMyUserById(userId);
        myUser.setApproved(true);
        return myUserRepository.save(myUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser userFindByEmail = myUserRepository.findMyUserByEmail(email);

        if (userFindByEmail != null) {
            return userFindByEmail;
        }
        return null;
    }
}
