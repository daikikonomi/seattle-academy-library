package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.BooksService;

@Controller
public class SearchBookController {
	final static Logger logger = LoggerFactory.getLogger(SearchBookController.class);
	
	@Autowired
    private BooksService booksService;
	
	
	/**
	 * 検索されたワードに一致する文書を検索する
	 * 
	 * @param model
	 * @return　書籍一覧画面
	 */
	@RequestMapping(value = "/searchBook", method = RequestMethod.POST)
    public String searchBook(Locale locale, Model model, String searchedTitle) {
		
		// デバッグ用ログ
        logger.info("Welcome createAccount! The client locale is {}.", locale);

		model.addAttribute("bookList", booksService.searchBookList(searchedTitle));
		return "home";
	}

	
	
    
}
