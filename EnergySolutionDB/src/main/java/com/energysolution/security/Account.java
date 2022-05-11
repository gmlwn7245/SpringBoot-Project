package com.energysolution.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import security2.UserRole;


//@Data : @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode 포함
@Data
public class Account implements UserDetails{
	private Long no;
	
	private String UserId;
	private String Password;
	private String Email;
	private String Name;
	private String Role;
    private ArrayList<? extends GrantedAuthority> authorities;
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(authorities);
        return authList;
    }

	@Override
	public String getPassword() {
		return this.Password;
	}

	//ID로 체크하기 때문에 ID로 설정
	@Override
	public String getUsername() {
		return this.UserId;
	}
	
	public Long getNo() {
	    return no;
	}
	public void setNo(Long no) {
	    this.no = no;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public void setName(String Name) {
		this.Name=Name;
	}
	
	public String getRole() {
		return this.Role;
	}
	
	public void setRole(String Role) {
		this.Role=Role;
	}
	
	public String getEmail() {
		return this.Email;
	}
	
	public void setEmail(String Email) {
		this.Email=Email;
	}

	//계정 만료 여부 
	//true : 만료안됨
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있는지 
	//true : 잠기지 않음
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되었는지 -> 임시 비밀번호 발급시 설정 추가
	//true : 만료 안됨
	@Override
	public boolean isCredentialsNonExpired() {
		return  true;
	}

	//계정이 활성화 되었는지
	//true : 활성화됨
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
