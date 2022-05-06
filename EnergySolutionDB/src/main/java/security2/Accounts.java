package security2;

import java.util.ArrayList;

import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Data : @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode 포함
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Accounts {
	private String UserId;
	private String Password;
	private String Email;
	private String Name;
    private ArrayList<GrantedAuthority> authorities;
    private UserRole userRole = UserRole.USER;
	
    
	
}
