package springmvc_example.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class FileDownloadController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() throws Exception {
		if(1==1)throw new Exception("Test Erro");
		System.out.println("111111111");
		return "/customer/index";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(1==1)throw new IOException("Test IOException");
		
		 File file = new File("C:\\files\\koala.jpg"); 
		
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		String mimeType = URLConnection.guessContentTypeFromStream(inputStream);

		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));

		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

	@ExceptionHandler(IOException.class)
	public ModelAndView handleErrors(Exception ex) {
		ModelAndView model = new ModelAndView("/customer/error");
		model.addObject("exception", ex);
		return model;

	}

}
