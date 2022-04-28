package com.green.computer.repository;

import com.green.computer.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
//    @Query("select m, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m"
//        +" left outer join Review r on r.movie = m group by m"
//    )
//    Page<Object[]> getListPage(Pageable pageable);
//    @Query("select m, max(mi), avg(coalesce(r.grade, 0)), count(distinct r) from Movie m"
//            +" left outer join MovieImage mi on mi.movie = m"
//            +" left outer join Review r on r.movie = m group by m"
//    )
//    Page<Object[]> getListPage(Pageable pageable);
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m"
            +" left outer join MovieImage mi on mi.movie = m "
            +" left outer join Review r on r.movie = m group by m"
    )
    Page<Object[]> getListPage(Pageable pageable); // 페이지 처리
    //쿼리의 내용을 보면 movie_image 테이블에서 해당하는 모든 영화와 이미지를 다 가져오는 쿼리입니다.
    //이러한 상황이 발생하는 이유는 목록을 가져오는 쿼리는 문제가 없지만 max를 이용하는 부분이 들어가면서 모든 이미지를 가져오는
    //쿼리가 실행됩니다. (N+1문제)
    // N+1 문제
    // N+1 문제는 다음과 같은 상황을 의미합니다.
    // 1번의 쿼리로 N개의 데이터를 가져왔는데 N개의 데이터를 처리하기 위해서 필요한 추가적인 쿼리가 각 N개에 대해서 수행되는 상황
    // 위의 경우 1페이지에 해당하는 10개의 데이터를 가져오는 쿼리 1번과 각 영화의 모든 이미지를 가져오기 위한 10번의 추가적인 쿼리가 실행되는 것입니다.
    // 이렇게 되면 한 페이지를 볼 때 마다 11번의 쿼리를 실행하기 때문에 성능에 문제가 생기므로 해결해야 합니다.
    @Query("select m, mi from Movie m left outer join MovieImage mi on mi.movie = m  where m.mno=:mno")
    List<Object[]> getMovieWithAll(Long mno);// 특정 영화 조회
}
