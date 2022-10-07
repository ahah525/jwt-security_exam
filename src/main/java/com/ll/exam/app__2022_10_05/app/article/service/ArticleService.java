package com.ll.exam.app__2022_10_05.app.article.service;

import com.ll.exam.app__2022_10_05.app.article.dto.ArticleModifyDto;
import com.ll.exam.app__2022_10_05.app.article.entity.Article;
import com.ll.exam.app__2022_10_05.app.article.repository.ArticleRepository;
import com.ll.exam.app__2022_10_05.app.member.entity.Member;
import com.ll.exam.app__2022_10_05.app.security.entity.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public Article write(Member author, String subject, String content) {
        Article article = Article.builder()
                .author(author)
                .subject(subject)
                .content(content)
                .build();

        articleRepository.save(article);

        return article;
    }

    public List<Article> findAll() {
        return articleRepository.findAllByOrderByIdDesc();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public void delete(Article article) {
        articleRepository.delete(article);
    }

    // 게시물 삭제 권한 있는지 검증
    public boolean actorCanDelete(MemberContext memberContext, Article article) {
        return memberContext.getId() == article.getAuthor().getId();
    }

    @Transactional
    public void modify(Article article, ArticleModifyDto articleModifyDto) {
        article.setSubject(articleModifyDto.getSubject());
        article.setContent(articleModifyDto.getContent());
    }

    // 게시물 수정 권한 있는지 검증
    public boolean actorCanModify(MemberContext memberContext, Article article) {
        return memberContext.getId() == article.getAuthor().getId();
    }
}
