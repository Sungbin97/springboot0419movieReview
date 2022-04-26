package com.green.computer.repository.search;

import com.green.computer.entity.Board;
import com.green.computer.entity.QBoard;
import com.green.computer.entity.QMember;
import com.green.computer.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

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
        List<Board> result = jpqlQuery.fetch(); //fetch=가져오다
        return null;
    }

    @Override
    public Board search2() {
        log.info("search2................");
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);
        log.info("---------------");
        log.info(jpqlQuery);
        log.info("----------------");
        List<Board> result = jpqlQuery.fetch(); //fetch=가져오다
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage..........");
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);
        booleanBuilder.and(expression);
        if(type!=null){
            String[] typeArr = type.split("");
            // 검색 조건 작성하기
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                    }
                }
                booleanBuilder.and(conditionBuilder);
            }
            tuple.where(booleanBuilder);
            //sort by .org.springframework.data.domain.Sort는 내부적으로 여러개의 Sort 객체를 연결 할 수 있기 때문에 forEach구문 사용하여 처리
            //OrderSpecifier에는 정렬이 필요하므로 Sort 객체의 정렬 관련 정보를 com.querydsl.core.types.Order타입으로 처리하고 Sort 객체에 속성
            //(bno, title)등은 PathBuilder라는 것을 이용하여 처리
            //PathBuilder를 생성 할 때 문자열로 된 이름은 JPQLQuery를 생성시에 이용하는 변수명과 동일해야 함
            //JPQLquery를 이용해서 동적으로 검색 조건을 처리해보면 상당히 복잡하고 어렵다는 생각을 하게됩니다.
            //대신에 얻는 장점은 한번의 개발만으로 count 쿼리도 같이 처리 할 수 있다는 점입니다.
            //count를 얻는 방법은 fetchCount 입니다.
            Sort sort = pageable.getSort();
            sort.stream().forEach(order -> {
                Order direction = order.isAscending() ? Order.ASC : Order.DESC;
                String prop = order.getProperty();
                PathBuilder orderByExpression = new PathBuilder(Board.class, "board");
                tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
            });
            tuple.groupBy(board);
            //page 처리
            tuple.offset(pageable.getOffset());
            tuple.limit(pageable.getPageSize());
            List<Tuple> result = tuple.fetch();
            log.info(result);
            long count  = tuple.fetchCount();
            log.info("Count: " + count);
            return new PageImpl<Object[]>(result.stream().map(t->
                t.toArray()).collect(Collectors.toList()), pageable, count);

    //PathBuilder => 사용 방법
    }
}

