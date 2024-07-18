package data_transfer;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDataTransfer {

    private long id;

    @NotEmpty(message = "Username should not be empty.")
    private String username;

    @NotEmpty(message = "Password should not be empty.")
    private String password;

}
