package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;

    public MyUser createUser(MyUserDto myUserDto) {
        MyUser myUser = new MyUser();
        myUser.setFirstName(myUserDto.getFirstName());
        myUser.setLastName(myUserDto.getLastName());
        myUser.setPassword(myUserDto.getPassword());
        myUser.setEmail(myUserDto.getEmail());
        return myUserRepository.save(myUser);
    }

    public MyUser approveUser(Long userId) {
        MyUser myUser = myUserRepository.findMyUserById(userId);
        myUser.setApproved(true);
        return myUserRepository.save(myUser);
    }
}
