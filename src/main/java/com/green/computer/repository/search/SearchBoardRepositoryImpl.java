package com.green.computer.repository.search;

import com.green.computer.entity.Board;
import com.green.computer.entity.QBoard;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    // 생성자
    // QuerydslRepositorySupport를 상속해야하고, 부모 생성자(QuerydslRepositorySupport)에 도메인(Board) 클래스 지정
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    // 메서드
    @Override
    public Board search1() {
        log.info("search1................");
        QBoard board = QBoard.board;
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.select(board).where(board.bno.eq(1L));
        log.info("---------------");
        log.info(jpqlQuery);
        log.info("----------------");
        List<Board> result = jpqlQuery.fetch();
        return null;
    }
}
