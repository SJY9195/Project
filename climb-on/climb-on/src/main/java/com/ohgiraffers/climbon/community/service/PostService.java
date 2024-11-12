package com.ohgiraffers.climbon.community.service;

import com.ohgiraffers.climbon.community.dao.PostDAO;
import com.ohgiraffers.climbon.community.dto.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostDAO postDAO;

    // 페이지와 카테고리에 따라 필터링된 게시글 목록을 가져온다.
    public List<PostDTO> getPostsByPageAndCategoryAndSearch(int page, int pageSize, String category, String searchKeyword) {
        int offset = (page - 1) * pageSize;
        return postDAO.getPostsByPageAndCategoryAndSearch(offset, pageSize, category, searchKeyword);    // 해당 페이지의 게시글을 가져오기 위해 offset 값을 계산하고, 이를 기반으로 DAO에서 데이터 가져옴. ,searchKeyword 파라미터 추가
    }

    // 카테고리 필터링을 적용하여 총 게시글 수를 반환한다.
    public int getTotalPostCount(String category, String searchKeyword) {
        return postDAO.getTotalPostCount(category, searchKeyword); // 전체 게시글 수를 가져오는 메소드
    }


    public PostDTO getPostById(Integer id) {
        // 조회시 조회수 증가
        postDAO.incrementViewCount(id);
        return postDAO.getPostById(id);
    }

    public void insertPost(PostDTO post) {
        postDAO.insertPost(post);
    }

    public void updatePost(PostDTO post) {
        postDAO.updatePost(post);
    }

    public void deletePost(Integer id) {
        postDAO.deletePost(id);
    }

}
