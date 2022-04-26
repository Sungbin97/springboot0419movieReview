package com.green.computer.service;

import com.green.computer.dto.BoardDTO;
import com.green.computer.dto.PageRequestDTO;
import com.green.computer.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("Test...제목")
                .content("Test...내용")
                .writerEmail("user55@aaa.com")
                .build();
        Long bno = boardService.register(dto);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(pageRequestDTO);
        // getList 함수 호출하면, 쿼리문에 '테이블 조인'이 되므로 => 여러 entity (Member, Board, ReplyCount)가 반환됨 => 이걸 Object[]로 표현
        for(BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet(){
        Long bno = 100L;
        BoardDTO dto = boardService.get(bno);
        System.out.println(dto);
    }

    @Test
    public void testRemove(){
        Long bno = 2L;
        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(3L)
                .title("제목 변경합니다.")
                .content("내용 변경합니다.")
                .build();
        boardService.modify(boardDTO);
    }
}
