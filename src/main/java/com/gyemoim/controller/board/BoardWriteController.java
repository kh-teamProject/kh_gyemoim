package com.gyemoim.controller.board;

import com.gyemoim.dto.board.BoardWriteDTO;
import com.gyemoim.service.board.BoardWriteService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.InputStream;

@Controller
public class BoardWriteController {
    @Autowired
    private SqlSession sqlSession;
    @Inject
    private BoardWriteService service;
    @RequestMapping(value = "/write")
    public String write() {
        return "board/write";
    }

    @RequestMapping(value = "/writePost" ,method = RequestMethod.POST)
    public String writePost(BoardWriteDTO dto, RedirectAttributes request) throws Exception {
        System.out.println("해결이 되었습니당");
        service.write(dto);
        request.addFlashAttribute("message","Sucess!");
        return "redirect:/board/list";
    }

}
