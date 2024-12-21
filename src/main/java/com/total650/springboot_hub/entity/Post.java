package com.total650.springboot_hub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    ///What is FetchType.LAZY:
    //It means whenever we load Post Entity, then the Category object won't load immediately but have to use the getter by demand

    ///What is @OneToMany?
    // This is not a column for Post, but a note for the JPA to understand the relationship and to create column on Comment table
    //mappedBy được sử dụng để định nghĩa phía "bị quản lý" trong mối quan hệ hai chiều.
    //Nó chỉ ra rằng phía này không sở hữu khóa ngoại trong cơ sở dữ liệu, mà chỉ là đại diện của quan hệ trong Java.
    //Giá trị của mappedBy (trong trường hợp này là "post") phải khớp với tên thuộc tính trong entity đối tác (Comment), nơi định nghĩa mối quan hệ liên kết này.
    //Ở entity Comment, chúng ta phải khai báo mối quan hệ ngược lại bằng @ManyToOne và chỉ định tên của khóa ngoại (post)
    //Vậy JPA hiểu rằng post là trường trong Comment đang tham chiếu đến Post
    // cascadeType.ALL means if the post get deleted then all the comments link to the post also get deleted

    ///Kinh nghiệm thương đau tại sao ko dùng @Data ở Entity Class:
    //Because we use ModalMapper library to convert back and forth Post and PostDTO (entity <-> dto)
    //ModelMapper internally uses toString() method to print the result for mapping
    //Post and Comment entity are having BIDIRECTIONAL relationship (@OneToMany và @ManyToOne)
    //When ModelMapper use Post.toString() and Comment.toString() there will be an infinity loop between Post and Comment (Lỗi StackOverFlowError được in ra)
    //Because: đang có @OneToMany và @ManyToOne, 2 entity class này tham chiếu lẫn nhau. Khi gọi Post.toString() thì chính method đó lại gọi Comments.toString() và ngược lại...
    //Therefor we don't use @Data on Post because @Data will generate toString()

    ///Tại sao Post.toString() gọi Comment.toString() ?
    // Cách mà .toString() hoạt động là nó
    // Return "Post(id=" + id + ", title=" + title + ", description=" + description  + ", content=" + content + ", comments=" + comments + ")";
    // Tức là trong .toString() lại gọi comments mà comments đang là SET, nên nó lại cần phải gọi .toString() của comments và cứ thế lại hồi quy...

    ///Bài học đối với một QC Engineer:
    // Hãy nghi ngờ @Data hay các annotation mà generate thừa field, đặc biệt trong các Class Entity hay trỏ tới nhau
    // Defect nằm ở chỗ @Data, nhưng nếu ko nhờ ModelMapper để trigger ra Failure thì sẽ chả phát hiện được Defect này
}
