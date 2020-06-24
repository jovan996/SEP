package rs.grgur.jovan.optika.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "persons")
public class Person implements Serializable {

    @Id
    @Column(name = "jmbg", unique = true)
    @NotNull
    private String jmbg;

    @Column(name = "name")
    @NotNull
    @Size(max = 45)
    private String name;

    @Column(name = "surname")
    @NotNull
    @Size(max = 45)
    private String surname;

    @Column(name = "address")
    @Size(max = 100)
    private String address;

    @Column(name = "email")
    @Size(max = 45)
    private String email;

    @Column(name = "phone")
    @Size(max = 45)
    private String phone;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "persons_jmbg")
    @JsonIgnore
    private Set<Account> accounts;

    public Person() {
    }

    public Person(Person person) {
        this.jmbg = person.jmbg;
        this.name = person.name;
        this.surname = person.surname;
        this.address = person.address;
        this.email = person.email;
        this.phone = person.phone;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @JsonIgnore
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "Person{" + "jmbg=" + jmbg + ", name=" + name + ", surname=" + surname + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.jmbg);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.jmbg, other.jmbg)) {
            return false;
        }
        return true;
    }
}
