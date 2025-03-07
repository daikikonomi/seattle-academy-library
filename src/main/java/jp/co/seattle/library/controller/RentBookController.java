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
    		//@RequestParam("title") String title,
    		//@RequestParam("rentDate") String rentDate,
    		//@RequestParam("returnDate") String returnDate,
    		Model model) {
    	
    	logger.info("Welcome rentBook! The client locale is {}.", locale);


    	
    	CirculationHistoryInfo CirculationHistoryInfo = rentBookService.selectRentHistoryInfo(bookId);
    	
    	
    	if (CirculationHistoryInfo == null) {
    		rentBookService.rentBook(bookId);
    	}
    	else {
    		if(CirculationHistoryInfo.getRentDate() == null) {
    			rentBookService.updateStatus(bookId);
    		}
    		else {
    			model.addAttribute("errorMessage","貸出し済みです" );
    		}
    	}
    	
    	model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
    	return "details";

    	}

}
