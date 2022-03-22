package com.github.loginservice.securityManagement;
import com.github.loginservice.business.UserBusiness;
import com.github.loginservice.models.Role;
import com.github.loginservice.models.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserBusiness userBusiness;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDb userDb=userBusiness.findOneByUserName(username);
        Role role = userDb.getRoleUser();
        if(userDb==null) throw new UsernameNotFoundException("invalid user");
        Collection<GrantedAuthority> authorities=new ArrayList<>();
            role.getRole().forEach(r->{
            authorities.add(new SimpleGrantedAuthority("ROLE_" + r));
        });
        return new User(userDb.getUserName(),userDb.getPassword(),authorities);
    }
}
