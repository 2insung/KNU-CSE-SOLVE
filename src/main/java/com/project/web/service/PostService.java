package com.project.web.service;

import com.project.web.controller.dto.auth.PrincipalDetails;
import com.project.web.controller.dto.post.SavePostRequestDto;
import com.project.web.domain.*;
import com.project.web.exception.Error400Exception;
import com.project.web.exception.Error404Exception;
import com.project.web.repository.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostPreviewRepository postPreviewRepository;
    private final PostContentRepository postContentRepository;
    private final CommentRepository commentRepository;
    private final PostTypeRepository postTypeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void savePost(String type, SavePostRequestDto savePostRequestDto, PrincipalDetails principalDetails){
        PostType postType = postTypeRepository.findByType(type)
                .orElseThrow(() -> new Error404Exception("존재하지 않는 게시판입니다."));

        Member member = memberRepository.findById(principalDetails.getId())
                .orElseThrow(() -> new Error400Exception("존재하지 않는 사용자입니다."));

        Boolean isNotification = savePostRequestDto.getIsNotification() != null ?
                savePostRequestDto.getIsNotification() : false;
        String title = savePostRequestDto.getTitle();
        String body = savePostRequestDto.getBody();
        Document doc = Jsoup.parse(body);
        String bodyText = doc.text();
        String summary = bodyText.length() > 20 ? bodyText.substring(0, 20) + "..." : bodyText;
        Element imgElement = doc.select("img").first();
        String thumbnail = imgElement != null ? imgElement.attr("src") : null;

        Post post = Post.builder()
                .member(member)
                .postType(postType)
                .title(title)
                .isNotification(isNotification)
                .isHot(false)
                .hits(0)
                .recommend(0)
                .commentCount(0)
                .build();

        Post savedPost = postRepository.save(post);

        PostContent postContent = PostContent.builder()
                .post(savedPost)
                .body(body)
                .bodyText(bodyText)
                .build();

        PostPreview postPreview = PostPreview.builder()
                .post(savedPost)
                .summary(summary)
                .thumbnail(thumbnail)
                .build();

        postContentRepository.save(postContent);
        postPreviewRepository.save(postPreview);
    }

}
