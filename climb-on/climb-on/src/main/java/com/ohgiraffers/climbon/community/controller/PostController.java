package com.ohgiraffers.climbon.community.controller;

import com.ohgiraffers.climbon.community.dto.PostDTO;
import com.ohgiraffers.climbon.community.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/community")
public class PostController {

    @Autowired
    private PostService postService;

    // 게시글 목록 페이지 - 페이지네이션 및 카테고리 필터링 추가
    @GetMapping
    public String getAllPosts(@RequestParam(defaultValue = "1") int page, @RequestParam(required = false) String category, @RequestParam(required = false) String searchKeyword, Model model){  // page 파라미터와 pageSize를 사용해 해당 페이지에 맞는 게시글 목록을 조회
        // & category 파라미터를 받아 해당 카테고리의 게시글만 조회하도록 설정하고, 카테고리 파라미터가 없으면 모든 게시글 조회
        //  searchKeyword 파라미터를 추가해서 검색어가 있을 때만 검색 결과를 반환하도록 함.
        // 유효성 로직은 templates의 post.html에 있다.
        int pageSize = 15;
        List<PostDTO> posts = postService.getPostsByPageAndCategoryAndSearch(page, pageSize, category, searchKeyword);
        int totalPosts = postService.getTotalPostCount(category, searchKeyword); // 전체 게시글 수   //전체 게시글 수를 가져와 페이지수를 계산
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize); // 전체 페이지 수 계산  //ceil 함수는 올림을 해줌

        model.addAttribute("posts", posts);  // 뷰에 데이터 전달  (키, 객체)  //Thymeleaf는 ${키}로 입력하고 객체를 받음
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("category", category);
        model.addAttribute("searchKeyword", searchKeyword);

//        for (PostDTO post: posts) {   게시글에 DTO 값들이 잘 담기는지 확인
//            System.out.println(post.getTitle());
//            System.out.println(post.getCreatedAt());
//        }

        return "community/post";
    }

    // 특정 게시글 상세 페이지
    @GetMapping("/{id}")
    public String getPostById(@PathVariable Integer id, Model model){
        PostDTO post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "community/postDetail"; // 상세보기용 postDetail.html 템플릿 반환
    }

    // 게시글 작성 폼 페이지
    @GetMapping("/new")
    public String CreatePostForm(Model model){
        model.addAttribute("post", new PostDTO()); // 빈 PostDTO 객체 전달
        return "community/postForm"; // postForm.html 템플릿 반환
    }



    // 게시글 작성 처리
    @PostMapping("/new")
    public String createpost(@ModelAttribute PostDTO post){
        postService.insertPost(post); // 새로운 게시글 DB에 추가
        return "redirect:/community"; // 작성 후 게시글 목록으로 리다이렉트
    }

    // 게시글 수정 처리
    @PostMapping("/{id}/edit")
    public String updatePost(@PathVariable Integer id, @ModelAttribute PostDTO post){
        post.setId(id);
        postService.updatePost(post);
        return "redirect:/community/" + id;
    }

    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Integer id){
       postService.deletePost(id);
       return "redirect:/community";
    }

}
