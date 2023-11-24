package com.insung.knucsesolve;

import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentServiceTest {
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//    @Autowired
//    private PostRepository postRepository;
//    @Autowired
//    private PostCommentCountRepository postCommentCountRepository;
//    @Autowired
//    private CommentRepository commentRepository;
//    @Autowired
//    private CommentChildCountRepository commentChildCountRepository;
//    @Autowired
//    private CommentStatRepository commentStatRepository;
//    @Autowired
//    private CommentRecommendMemberRepository commentRecommendMemberRepository;
//    @Autowired
//    private CommentService commentService;
//
//
//    @Test
//    public void setUp() throws InterruptedException {
//        Member member1 = Member.builder()
//                .id(1)
//                .build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .id(2)
//                .build();
//        memberRepository.save(member2);
//
//        Member member3 = Member.builder()
//                .id(3)
//                .isDeleted(false)
//                .build();
//        memberRepository.save(member3);
//
//        Member member4 = Member.builder()
//                .id(4)
//                .isDeleted(false)
//                .build();
//        memberRepository.save(member4);
//
//        Board board = Board.builder()
//                .id(1)
//                .type("testType")
//                .alias("testAlias")
//                .build();
//        boardRepository.save(board);
//
//        Post post = Post.builder()
//                .id(1)
//                .member(member1)
//                .board(board)
//                .build();
//        postRepository.save(post);
//        PostCommentCount postCommentCount = PostCommentCount.builder()
//                .post(post)
//                .commentCount(3)
//                .totalCommentCount(3)
//                .build();
//        postCommentCountRepository.save(postCommentCount);
//
//
//        Comment comment1 = Comment.builder()
//                .id(1)
//                .rootCommentId(1)
//                .member(member2)
//                .parentMember(member2)
//                .post(post)
//                .isDeleted(false)
//                .isRoot(true)
//                .childCount(2)
//                .build();
//        CommentStat commentStat1 = CommentStat.builder()
//                .comment(comment1)
//                .recommendCount(0)
//                .build();
//        commentRepository.save(comment1);
//        commentStatRepository.save(commentStat1);
//
//        Comment comment2 = Comment.builder()
//                .id(2)
//                .rootCommentId(1)
//                .member(member3)
//                .parentMember(member2)
//                .post(post)
//                .isDeleted(false)
//                .isRoot(false)
//                .childCount(0)
//                .build();
//        CommentStat commentStat2 = CommentStat.builder()
//                .comment(comment2)
//                .recommendCount(0)
//                .build();
//        commentRepository.save(comment2);
//        commentStatRepository.save(commentStat2);
//
//        Comment comment3 = Comment.builder()
//                .id(3)
//                .rootCommentId(1)
//                .member(member4)
//                .parentMember(member2)
//                .post(post)
//                .isDeleted(false)
//                .isRoot(false)
//                .childCount(0)
//                .build();
//        CommentStat commentStat3 = CommentStat.builder()
//                .comment(comment3)
//                .recommendCount(0)
//                .build();
//        commentRepository.save(comment3);
//        commentStatRepository.save(commentStat3);
//
//        int numberOfThreads = 2;
//        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
//        CountDownLatch latch = new CountDownLatch(numberOfThreads);
//
//        for (int i = 0; i < numberOfThreads; i++) {
//            Integer postId = 1;
//            Integer commentId = i == 0 ? 2 : 3;
//            service.submit(() -> {
//                commentService.deleteComment(postId, commentId);
//                latch.countDown();
//            });
//        }
//        latch.await(); // 모든 스레드가 완료될 때까지 대기
//        service.shutdown();
//
//        // 1. 루트 댓글의 자식이 0인지. 2. postCommentCount 가 1인지. 3. 각 멤버의 댓글 수가  -1인지
//        Comment comment = commentRepository.findById(1).orElseThrow(() -> new Error404Exception("존재하지 않는,."));
//        assertEquals(comment.getChildCount(), 0);
//
//    }
//
//    @Test
//    public void setUp2() throws InterruptedException {
//        Member member1 = Member.builder()
//                .id(1)
//                .build();
//        memberRepository.save(member1);
//
//        Member member2 = Member.builder()
//                .id(2)
//                .build();
//        memberRepository.save(member2);
//
//        Member member3 = Member.builder()
//                .id(3)
//                .isDeleted(false)
//                .build();
//        memberRepository.save(member3);
//
//        Member member4 = Member.builder()
//                .id(4)
//                .isDeleted(false)
//                .build();
//        memberRepository.save(member4);
//
//        Board board = Board.builder()
//                .id(1)
//                .type("testType")
//                .alias("testAlias")
//                .build();
//        boardRepository.save(board);
//
//        Post post = Post.builder()
//                .id(1)
//                .member(member1)
//                .board(board)
//                .build();
//        postRepository.save(post);
//        PostCommentCount postCommentCount = PostCommentCount.builder()
//                .post(post)
//                .commentCount(3)
//                .totalCommentCount(3)
//                .build();
//        postCommentCountRepository.save(postCommentCount);
//
//
//        Comment comment1 = Comment.builder()
//                .id(1)
//                .rootCommentId(1)
//                .member(member2)
//                .parentMember(member2)
//                .post(post)
//                .isDeleted(false)
//                .isRoot(true)
//                .childCount(2)
//                .build();
//        CommentStat commentStat1 = CommentStat.builder()
//                .comment(comment1)
//                .recommendCount(0)
//                .build();
//        commentRepository.save(comment1);
//        commentStatRepository.save(commentStat1);
//
//        Comment comment2 = Comment.builder()
//                .id(2)
//                .rootCommentId(1)
//                .member(member3)
//                .parentMember(member2)
//                .post(post)
//                .isDeleted(false)
//                .isRoot(false)
//                .childCount(0)
//                .build();
//        CommentStat commentStat2 = CommentStat.builder()
//                .comment(comment2)
//                .recommendCount(0)
//                .build();
//        commentRepository.save(comment2);
//        commentStatRepository.save(commentStat2);
//
//        Comment comment3 = Comment.builder()
//                .id(3)
//                .rootCommentId(1)
//                .member(member4)
//                .parentMember(member2)
//                .post(post)
//                .isDeleted(false)
//                .isRoot(false)
//                .childCount(0)
//                .build();
//        CommentStat commentStat3 = CommentStat.builder()
//                .comment(comment3)
//                .recommendCount(0)
//                .build();
//        commentRepository.save(comment3);
//        commentStatRepository.save(commentStat3);
//
//        int numberOfThreads = 3;
//        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
//        CountDownLatch latch = new CountDownLatch(numberOfThreads);
//
//        for (int i = 0; i < numberOfThreads; i++) {
//            Integer postId = 1;
//            Integer commentId = i == 0 ? 2 : i == 1 ? 1 : 3;
//            service.submit(() -> {
//                commentService.deleteComment(postId, commentId);
//                latch.countDown();
//            });
//        }
//        latch.await(); // 모든 스레드가 완료될 때까지 대기
//        service.shutdown();
//
//        // 1. 루트 댓글의 자식이 0인지. 2. postCommentCount 가 1인지. 3. 각 멤버의 댓글 수가  -1인지
//        long count = commentRepository.count();
//        assertEquals(count , 0);
//
//        PostCommentCount pcc = postCommentCountRepository.findByPostId(1)
//                        .orElseThrow(() -> new RuntimeException());
//        assertEquals(pcc.getCommentCount(), 0);
//        assertEquals(pcc.getTotalCommentCount(), 0);
//    }

}
