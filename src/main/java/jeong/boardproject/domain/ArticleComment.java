package jeong.boardproject.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article; // 게시글 (ID)  ManyToOne 관계
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount; // 유저 정보 (ID)

    @Setter @Column(nullable = false, length = 500) private String content; // 본문


    protected ArticleComment() {} //하이버 네이트에 생성자 만듬

    private ArticleComment(Article article, UserAccount userAccount, String content) {
        this.article = article;
        this.userAccount = userAccount;
        this.content = content;
    }
    // 내가 필요한 생성자만 하나더 만듬
    // private로 막고 다른 클래스에서 사용못하게.

    public static ArticleComment of(Article article, UserAccount userAccount, String content) {
        return new ArticleComment(article, userAccount, content);
    }
    // 팩토리메서드로 다시 연다.
    // 더 큰 유연성과 제어력을 위해 private 생성자와 정적 팩토리 메서드를 함께 사용하는 방식을 선호합니다.
    // 이 메서드는 클래스 바깥에서도 ArticleComment 객체를 만들 수 있게 해줍니다.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}