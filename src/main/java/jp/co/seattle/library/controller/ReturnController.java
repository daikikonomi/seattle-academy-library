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

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBookService;

@Controller
public class ReturnController {
	final static Logger logger = LoggerFactory.getLogger(ReturnController.class);
	
	@Autowired
    private BooksService booksService;
	
	@Autowired
    private RentBookService rentBookService;
	
	
	/**
	 * 返却機能の実装 
	 * 
	 * @param locale
	 * @param bookId
	 * @param model
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/returnBook", method = RequestMethod.POST)
	public String returnBook
		(Locale locale,
		@RequestParam("bookId") int bookId,
		Model model) {
		 
		logger.info("Welcome returnBook! The client locale is {}.", locale);
		
		int count = rentBookService.getRentBook();
    	rentBookService.returnBook(bookId);
    	int count2 = rentBookService.getRentBook();
    	

    	//本の存在チェック
    	if (count == count2) {
    		model.addAttribute("errorMessage","貸出されていません" );
    	}
	    model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
	    return "details";
	 }
}
