package DealerStat.service;

import DealerStat.dto.MyUserDto;
import DealerStat.entity.MyUser;
import DealerStat.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {
    @Autowired
    MyUserRepository myUserRepository;

    public MyUser createPeople(MyUserDto myUserDto) {
        MyUser myUser = new MyUser();
        myUser.setFirstName(myUserDto.getFirstName());
        myUser.setLastName(myUserDto.getLastName());
        myUser.setPassword(myUserDto.getPassword());
        myUser.setEmail(myUserDto.getEmail());
        return myUserRepository.save(myUser);
    }
}
