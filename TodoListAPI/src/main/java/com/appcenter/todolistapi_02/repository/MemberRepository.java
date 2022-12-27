package com.appcenter.todolistapi_02.repository;

import com.appcenter.todolistapi_02.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> getByMemberId(String memberId);
    boolean existsByMemberId(String memberId);

    Optional<Member> findById(Long memberId);


    @Transactional
    void deleteById(Long id);

    Optional<Member> findByMemberId(String username);
}
