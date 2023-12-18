package com.example.kursachrps.service;

import com.example.kursachrps.models.Article;
import com.example.kursachrps.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;
    private FileService fileService;

    public ArticleService(ArticleRepository articleRepository,
                          FileService fileService) {
        this.articleRepository = articleRepository;
        this.fileService = fileService;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article saveArticle(Article article, MultipartFile file1) throws IOException{

        if (file1  != null && file1.getSize() != 0) {
            article.setFile(file1);
        }
        Article articleFromDB = new Article();
        if (article != null) {
            fileService.saveImage(article);
            articleFromDB = articleRepository.save(article);
        }
        return articleFromDB;
    }

    public Article getArticleById(String articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }

    public void deleteArticle(String articleId) {
        articleRepository.deleteById(articleId);
    }

}
