package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;

/**
 * 書籍サービス
 * 
 *  booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
    final static Logger logger = LoggerFactory.getLogger(BooksService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 書籍リストを取得する
     * @return 書籍リスト
     */
    public List<BookInfo> getBookList() {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> getedBookList = jdbcTemplate.query(
                "select id,title,author,publisher,publish_date,thumbnail_url from books order by title asc",
                new BookInfoRowMapper());

        return getedBookList;
    }
    
    
    public List<BookInfo> searchBookList(String searchedTitle) {

        // TODO 取得したい情報を取得するようにSQLを修正
        List<BookInfo> searchedBookList = jdbcTemplate.query(
                "SELECT * FROM books left join rentbooks on books.id = rentBooks.book_id WHERE title like '%" + searchedTitle + "%'",
                new BookInfoRowMapper());

        return searchedBookList;
    }

    /**
     * 書籍IDに紐づく書籍詳細情報を取得する
     *
     * @param bookId 書籍ID
     * @return 書籍情報
     */
    public BookDetailsInfo getBookInfo(int bookId) {

        // JSPに渡すデータを設定する
        String sql = "select * from books left join rentbooks on books.id = rentBooks.book_id where books.id = " + bookId;

        BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

        return bookDetailsInfo;
    }

    /**
     * 最新の書籍情報を取得する
     *
     * @return 最新書籍情報
     */
    public BookDetailsInfo getLatestBookInfo() {

     
    	String sql = "select * from books left join rentbooks on books.id = rentBooks.book_id where id = (select max (id) from books);";
                

        BookDetailsInfo latestBookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());

        return latestBookDetailsInfo;
    }
    
    
    /**
     * 書籍を登録する
     *
     * @param bookInfo 書籍情報
     */
    public void registBook(BookDetailsInfo bookInfo) {

        String sql = "INSERT INTO books (title, author,publisher,thumbnail_name,thumbnail_url,reg_date,upd_date,publish_date,isbn,explanation) VALUES ('"
                + bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
                + bookInfo.getThumbnailName() + "','"
                + bookInfo.getThumbnailUrl() + "',"
                + "now(),"
                + "now(),'"
                + bookInfo.getPublish_date() + "','"
                + bookInfo.getIsbn() + "','"
                + bookInfo.getExplanation() + "')" ;

        jdbcTemplate.update(sql);
    }
    
    /**
     * 書籍を削除する
     *
     * @param bookID 書籍ID
     * @return home
     */
    public void deleteBook(int bookId) {
    	
    	String sql = "delete from books where id = " + bookId + ";";
    	jdbcTemplate.update(sql);
    	
    	
    	

    }
    

    /**
     * 書籍を編集する
     * 
     */
    public void updateBook(BookDetailsInfo bookInfo) {
    	
    	String sql = "UPDATE books SET title ='" + bookInfo.getTitle() + "', author = '" + bookInfo.getAuthor() + "', publisher = '" +  bookInfo.getPublisher() 
    	+ "', publish_date = '" + bookInfo.getPublish_date() + "', thumbnail_url ='" + bookInfo.getThumbnailUrl() + "', isbn ='"  + bookInfo.getIsbn() 
        + "', upd_date = now(), explanation ='" + bookInfo.getExplanation() + "' Where id = " + bookInfo.getBookId();
    	
    	jdbcTemplate.update(sql);
    	
    }
    
    /**
     * 書籍を編集する
     * 
     */
    public void editbook(BookDetailsInfo bookInfo) {
    	
    	String sql = "UPDATE books" 
    			+ "SET title = '" + bookInfo.getTitle() + "',"
    			+ "author = '" + bookInfo.getAuthor() + "',"
    			+ "publisher = '" + bookInfo.getPublisher() + "',"
    			+ "publish_date = '" + bookInfo.getPublish_date() + "',"
    			+ "explanation = '" + bookInfo.getPublish_date() + "',"
    			+ "isbn = '" + bookInfo.getPublish_date() + "',"
    			+ "thumbnail_url = '" + bookInfo.getPublish_date() + "',"
    			+ "reg_date=now(),"
    			+ "upd_date=now()"
    			+ "where id = " + bookInfo.getBookId() ;
    	
    	jdbcTemplate.update(sql);
    	
    }
    
    
}
