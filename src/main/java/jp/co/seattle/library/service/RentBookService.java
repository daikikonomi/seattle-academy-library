package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RentBookService {
	final static Logger logger = LoggerFactory.getLogger(RentBookService.class);
    
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/**
	 * booksテーブルから対象の本を取得しrentBooksテーブルに挿入
	 * 
	 * @param bookId
	 */
    public void rentBook(int bookId) {

        String sql = "INSERT INTO rentBooks(book_id) SELECT " + bookId + " where not exists (select book_id from rentbooks where book_id = " + bookId + ")";
        
        jdbcTemplate.update(sql);
    }
    


    /**
     * rentbooksテーブルから対象の本を削除（返却）
     * 
     * @param bookId
     */
    public void returnBook(int bookId) {

        String sql = "delete from rentbooks where book_id = " + bookId ;
        
        jdbcTemplate.update(sql);
    }
    

    //rentbooksテーブルから本を取得しカウントする
    public int getRentBook() {
    	
    	String sql = "SELECT count (book_id) from rentBooks";
    	
		return jdbcTemplate.queryForObject(sql,int.class);

	}   

}
