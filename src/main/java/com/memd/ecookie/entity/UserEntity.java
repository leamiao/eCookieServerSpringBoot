package com.memd.ecookie.entity;

import java.util.Objects;

import javax.persistence.Entity;

@Entity (name = "users") // This tells Hibernate to make a table out of this class
public class UserEntity extends BaseAuditEntity{
    private static final long serialVersionUID = 8211741030488027929L;

    private String name;

    private String email;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }
    
	@Override
	public int hashCode() {
		return Objects.hash(this.name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null){
			return false;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		UserEntity other = (UserEntity) obj;
		return Objects.equals(this.name, other.name);
	}}