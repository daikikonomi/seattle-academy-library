package jp.co.seattle.library.controller;

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

import jp.co.seattle.library.dto.CirculationHistoryInfo;
import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBookService;

/**
 * 削除コントローラー
 *  
 * 
 */
@Controller //APIの入り口
public class DeleteBookController {
    final static Logger logger = LoggerFactory.getLogger(DeleteBookController.class);
    
   
	
	@Autowired
    private RentBookService rentBookService;

	@Autowired
	private BooksService booksService;   
    
    /**
     * 対象書籍を削除する
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    public String deleteBook(
            Locale locale,
            @RequestParam("bookId") int bookId,
            Model model) {
    	
    	logger.info("Welcome delete! The client locale is {}.", locale);
    	
    	
		CirculationHistoryInfo CirculationHistoryInfo = rentBookService.selectRentHistoryInfo(bookId);

    	//rentBooksテーブルに指定された書籍が存在するか確認
    	if (CirculationHistoryInfo == null) {
    	    booksService.deleteBook(bookId);
    		model.addAttribute("bookList", booksService.getBookList());
    		return "home";
    	}
    	else {
    		if(CirculationHistoryInfo.getRentDate() == null ) {
    			booksService.deleteBook(bookId);
        		model.addAttribute("bookList", booksService.getBookList());
        		return "home";
			}
    		else {
    			model.addAttribute("errorMessage","貸出し中の書籍です" );	
    			model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
    			return "details";
			}
    		
    	}


	}
	
}
