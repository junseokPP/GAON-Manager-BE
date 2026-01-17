package com.back.gaon.domain.user.entity;

import com.back.gaon.common.baseEntity.BaseEntity;
import com.back.gaon.domain.user.enums.Role;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class User extends BaseEntity {

    String loginId;

    String password;

    String name;

    String phone;

    Role role;

}
