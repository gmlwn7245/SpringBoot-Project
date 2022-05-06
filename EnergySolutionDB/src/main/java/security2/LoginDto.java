package security2;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
	@JsonProperty(value="UserId")
	private String UserId;
	
	@JsonProperty(value="Password")
	private String Password;
}
