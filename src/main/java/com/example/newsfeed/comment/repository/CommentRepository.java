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
    // ':postId' 는 @Param("postId") 를 통해 값을 바인딩 한다는 거고, 이 값을 post 의 postId 과 대조에서 게시물을 특정시킴
//    @Query("SELECT * FROM Comment c WHERE c.post.postId = :postId AND c.deletedAt IS NULL ORDER BY c.updatedAt")
    // SQL 쿼리문이랑 다르게 JPQL 에서는 SELECT * 사용 할 수 없음. JPQL 는 객체를 다루는 거기 때문에 * 대신 객체를 SELECT 시켜야함. SQL 의 객체 버전이라 생각하면 편함
//    @Query("SELECT Comment FROM Comment c WHERE c.post.postId = :postId AND c.deletedAt IS NULL ORDER BY c.updatedAt")
    // 이렇게하면 Comment 라는 객체가 아니라 엔티티의 이름을 반환 시킴
    // => 객체 자체를 반환 시키려면 무조건 FROM 에서 객체 별칭 설정하고 별칭으로 넣어야함. 흔히 메서드 필드에서 '반환객체 변수 = 내용' 이 방식과 똑같다고 생각하면 됨.

    @Query("SELECT c " +
            "FROM Comment c " +
            "WHERE c.post.postId = :postId AND c.deletedAt IS NULL " +
            "ORDER BY c.updatedAt")
    List<Comment> findAllByPostId(@Param("postId") Long postId, Pageable pageable);

    // Pageable 쓰면 댓글도 페이징해서 할 수 있음
    // 그냥 List 로만 하면 댓글이 한번에 쭈르륵 조회되는 방식임

    // 좋아요가 많은 상위 3개의 댓글 목록 조회 (삭제되지 않은 것만 필터링)
    // JPQL 는 LIMIT 가 없기 때문에 setMaxResults(3) 혹은 Top3 키워드 사용해야됨.
    // => Top3 키워드는 메서드 이름에 적으면 알아서 적용됨
    // => setMaxResults(3) 는 EntityManager 쿼리문 방식을 사용하여 작성 할때 사용
    // => @Query 쿼리문 방식은 Top3 키워드 방식이 더 알맞음
    @Query("SELECT c " +
            "FROM Comment c " +
            "LEFT JOIN LikeComment lc ON lc.comment.commentId = c.commentId " +
            "WHERE c.post.postId = :postId AND c.deletedAt IS NULL " +
            "GROUP BY c.commentId " +
            "ORDER BY COUNT(lc) DESC, c.updatedAt")
    List<Comment> findTop3ByPostIdOrderByLikeCountDesc(@Param("postId") Long postId);

    Long countByPost(Post post);//해당 게시물의 댓글 수를 세는 쿼리 추가했습니다.

}


