package com.green.computer.repository;

import com.green.computer.entity.Member;
import com.green.computer.entity.Movie;
import com.green.computer.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    @Modifying
    @Query("delete from Review mr where mr.member=:member")
    void deleteByMember(Member member);
    //3개의 데이터를 삭제한다고 하면 당연히 where 조건에 member_mid 칼럼을 이용해서 한번에 3개의 데이터가 삭제될것처럼 보이지만
    //실제로 실행되는 SQL은 review 테이블에서 3번 반복적으로 실행된 후에 m_member 테이블을 삭제하므로
    //이런 비효율을 막기 위해 @Query를 이용해서 where 절을 지정하는 것이 더 나은 방법이고 update 나 delete를 이용하기 위해
    //@Modify 어노테이션이 반드시 필요함

}
