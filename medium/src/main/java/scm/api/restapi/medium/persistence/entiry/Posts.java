package scm.api.restapi.medium.persistence.entiry;

import java.util.Date;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Users user;
    
    @Column
    private String image;
    
    @ManyToMany
    @JoinTable(
            name = "Post_Categories",
            joinColumns = @JoinColumn(name="post_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
            )
    Set<Categories> categories;
    
    @OneToMany(mappedBy = "post")
    Set<Comments> comments;
    
    @Column(length = 30)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column( name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    
    @Column( name = "updated_at" )
    @UpdateTimestamp
    private Date udpatedAt;
    
    @Column(name = "deleted_at")
    private Date deletedAt;
}
