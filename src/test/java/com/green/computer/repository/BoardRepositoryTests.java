package com.green.computer.repository;

import com.green.computer.entity.Board;
import com.green.computer.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1,100).forEach(i->{

            // 글 작성자 정보 등록을 위한 Member Set
            Member member = Member.builder()
                    .email("User"+i+"@aaa.com")
                    .build();

            // Board Set
            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content..."+i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Transactional // 추가 안하면 Session이 close 되었다는 경고 뜨면서 터져버림(아래 getWriter도 동시에 가져오려고하면)
    @Test
    public void testRead(){
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();
        System.out.println("Board: "+board);
        System.out.println("Board 작성자: "+board.getWriter());
        // Board 가져올때 Member(getWriter)도 동시에 가져오려고 하면 터짐 => @Transactional 추가하면 안터짐
        // Board class에서 writer 멤버변수에 fetch = FetchType.LAZY로 해놨기 때문

        // 위 내용은 '연관관계' 지정 이슈임
        // Entity간 연관관계를 지정하는 경우 항상 @ToString으로 주의해야함
        // ToSTring은 해당 클래스 모든 멤버변수를 출력하는데
        // writer 멤버변수를 출력하면 Member 객체도 같이 출력되어야함
        // Member를 출력하기 위해 Memeber 객체의 ToString이 호출되어야 하고 이때 테이블의 연결이 필요하므로
        // 습관적으로 항상 foreign key로 연결된 테이블의 멤버변수를 제외(exclude)하는 습관을 가져야함

        // 지연로딩(fetch = FetchType.LAZY)의 장단점
        // 지연로딩은 조인을 하지 않기 때문에 단순하게 하나의 테이블을 이용하는 경우에는 빠른 속도처리가 가능한 장점이 있으나
        // 필요한 순간에 쿼리를 실행해야 하는 경우 연관관계가 복잡한 경우 여러번의 쿼리가 실행된다는 단점이 있다
        // 지연 로딩을 기본으로 사용하고 상황에 맞게 필요한 방법을 찾는다로 생각할 것
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[]) result;
        System.out.println("------------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);
        for(Object[] i : result){
            System.out.println(Arrays.toString(i));
        }
    }

    @Test
    public void testGetBoardWithReplyCount(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead2(){
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){
        boardRepository.search1();
    }


}
