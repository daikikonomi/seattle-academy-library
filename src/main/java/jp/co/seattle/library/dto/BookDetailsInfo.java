package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class BookDetailsInfo {

    private int bookId;

    private String title;

    private String author;

    private String publisher;
    
    private String publish_date;
    
    private String isbn;
    
    private String explanation;
    
    private String thumbnailUrl;

    private String thumbnailName;
    
    private int rentBookId; 
    
    private String rentStatus;

    public BookDetailsInfo() {

    }

    //コンストラクタ
    public BookDetailsInfo(int bookId, String title, String author, String publisher, String publish_date, String isbn, String explanation,
            String thumbnailUrl, String thumbnailName, int rentBookId, String rentStatus) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.isbn = isbn;
        this.explanation = explanation;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailName = thumbnailName;
        this.rentBookId = rentBookId;
        this.rentStatus = rentStatus;
    }

}