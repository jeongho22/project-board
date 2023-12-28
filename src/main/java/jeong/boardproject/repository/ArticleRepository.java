package jeong.boardproject.repository;

import jeong.boardproject.domain.Article;
import jeong.boardproject.domain.QArticle;
import jeong.boardproject.repository.querydsl.ArticleRepositoryCustom;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        ArticleRepositoryCustom,
        QuerydslPredicateExecutor<Article>, // QuerydslPredicateExecutor 은 Article 에 대한 기본 검색기능을 추가 시킴 ,but 부분검색이안됌
        QuerydslBinderCustomizer<QArticle> { // 그래서 이걸 추가 시킨다음에 아래 customize에 추가적인 검색 세부규칙을 해준다.

    Page<Article> findByTitleContaining(String title, Pageable pageable);
    Page<Article> findByContentContaining(String content, Pageable pageable);
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);
    Page<Article> findByHashtag(String hashtag, Pageable pageable);

    void deleteByIdAndUserAccount_UserId(Long articleId, String userid);

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true);                               // 리스트에 따라 선택적으로 검색하고싶을때 적어놓음
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 원하는 필드를 추가시켜줌
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 문자열 관련 IgnoreCase을써서 대소문자 구분 x
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 문자열 관련 IgnoreCase을써서 대소문자 구분 x
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase); // 문자열 관련 IgnoreCase을써서 대소문자 구분 x
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);            // 날짜는 eq 로 동일검사해야줘야함... 대소문자가아님
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // 작성자 대소문자 x
    }
    // 세부적인 규칙들이다
}