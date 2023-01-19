package scm.api.restapi.medium.persistence.entiry;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import scm.api.restapi.medium.forms.PostForm;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column
    private String image;

    @ManyToMany
    @JoinTable(name = "Post_Categories", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Categories> categories;

    @OneToMany(mappedBy = "post")
    Set<Comments> comments;

    @Column(length = 130)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @OrderBy("DESC")
    private Date udpatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    public Posts(PostForm form) {
        this.title = form.getTitle();
        this.description = form.getDescription();
        this.image = form.getImageURL();
    }

    public Set<Comments> getComments() {
        return this.comments.stream().filter(com -> com.getParentCommentId() == null)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
