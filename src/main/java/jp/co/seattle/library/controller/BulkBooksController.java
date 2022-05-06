package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

@Controller 
public class BulkBooksController {
	final static Logger logger = LoggerFactory.getLogger(BulkBooksController.class);
	
	
	@Autowired
    private BooksService booksService;
	
	//[一括登録]ボタンを押すと画面遷移する
	@RequestMapping(value = "/bulkBooks", method = RequestMethod.GET) //value＝actionで指定したパラメータ
    //RequestParamでname属性を取得
    public String bulkBooks(Locale locale, Model model) {
		
		// デバッグ用ログ
    	logger.info("Welcome bulkBooksControler.java! The client locale is {}.", locale);
        return "bulkBooks";
    }
	
	//CSVファイルの読み込み&バリデーションチェック
	@Transactional
	@RequestMapping(value = "/bulkRegistBooks", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	  public String bulkRegistBooks(Locale locale, @RequestParam("upload_file") MultipartFile uploadFile, Model model) {
	    
		// デバッグ用ログ
    	logger.info("Welcome bulkBooksControler.java! The client locale is {}.", locale);
 
    	List<BookDetailsInfo> bookLists = new ArrayList<BookDetailsInfo>();
    	List<String> errorLists = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(uploadFile.getInputStream(), StandardCharsets.UTF_8))){
	      String line;
	      line = br.readLine();
	      int count = 1;
	      
	      //データが無くなるまで、繰り返し実施
	      while (!StringUtils.isEmpty(line)) {
	    	
	        final String[] split = line.split(",",-1);

	        
	        //値をDtoに保管
	        final BookDetailsInfo bookInfo = new BookDetailsInfo();
	        bookInfo.setTitle(split[0]);
	        bookInfo.setAuthor(split[1]);
	        bookInfo.setPublisher(split[2]);
	        bookInfo.setPublish_date(split[3]);
	        bookInfo.setIsbn(split[4]);	        	        
	        bookInfo.setExplanation(split[5]);	
	        
	        // バリデーションチェック
	        boolean repuiredCheck = split[0].isEmpty() || split[1].isEmpty() || split[2].isEmpty() || split[3].isEmpty() ;
	        boolean publishDateCheck = !(split[3].length() == 8 && split[3].matches("^[0-9]+$")) ;
	        boolean isbnCheck = !(split[4].length()==10 || split[4].length() == 13 || split[4].length() == 0) ;
	        
	        
	        if(repuiredCheck || publishDateCheck || isbnCheck ) {
	        	errorLists.add(count + "行目でエラーが発生しました");
	        }else {
	        	bookLists.add(bookInfo);
	        }
	        
	        count ++;
	        line = br.readLine();
	         
	      	}if(bookLists.isEmpty()) {
	      		model.addAttribute("emptyFailuedMessage","CSVに書籍情報がありません。" );
	      		return "bulkBooks";
	      	}
	      
	    } catch (IOException e) {
	      throw new RuntimeException("ファイルが読み込めません", e);
	    }
		
		if(errorLists.size() == 0) {
			for (BookDetailsInfo bookInfo : bookLists) {
	        	booksService.registBook(bookInfo);
			}
			model.addAttribute("bookList", booksService.getBookList());
        	return "home";
        
        }else {
        	model.addAttribute("errorMessages", errorLists);
        	return "bulkBooks";
        }
	
	  }
	
}
