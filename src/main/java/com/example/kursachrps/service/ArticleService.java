package com.example.kursachrps.service;

import com.example.kursachrps.models.Article;
import com.example.kursachrps.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final RestTemplate restTemplate;

    @Value("${file.manager.path}")
    private String fileManagerPath;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, RestTemplate restTemplate) {
        this.articleRepository = articleRepository;
        this.restTemplate = restTemplate;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll(Sort.by(Sort.Direction.DESC, "dateTime"));
    }

    public Page<Article> getAllArticlesWithPagination(int numPage, int pageSize) {
        Sort sort = Sort.by(Sort.Direction.DESC, "dateTime");
        Pageable pageable = PageRequest.of(numPage, pageSize).withSort(sort);
        return articleRepository.findAll(pageable);
    }

    public List<Article> getLastFiveArticles() {
        return articleRepository.findTop5ByOrderByDateTimeDesc();
    }

    @Transactional
    public Article saveArticle(Article article, MultipartFile file) {
        if (file != null && file.getSize() != 0) {
            Article article1 = articleRepository.save(article);
            Article article2 = articleRepository.findById(article1.getId()).orElse(null);
            String fileName = article1.getId() + "_" + file.getOriginalFilename();
            if (article2 != null) {
                org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);

                MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
                body.add("fileName", fileName);
                body.add("file", new FileSystemResource(Objects.requireNonNull(convertMultipartFileToFile(file))));

                HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

//                ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/articleImages/upload", HttpMethod.POST, requestEntity, String.class);
                ResponseEntity<String> response = restTemplate.exchange(fileManagerPath + "/articleImages/upload", HttpMethod.POST, requestEntity, String.class);
                article2.setLink(response.getBody().substring(response.getBody().lastIndexOf('/')+1));
                return articleRepository.save(article2);
            }
        } else  {
            return articleRepository.save(article);
        }
        return null;
    }

    public void editArticle(String articleId, String name, String body, MultipartFile file) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article != null) {
            article.setName(name);
            article.setBody(body);
            articleRepository.save(article);
//            String fileName = article.getId() + "_" + file.getOriginalFilename();
//            String oldFileName = article.getLink();
//            article.setLink(fileName);
//            articleRepository.save(article);
//
//            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
//            requestBody.add("fileName", fileName);
//            requestBody.add("oldFileName", oldFileName);
//            requestBody.add("file", new FileSystemResource(Objects.requireNonNull(convertMultipartFileToFile(file))));
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//            ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/articleImages/upload", HttpMethod.POST, requestEntity, String.class);
        }
    }

    public Article getArticleById(String articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }

    public void deleteArticle(String articleId) {
        articleRepository.deleteById(articleId);
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        if (!file.isEmpty()) {
            File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
            try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return convertedFile;
        }
        else {
            return null;
        }
    }
}
