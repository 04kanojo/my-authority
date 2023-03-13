package com.kanojo.domain;

import com.kanojo.module.Admin;
import com.kanojo.module.Resource;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * SpringSecurity需要的用户详情
 */
@Data
public class AdminDetails implements UserDetails {
    private Admin admin;

    /**
     * 资源集合
     */
    private List<Resource> resourceList;

    public AdminDetails(Admin Admin, List<Resource> resourceList) {
        this.admin = Admin;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
//        return resourceList.stream().map(resource -> new SimpleGrantedAuthority(resource.getId(), resource.getName())).collect(Collectors.toList());
        return null;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getStatus().equals(1);
    }
}
