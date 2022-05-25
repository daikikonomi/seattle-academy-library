package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;


/**
 * 書籍貸出履歴格納DTO
 * 
 */
@Configuration
@Data
public class CirculationHistoryInfo {
	
    private int bookId;

    private String title;

    private String rentDate;

    private String returnDate;
    
    public CirculationHistoryInfo() {
    	
    }
    
    //コンストラクタ
    public CirculationHistoryInfo (int bookId, String title, String rentDate, String returnDate) {
    	this.bookId = bookId;
        this.title = title;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

}
