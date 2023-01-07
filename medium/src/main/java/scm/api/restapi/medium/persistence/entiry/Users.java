package scm.api.restapi.medium.persistence.entiry;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Data;
import scm.api.restapi.medium.forms.UserForm;

@Entity
@Table(name = "users")
@Data
public class Users implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = -7216551695597215352L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100)
    private String bio;

    @Column(nullable = true, length = 100)
    private String profile;
    
    @OneToMany(mappedBy = "user")
    private Set<Posts> posts;
    
    @OneToMany(mappedBy = "user")
    private Set<Comments> comments;
    
    @Column(length = 100)
    private String password;
    
    @Column(length = 100)
    private String ip;
    
    @Column(name = "created_at")
    @CreationTimestamp
    
    private Date createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    @OrderBy("DESC")
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return true;
    }

    public Users() {
        super();
    }

    public Users(UserForm form) {
        this.username = form.getName();
        this.email = form.getEmail();
        this.bio = form.getBio();
        this.profile = form.getProfileURL();
        this.password = form.getPassword();
    }

    public void addPost(Posts post) {
        this.posts.add(post);
    }
    
}
