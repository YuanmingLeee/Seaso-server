package com.seaso.seaso.modules.sys.dao;

import com.seaso.seaso.modules.sys.entity.Role;
import com.seaso.seaso.modules.sys.utils.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByRoleTypeIn(List<RoleType> roleTypes);
}
