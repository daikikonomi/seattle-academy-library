package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.CirculationHistoryInfo;
import jp.co.seattle.library.rowMapper.CirculationHistoryInfoRowMapper;

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

        String sql = "INSERT INTO rentBooks(book_id, rent_date) SELECT " + bookId + ",now() where not exists (select book_id from rentbooks where book_id = " + bookId + ")";
        
        jdbcTemplate.update(sql);
    }
    
    
    
    /**
     * 借りられた本の情報を取得する
     * 
     * @param bookId
     * @return
     */
    public List<CirculationHistoryInfo> getRentBookInfo() {

        // JSPに渡すデータを設定する
    	List<CirculationHistoryInfo> rentBookList = jdbcTemplate.query(
    			"SELECT books.id, title, rent_date, return_date from rentbooks left join books on books.id = rentBooks.book_id ",
    			new CirculationHistoryInfoRowMapper()); 
        

        return rentBookList;
    }
    
    
    /**
     * 指定された本と一致する本の情報をDTOから取得する
     * 
     * @param bookId
     * @return
     */
    public CirculationHistoryInfo selectRentHistoryInfo(int bookId) {
        
        String sql = "SELECT books.id, title, rent_date, return_date from rentbooks left join books on books.id = rentBooks.book_id where books.id = " + bookId ;
        
    try {
    	 CirculationHistoryInfo selectRentHistoryInfo = jdbcTemplate.queryForObject(sql, new CirculationHistoryInfoRowMapper());
         return selectRentHistoryInfo;
    	
    }catch(Exception e) {
    	return null ;
    }
    
    }

    /**
     * rentbooksテーブルから対象の本を返却
     * 
     * @param bookId
     */
    public void returnBook(int bookId) {

        //String sql = "delete from rentbooks where book_id = " + bookId ;
    	String sql ="UPDATE rentbooks SET rent_date = null, return_date = now() WHERE rentbooks.book_id = " + bookId;
        
        jdbcTemplate.update(sql);
    }
    
    
    public void updateStatus(int bookId) {

        //String sql = "delete from rentbooks where book_id = " + bookId ;
    	String sql ="UPDATE rentbooks SET rent_date = now(), return_date = null WHERE rentbooks.book_id = " + bookId;
        
        jdbcTemplate.update(sql);
    }


    //rentbooksテーブルから本を取得しカウントする
    public int countRentBook(int bookId) {
    	
    	String sql = "SELECT count (book_id) from rentBooks where book_id = " + bookId;
    	
		return jdbcTemplate.queryForObject(sql,int.class);

	}   

}
