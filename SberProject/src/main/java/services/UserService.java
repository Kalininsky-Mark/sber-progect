package services;

import data_transfer.UserDataTransfer;
import tables.User;
import java.util.Optional;

public interface UserService {

    void save(UserDataTransfer userDataTransfer);

    Optional<User> findByUsername(String username);

}