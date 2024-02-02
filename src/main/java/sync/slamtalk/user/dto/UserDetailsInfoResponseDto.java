package sync.slamtalk.user.dto;

import lombok.*;
import sync.slamtalk.user.entity.SocialType;
import sync.slamtalk.user.entity.User;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class UserDetailsInfoResponseDto {
    /* 개인 정보 관련 */
    private String email;
    private SocialType socialType;

    /* 공개되어도 상관없는 부분 */
    private Long id;
    private String nickname;
    private String imageUrl;

    /* 마이페이지 기능 */
    private String selfIntroduction;

    /* 정보 수집 부분 */
    private String basketballSkillLevel;
    private String basketballPosition;
    private Long level = 0L;
    private Long levelScore = 0L;
    private Long mateCompleteParticipationCount = 0L;
    private Long teamMatchingCompleteParticipationCount = 0L;


    /**
     * 나의 프로필 조회 시 필요한 정보를 반환하는 생성자
     *
     * @param user db에서 조회한 user 객체
     * @param mateCompleteParticipationCount 메이트 참여완료 횟수
     * @return UserDetailsInfoResponseDto 개인정보 포함된 정보
     */
    public static UserDetailsInfoResponseDto generateMyProfile(
            User user,
            long levelScore,
            long mateCompleteParticipationCount
    ){ return UserDetailsInfoResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .selfIntroduction(user.getSelfIntroduction())
                .basketballSkillLevel( user.getBasketballSkillLevel() == null? null: user.getBasketballSkillLevel().getLevel())
                .basketballPosition(user.getBasketballPosition() == null ?null:user.getBasketballPosition().getPosition())
                .level(levelScore/User.LEVEL_THRESHOLD)
                .levelScore(levelScore)
                .mateCompleteParticipationCount(mateCompleteParticipationCount)
                .teamMatchingCompleteParticipationCount(0L)
                .email(user.getEmail())
                .socialType(user.getSocialType())
                .build();

    }

    /**
     * 상대방 프로필 조회 시 필요한 정보를 반환하는 생성자
     *
     * @param user                           db에서 조회한 user 객체
     * @param mateCompleteParticipationCount
     * @return UserDetailsInfoResponseDto 개인정보 제외된 정보
     */
    public static UserDetailsInfoResponseDto generateOtherUserProfile(
            User user,
            long levelScore,
            long mateCompleteParticipationCount
    ) { return UserDetailsInfoResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .selfIntroduction(user.getSelfIntroduction())
                .basketballSkillLevel( user.getBasketballSkillLevel() == null? null: user.getBasketballSkillLevel().getLevel())
                .basketballPosition(user.getBasketballPosition() == null ?null:user.getBasketballPosition().getPosition())
                .levelScore(levelScore)
                .level(levelScore / User.LEVEL_THRESHOLD)
                .mateCompleteParticipationCount(mateCompleteParticipationCount)
                .teamMatchingCompleteParticipationCount(0L)
                .build();

    }
}
