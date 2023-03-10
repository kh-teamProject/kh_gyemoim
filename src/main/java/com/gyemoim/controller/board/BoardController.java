package com.gyemoim.controller.board;

import com.gyemoim.domain.board.PageVO;
import com.gyemoim.domain.board.ReplyVO;
import com.gyemoim.service.board.BoardModifyService;
import com.gyemoim.service.board.BoardService;
import com.gyemoim.service.board.ReplyService;
import com.gyemoim.service.board.ReplyWriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/board/")
public class BoardController {

  @Inject
  private BoardService boardService;
  @Inject
  private ReplyService replyService;
  @Inject
  private ReplyWriteService replyWriteService;
  @Inject
  private BoardModifyService modifyService;
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String notice(Model model, @RequestParam(value = "nowPage", required = false) String nowPage, @RequestParam(value = "cntPerPager", required = false) String cntPerPage) throws Exception {
    int total = boardService.countBoard();

    if(nowPage == null && cntPerPage == null) {
      nowPage = "1";
      cntPerPage = "10";
    } else if(nowPage == null) {
      nowPage = "1";
    } else if (cntPerPage == null) {
      cntPerPage = "10";
    }

    PageVO vo = new PageVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));

    model.addAttribute("paging", vo);
    model.addAttribute("list", boardService.selectBoard(vo));

    return "board/notice";
  }

  @RequestMapping(value = "/read")
  public String read(Model model, @RequestParam("bid") int bid) throws Exception {
    List<ReplyVO> reply = replyWriteService.reply(bid);
    model.addAttribute("board", boardService.readDetail(bid));
    model.addAttribute("reply", reply);
    model.addAttribute("attached", modifyService.attached(bid));

    return "board/read";
  }

  @RequestMapping(value = "/getSearchList")
  public String getSearchList(Model model, @ModelAttribute("spv") PageVO spv, @RequestParam(value = "nowPage", required = false) String nowPage, @RequestParam(value = "cntPerPage", required = false) String cntPerPage) throws Exception {
    int total = boardService.searchCountBoard(spv);

    if(cntPerPage == null && nowPage == null) {
      nowPage = "1";
      cntPerPage = "10";
    } else if(nowPage == null) {
      nowPage = "1";
    } else if(cntPerPage == null) {
      cntPerPage = "10";
    }

    PageVO vo = new PageVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage), spv.getType(), spv.getKeyword());

    model.addAttribute("paging", vo);
    model.addAttribute("listAll", boardService.searchList(vo));

    return "board/searchList";
  }

}
