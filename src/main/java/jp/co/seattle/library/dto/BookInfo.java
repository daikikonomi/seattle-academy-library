package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍基本情報格納DTO
 */
@Configuration
@Data
public class BookInfo {

    private int bookId;

    private String title;

    private String author;

    private String publisher;

    private String publish_date;
    
    private String explanation;
    
    private String isbn;
    
    private String thumbnail;
    
    public BookInfo() {

    }

    // コンストラクタ
    public BookInfo(int bookId, String title, String author, String publisher, String publish_date, String explanation, String isbn, String thumbnail) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.explanation = explanation;
        this.isbn = isbn;
        this.thumbnail = thumbnail;
    }

}