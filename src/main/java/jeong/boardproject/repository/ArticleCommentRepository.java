package jeong.boardproject.repository;

import jeong.boardproject.domain.ArticleComment;
import jeong.boardproject.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>, // QuerydslPredicateExecutor 은 Article 에 대한 기본 검색기능을 추가 시킴 ,but 부분검색이안됌
        QuerydslBinderCustomizer<QArticleComment> { // 그래서 이걸 추가 시킨다음에 아래 customize에 추가적인 검색 세부규칙을 해준다.

    List<ArticleComment> findByArticle_Id(Long articleId);
    void deleteByIdAndUserAccount_UserId(Long articleCommentId, String userId);

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        bindings.excludeUnlistedProperties(true);                             // 리스트에 따라 선택적으로 검색하고싶을때 적어놓음
        bindings.including(root.content, root.createdAt, root.createdBy);    // 원하는 필드를 추가시켜줌
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }

}