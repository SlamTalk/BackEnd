package sync.slamtalk.team.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sync.slamtalk.mate.entity.RecruitmentStatusType;
import sync.slamtalk.team.entity.TeamMatching;
import sync.slamtalk.user.entity.User;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TeamMatchingRepository extends JpaRepository<TeamMatching, Long> {

    // 팀 매칭 목록 조회를 위한 메소드 입니다. EntityGraph를 이용하여 즉시로딩으로 신청자 목록을 가져옵니다.(N + 1 문제 해결)
    @EntityGraph(value = "TeamMatching.forEagerApplicants")
    @Query("select t from TeamMatching t where t.createdAt < :cursor and t.isDeleted != true order by t.createdAt desc")
    List<TeamMatching> findAllByCreatedAtBefore(@Param("cursor")LocalDateTime cursor, PageRequest request);

    /* 레벨 시스템 : 모집상태가 완료된 사용자의 개수를 반환하는 메서드 */
    @Query("select count(*) " +
            "from TeamMatching t " +
            "join TeamApplicant a on t.teamMatchingId = a.teamMatching.teamMatchingId " +
            "where a.applicantId = :userId " +
            "and t.recruitmentStatus = 'COMPLETED' " +
            "and a.applyStatus = 'ACCEPTED'")
    long findTeamMatchingByCompleteParticipationCount(@Param("userId") Long userId);

    @Query("select count(*) from TeamMatching t where t.writer = :writer and t.recruitmentStatus = 'COMPLETED'")
    long countTeamMatchingByWriter(@Param("writer") User writer);
}
