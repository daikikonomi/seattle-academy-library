package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentBookService;



	
	@Controller //APIの入り口
	public class RentHistoryController {
	    final static Logger logger = LoggerFactory.getLogger(RentHistoryController.class);
	    
	    @Autowired
	    private RentBookService rentBookService;
	    
	    @Autowired
	    private BooksService booksService;

	    @RequestMapping(value = "/rentHistory", method = RequestMethod.GET) //value＝actionで指定したパラメータ
	    //RequestParamでname属性を取得
	    public String rentHistory(Model model) {
	    	model.addAttribute("rentHistoryList", rentBookService.getRentBookInfo());
	        return "rentHistory";
	    }
	    
	    @RequestMapping(value = "/showHistory", method = RequestMethod.GET) //value＝actionで指定したパラメータ
	    //RequestParamでname属性を取得
	    public String showRentHistory(Model model, 
	    		@RequestParam("bookId") int bookId) {
	    	model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
	        return "details";
	    }
	    
	    
	}

