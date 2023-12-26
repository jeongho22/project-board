package jeong.boardproject.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter

//가져오기(getter)' 함수를 자동으로 만들어줍니다. 예를 들어, 클래스에 private int age; 필드가 있다면,
// @Getter 어노테이션은 자동으로 public int getAge()라는 함수를 생성하는역할

@ToString(callSuper = true)

// 클래스의 정보를 문자열 형태로 만들어주는 toString() 메소드를 자동으로 생성
// Article 클래스가 id과 title 필드를 가지고 있고, @ToString 어노테이션을 사용하면,
// Article 객체를 출력할 때 "Person(id=30, title=john)"과 같은 형태의 문자열이 생성

@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
// jpa 표준 어노테이션
//@Index(columnList = "title")은 name 열을 기준으로 인덱스를 만듭니다. 이렇게 하면 title을 사용하는 검색 쿼리가 더 빨라집니다.

@Entity

// 데이터 베이스랑 연동하는 어노테이션
// 클래스의 각 객체는 데이터베이스 테이블의 한 행(row)과 일치하게 됩니다. 클래스의 필드는 테이블의 열(column)과 대응
public class Article extends AuditingFields {

    @Id                                                 // 기본키 걸어주고
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동으로 1,2,3,.... 번호 매겨주는 어노테이션
    private Long id;

    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;
    @Setter @Column(nullable = false) private String title;                     // nullable는 true가 기본값인데 null값 금지
    @Setter @Column(nullable = false, length = 10000) private String content;   // nullable는 true가 기본값인데 null값 금지
    @Setter private String hashtag;

    //@Setter는 내가 임의적으로 수정할수있게 만들어주는 어노테이션이다.
    // 하지만 위에 4개만 해놓는건 나머지 id나 creat,modify 이런건 자동으로 jpa가 세팅하도록 만들어주고싶기때문에 나머지에는안씀.

    @ToString.Exclude
    // artcle쪽에서 tostring이 있고 comment 쪽에도 tostring이 있어서 무한히 반복하는 순환참조가 생길수있기 때문에 끊어줘야함.
    // 안쓰면 계속 읽음... 무한하게

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)

    // comment쪽 article 맵퍼해줌
    // 게시글이 삭제되면 다 삭제가 되어야해서 cascade 옵션추가

    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    //이 코드 줄은 ArticleComment 객체들을 저장하기 위한 Set 컬렉션을 선언합니다.
    // LinkedHashSet을 사용함으로써 댓글들이 입력된 순서대로 저장되고 유지됩니다.


    protected Article() {}
    // 하이버네이트를 사용할경우 기본생성자 하나가 필요.
    //hibernate는 Java에서 사용되는 인기 있는 ORM (Object-Relational Mapping) 프레임워크

    // 하이버 네이트에서 이런식으로 만들어줌
    //   create table article (
    //       id bigint not null auto_increment,
    //        created_at datetime(6) not null,
    //        created_by varchar(100) not null,
    //        modified_at datetime(6) not null,
    //        modified_by varchar(100) not null,
    //        content varchar(10000) not null,
    //        hashtag varchar(255),
    //        title varchar(255) not null,
    //        user_id varchar(50) not null,
    //        primary key (id)
    //    ) engine=InnoDB



    private Article(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // Article 클래스의 객체는 private 때문에 클래스 외부에서 직접 생성될 수 없습니다.
    // 메타 데이터 빼고 생성자 하나더 생성... 메타데이터는 자동으로 생성되는 데이터들임...

    public static Article of(UserAccount userAccount, String title, String content, String hashtag) {
        return new Article(userAccount, title, content, hashtag);
    }

    // of 메서드가 없다면, Article 객체를 생성할 때 new Article(userAccount, title, content, hashtag)와
    // 같이 직접 생성자를 호출해야 합니다.
    // 하지만 이 코드에서 Article의 생성자는 private으로 설정되어 있기 때문에,
    // 클래스 외부에서는 접근할 수 없습니다. 즉, of 메서드가 없으면 다른 클래스에서 Article 객체를 만들 수 없게 됩니다.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // 1. 자기 자신과 비교 같으면 true 반환

        if (!(o instanceof Article that)) return false;

        //2. 타입체크 Article 타입이 아니라면,
        // 즉 Article 클래스의 인스턴스가 아니라면,
        // false를 반환하여 두 객체가 다르다고 판단

        return id != null && id.equals(that.getId());

        //3.먼저 현재 객체의 id가 null이 아닌지 확인하고,
        // 그 다음 현재 객체의 id와 전달된 객체 o의 id를 비교합니다.
        // 두 id가 같다면 true를 반환하여 두 객체가 같다고 판단하고, 다르면 false를 반환합니다.
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}