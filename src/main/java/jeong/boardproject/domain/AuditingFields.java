package jeong.boardproject.domain;



import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingFields {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate                                        // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false, updatable = false)        // nullable는 true가 기본값인데 null값 금지
    private LocalDateTime createdAt;

    @CreatedBy                                                  // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false, updatable = false, length = 100)  // nullable는 true가 기본값인데 null값 금지
    private String createdBy;                                   // 생성자 (누가만들었는지는 jpaconfig에서 인증권한을 거침)

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate                                   // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false)                           // nullable는 true가 기본값인데 null값 금지
    private LocalDateTime modifiedAt;

    @LastModifiedBy                                     // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false, length = 100)             // nullable는 true가 기본값인데 null값 금지
    private String modifiedBy;

}