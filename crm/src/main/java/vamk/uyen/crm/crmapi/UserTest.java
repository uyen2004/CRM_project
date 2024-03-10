package vamk.uyen.crm.crmapi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserTest {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String phone;
}
