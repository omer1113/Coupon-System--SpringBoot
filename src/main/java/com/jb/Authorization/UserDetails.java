package com.jb.Authorization;


import com.jb.Config.LoginManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {

    private LoginManager.ClientType clientType;
    private String email;
    private String password;


    public UserDetails(String email, LoginManager.ClientType clientType) {
        this.email = email;
        this.clientType = clientType;
    }
    /*
    UserDetails supportUser = User.builder()
            .username("support")
            .password(PASSWORD_ENCODER.encode("12345"))
            .roles(ApplicationUserRole.SUPPORT.name())
            .build();
    return new InMemoryUserDetailsManager(omerUser,client1,supportUser);

 */

}




















