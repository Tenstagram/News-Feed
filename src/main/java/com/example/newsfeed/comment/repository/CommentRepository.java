package com.example.newsfeed.comment.repository;

import com.example.newsfeed.comment.entity.Comment;
import com.example.newsfeed.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 게시글 ID 로 댓글 목록 조회
    // soft delete 방식을 채택했기 때문에 쿼리문을 커스텀해서 삭제 여부를 체크
    @Query("SELECT c " +
            "FROM Comment c " +
            "WHERE c.post.postId = :postId AND c.deletedAt IS NULL " +
            "ORDER BY c.updatedAt")
    List<Comment> findAllByPostId(@Param("postId") Long postId, Pageable pageable);

    // Pageable 쓰면 댓글도 페이징해서 할 수 있음
    // 그냥 List 로만 하면 댓글이 한번에 쭈르륵 조회되는 방식임

    // 좋아요가 많은 상위 3개의 댓글 목록 조회 (삭제되지 않은 것만 필터링)
    // JPQL 는 LIMIT 가 없기 때문에 setMaxResults(3) 혹은 Top3 키워드 사용

    // 조건: LikeComment 와 조인하여 삭제된거 필터링 + 좋아요 카운트 후 내림차순으로 정렬 + 카운트된 좋아요 개수로 추가 필터링
    @Query("SELECT c " +
            "FROM Comment c " +
            "LEFT JOIN LikeComment lc ON lc.comment.commentId = c.commentId " +
            "WHERE c.post.postId = :postId AND c.deletedAt IS NULL " +
            "GROUP BY c.commentId " +
            "HAVING COUNT(lc) >= 5 " +
            "ORDER BY COUNT(lc) DESC, c.updatedAt")
    List<Comment> findTop3ByPostIdOrderByLikeCountDesc(@Param("postId") Long postId);

    Long countByPost(Post post); // 해당 게시물의 댓글 수를 세는 쿼리 추가했습니다.

}


