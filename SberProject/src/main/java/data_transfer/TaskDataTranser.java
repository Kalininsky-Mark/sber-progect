package data_transfer;

import elements.Categorys;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDataTranser {

    private long id;

    @NotEmpty(message = "Name must not be empty.")
    private String name;

    @NotNull(message = "Description must not be null.")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date expirationDate;

    private Categorys categorys = Categorys.Uncategorized;

    private boolean completed = false;

}
