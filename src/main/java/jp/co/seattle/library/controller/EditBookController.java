package jp.co.seattle.library.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.ThumbnailService;

@Controller //APIの入り口
public class EditBookController {
	final static Logger logger = LoggerFactory.getLogger(EditBookController.class);
    
	@Autowired
    private BooksService booksService;

    @Autowired
    private ThumbnailService thumbnailService;
    
    /**
     * 書籍情報を編集する
     * @param locale
     * @param bookId
     * @param model
     * @return edit画面に遷移
     */
    
    @RequestMapping(value = "/editBook", method = RequestMethod.POST)
    public String editBook(Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        // デバッグ用ログ
    	logger.info("Welcome detailsControler.java! The client locale is {}.", locale);

        model.addAttribute("bookeditInfo", booksService.getBookInfo(bookId));
        return "editBook";
    }
    
    /**
     * 書籍情報を更新する
     * @param locale
     * @param bookId
     * @param title
     * @param author
     * @param publisher
     * @param publish_date
     * @param explanation
     * @param isbn
     * @param file
     * @param model
     * @return 遷移先画面
     */
    @Transactional
    @RequestMapping(value = "/updateBook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    public String updateBook(Locale locale,
    		@RequestParam("bookId") Integer bookId,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("publisher") String publisher,         
            @RequestParam("publish_date") String publish_date,
            @RequestParam("explanation") String explanation,
            @RequestParam("isbn") String isbn,          
            @RequestParam("thumbnail") MultipartFile file,
            Model model) {
        logger.info("Welcome updateBooks.java! The client locale is {}.", locale);

        // パラメータで受け取った書籍情報をDtoに格納する。
        BookDetailsInfo bookInfo = new BookDetailsInfo();
        bookInfo.setBookId(bookId);
        bookInfo.setTitle(title);
        bookInfo.setAuthor(author);
        bookInfo.setPublisher(publisher);
        bookInfo.setPublish_date(publish_date);
        bookInfo.setIsbn(isbn);
        bookInfo.setExplanation(explanation);

        // クライアントのファイルシステムにある元のファイル名を設定する
        String thumbnail = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                // サムネイル画像をアップロード
                String fileName = thumbnailService.uploadThumbnail(thumbnail, file);
                // URLを取得
                String thumbnailUrl = thumbnailService.getURL(fileName);

                bookInfo.setThumbnailName(fileName);
                bookInfo.setThumbnailUrl(thumbnailUrl);

            } catch (Exception e) {

                // 異常終了時の処理
                logger.error("サムネイルアップロードでエラー発生", e);
                model.addAttribute("bookDetailsInfo", bookInfo);
                return "editBook";
            }
        }
        
        List<String> list = new ArrayList<String>();
        
        // バリデーションチェック
        if(bookInfo.getTitle().isEmpty() || bookInfo.getAuthor().isEmpty() || bookInfo.getPublisher().isEmpty() || bookInfo.getPublish_date().isEmpty()) {
        	list.add("必須項目を入力してください");
        } 
        
        if(!(bookInfo.getPublish_date().length()==8 && bookInfo.getPublish_date().matches("^[0-9]+$"))) {
        	list.add("出版日は半角数字のYYYYMMDD形式で入力してください");
        }
        
       
        if(!(bookInfo.getIsbn().length()==10 || bookInfo.getIsbn().length() == 13 || bookInfo.getIsbn().length() == 0)) {
            list.add("ISBNの桁数または半角数字が正しくありません");
        }
        
        if(list.size() == 0) {
        	booksService.updateBook(bookInfo);
        	model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookInfo.getBookId()));
        	model.addAttribute("resultMessage", "登録完了");
        	return "details";
        
        }else {
        	model.addAttribute("errorMessages", list);
        	model.addAttribute("bookeditInfo", bookInfo);
        	return "editBook";
        }
       
    }
    
}