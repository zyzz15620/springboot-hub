package com.total650.springboot_hub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
    // This is not a column, but a note for the database to understand the relationship
    /// mappedBy được sử dụng để định nghĩa phía "bị quản lý" trong mối quan hệ hai chiều.
    /// Nó chỉ ra rằng phía này không sở hữu khóa ngoại trong cơ sở dữ liệu, mà chỉ là đại diện của quan hệ trong Java.
    /// Giá trị của mappedBy (trong trường hợp này là "post") phải khớp với tên thuộc tính trong entity đối tác (Comment), nơi định nghĩa mối quan hệ liên kết này.
    /// Ở entity Comment, chúng ta phải khai báo mối quan hệ ngược lại bằng @ManyToOne và chỉ định tên của khóa ngoại (post)
    /// Vậy JPA hiểu rằng post là trường trong Comment đang tham chiếu đến Post
    // cascadeType.ALL means if the post get deleted then all the comments link to the post also get deleted
}
