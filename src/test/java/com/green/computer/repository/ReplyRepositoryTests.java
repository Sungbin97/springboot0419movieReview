package com.green.computer.repository;

import com.green.computer.entity.Board;
import com.green.computer.entity.Member;
import com.green.computer.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository repository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1,300).forEach(i->{

            // Reply 등록할 Board Set
            long bno = (long)(Math.random()*100)+1; // 1부터 100까지의 수
            Board board = Board.builder()
                    .bno(bno)
                    .build();

            // Reply Set
            Reply reply = Reply.builder()
                    .text("Reply..."+i)
                    .board(board) // 위에서 만든 Board Entity 넘겨줌
                    .replyer("guest")
                    .build();
            repository.save(reply);
        });
    }
    @Test
    public void testListByBoard(){
        List<Reply> replyList = repository.getRepliesByBoardOrderByRno(Board.builder().bno(100L).build());
        replyList.forEach(i -> System.out.println(i));
    }
}
