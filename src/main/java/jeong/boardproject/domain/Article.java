package jeong.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList="title"),
        @Index(columnList="hashtag"),
        @Index(columnList="createAt"),
        @Index(columnList="createBy")
})


@Entity
public class Article extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String title;    // 제목
    @Setter @Column(nullable = false , length = 10000) private String content;  // 내용

    @Setter private String hashtag;  // 해시태그


    // Collection 중복 허용 안함
    @ToString.Exclude // 순환참조 끊기위해, ArtcleComment 도 계속참조하고 서로 참조함
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    protected Article() {}
    private Article(String title, String content, String hashtag){
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of (String title,String content, String hashtag){
        return new Article(title,content,hashtag);
    }


    //동등성 검사... 로직
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false; // 패턴 variable
        return id != null && id.equals(article.id);        // id빼고는 제목, 내용, 전부다 같더라도 다른취급하겠다.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

