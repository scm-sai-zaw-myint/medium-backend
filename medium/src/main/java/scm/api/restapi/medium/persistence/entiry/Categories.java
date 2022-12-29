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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(length = 50)
    private String name;
    
    @ManyToMany(mappedBy = "categories")
    Set<Posts> posts;
    
    @Column( name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    
    @Column( name = "updated_at" )
    @UpdateTimestamp
    private Date udpatedAt;
    
}
