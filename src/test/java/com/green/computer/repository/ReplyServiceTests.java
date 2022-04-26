package com.green.computer.repository;

import com.green.computer.dto.ReplyDTO;
import com.green.computer.service.ReplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService service;

    @Test
    public void testGetList(){
        Long bno = 88L;
        List<ReplyDTO> replyDTOList = service.getList(bno);
        replyDTOList.forEach(i-> System.out.println(i));
    }
}
