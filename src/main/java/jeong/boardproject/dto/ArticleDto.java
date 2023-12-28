package jeong.boardproject.dto;

import jeong.boardproject.domain.Article;
import jeong.boardproject.domain.UserAccount;

import java.time.LocalDateTime;

// DTO 사용 이유:
// DTO를 사용하면 불필요하거나 민감한 데이터를 노출하지 않고 필요한 데이터만 클라이언트에 전달할 수 있습니다. 예를 들어, 사용자의 비밀번호나 개인 정보는 DTO를 통해서는 전송되지 않을 수 있습니다.
// 도메인 로직에 영향을 주지 않고 데이터 전송 형식을 변경할 수 있습니다. 이는 유지 관리를 용이하게 합니다.
// 불변 객체 만들때는 record 를 사용
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, String hashtag) {
        return new ArticleDto(null, userAccountDto, title, content, hashtag, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content,
                hashtag
        );
    }

}