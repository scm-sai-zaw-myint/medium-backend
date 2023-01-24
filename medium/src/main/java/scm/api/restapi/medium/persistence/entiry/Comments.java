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
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scm.api.restapi.medium.forms.CommentForm;

@Entity
@Table( name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    public Comments(CommentForm form) {
        this.id = form.getId();
        this.parentCommentId = form.getParentCommentId();
        this.body = form.getBody();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "parent_comment_id")
    private Integer parentCommentId;
    
    @Column(columnDefinition = "TEXT")
    private String body;
    
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    @OrderBy("updated_at DESC")
    private Posts post;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OrderBy("updated_at DESC")
    private Users user;
    
    @Column( name = "created_at")
    @CreationTimestamp
    private Date createdAt;
    
    @Column( name = "updated_at" )
    @UpdateTimestamp
    @OrderBy("DESC")
    private Date udpatedAt;
    
}
