package jeong.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@Table(indexes = {

        @Index(columnList="content"),
        @Index(columnList="createAt"),
        @Index(columnList="createBy")
})

@Entity
public class ArticleComment extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Getter를 사용하면 content,article 를 밖에서 가져갈 수 있는 문이 자동생김 = 정보가져다주는역할
    // @Setter를 사용하면 content,article 를 원하는대로 바꿀수있는 문이 자동으로 생김  = 정보를 수정하는역할
    @Setter
    @ManyToOne(optional = false)
    private Article article;

    @Setter
    @Column(nullable = false, length = 500)
    private String content;

    protected ArticleComment() {}

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of (Article article, String content) {
        return new ArticleComment(article,content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
