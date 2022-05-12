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
public class RentBookController {
	final static Logger logger = LoggerFactory.getLogger(RentBookController.class);
	
	@Autowired
    private BooksService booksService;
	
	@Autowired
    private RentBookService rentBookService;

    /**
     * 借りる機能の実装
     * 
     * @param locale
     * @param bookId
     * @param model
     * @return 詳細画面
     */
    @Transactional
    @RequestMapping(value = "/rentBook", method = RequestMethod.POST)
    public String rentBook
    		(Locale locale,
    		@RequestParam("bookId") int bookId,
    		Model model) {
    	
    	logger.info("Welcome delete! The client locale is {}.", locale);
    	
    	
    	int countBeforeRent = rentBookService.getRentBook();
    	rentBookService.rentBook(bookId);
    	int countAfterRent = rentBookService.getRentBook();
    	
    	//本の存在チェック
    	if (countBeforeRent == countAfterRent) {
    		
    		model.addAttribute("errorMessage","貸出し済みです" );
    		
    	}
    	
    	model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
    	return "details";

    	}

}
