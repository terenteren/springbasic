package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
//@ResponseBody
@RestController
public class CommentController {

    @Autowired
    CommentService service;

    // 댓글 수정
//    http://localhost:8888/ch4/comments/43
//    {
//        "pcno": 0,
//        "comment" : "hihihi",
//        "commenter" : "asdf"
//    }
    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session) {
//        String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setCno(cno);
        System.out.println("dto = " + dto);

        try {
            if(service.modify(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }


    // 댓글 등록
//    http://localhost:8888/ch4/comments?bno=872
//    {
//        "pcno": 0,
//        "comment" : "hi"
//    }
//    json 데이터의 경우 @RequestBody를 붙여 json을 자바객체로 변환,
//    header에 Content-Type:application/json가 필요
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setBno(bno);
        System.out.println("dto = " + dto);
        
        try {
            if(service.write(dto)!=1)
                throw new Exception("Write failed.");
            
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }


    // 댓글 삭제
    @DeleteMapping("/comments/{cno}") // /comments/1?bno=1085  PathVariable 사용(url 매게변수사용)
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id");
        String commenter = "asdf";

        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 댓글 조회
    @RequestMapping("/comments")
    public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;

        try {
            list = service.getList(bno);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400
        }
    }

}
