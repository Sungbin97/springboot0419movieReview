package com.green.computer.repository;

import com.green.computer.entity.Board;
import com.green.computer.repository.search.SearchBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository {

    // 아래는 클래스 기반의 쿼리문이므로, 실제 쿼리문과 조금 다름

    // Member와 Join
    @Query("select b,w from Board b left join b.writer w where b.bno = :bno") // :bno => param으로 받아온 bno를 표현하는 JPA 문법, Board 반드시 대문자
    Object getBoardWithWriter(@Param("bno") Long bno);


    // Reply와 Join
    @Query("select b,r from Board b left join Reply r on r.board =b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
    // Board class에 reply는 없음(연관관계 지정X) 따라서 on을 넣어줘야함
    // 만약 연관관계가 있으면 on 생략가능(자동으로 들어감)


    // 테이블 3개 조인(내용 아래)
    // 게시물(Board): 게시물 번호, 제목, 게시물 작성 시간
    // 회원(Member): 회원 이름, email
    // 댓글(Reply): 해당 게시물의 댓글

    // countQuery: 게시글 기준으로 group by 해라
    // 게시글 번호로 그루핑을 하여 목록화함, 동일한 게시글이 있으면
    // 동일한 게시글로 그루핑하여 하나의 게시글로 통합하여 게시글마다 고유한 번호로 출력됨
    @Query(value = "select b,w, count(r) from Board b " +
            "LEFT JOIN b.writer w " +
            "left outer join Reply r on r.board = b " +
            "group by b"
            , countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);


    @Query(value = "select b,w, count(r) from Board b " +
            "LEFT JOIN b.writer w " +
            "left outer join Reply r on r.board = b " +
            "where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);


}
