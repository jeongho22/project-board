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
@EntityListeners(AuditingEntityListener.class)          // 엔티티의 생성 및 수정 시간을 자동으로 관리해주는 역할을 합니다.
@MappedSuperclass                                       // 여러 엔티티들이 공통으로 사용하는 필드나 메서드를 정의할 때 사용됩니다.
public abstract class AuditingFields {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // 파싱에 대한 룰
    @CreatedDate                                        // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false, updatable = false)        // nullable는 true가 기본값인데 null값 금지, 업데이트 불가능
    private LocalDateTime createdAt;

    @CreatedBy                                                  // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false, updatable = false, length = 100)  // nullable는 true가 기본값인데 null값 금지 ,업데이트 불가능
    private String createdBy;                                   // 생성자 (누가만들었는지는 jpaconfig에서 인증권한을 거침)

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) // 파싱에 대한 룰
    @LastModifiedDate                                   // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false)                           // nullable는 true가 기본값인데 null값 금지
    private LocalDateTime modifiedAt;

    @LastModifiedBy                                     // jpa 를 통해 자동으로 작성되어짐
    @Column(nullable = false, length = 100)             // nullable는 true가 기본값인데 null값 금지
    private String modifiedBy;

}