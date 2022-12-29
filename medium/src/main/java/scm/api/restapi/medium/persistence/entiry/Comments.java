package scm.api.restapi.medium.persistence.entiry;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "parent_comment_id")
    private Integer parentCommentId;
    
    @Column(columnDefinition = "TEXT")
    private String body;
    
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Posts post;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    
    @Column( name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    
    @Column( name = "updated_at" )
    @UpdateTimestamp
    private Date udpatedAt;
    
}
