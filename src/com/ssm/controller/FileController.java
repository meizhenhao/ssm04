package com.ssm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ssm.pojo.MutipleFileDomain;
import com.ssm.pojo.OneFileDomain;

@Controller
public class FileController {
	private final static Log logger = LogFactory.getLog(FileController.class);

	// 单个文件上传页面，跳转
	@RequestMapping("/file/toUploadOneFile")
	public String toUploadOneFile(Model model) {
		// OneFileDomain oneFileDomain = new OneFileDomain();
		// model.addAttribute("oneFileDomain", oneFileDomain);
		return "oneFile";
	}

	// 多个文件上传页面，跳转
	@RequestMapping("/file/toUploadMutipleFile")
	public String toUploadMutipleFile(Model model) {
		// MutipleFileDomain mutipleFileDomain = new MutipleFileDomain();
		// model.addAttribute("mutipleFileDomain", mutipleFileDomain);
		return "mutipleFile";
	}

	// 单个文件上传处理，并保存到tomcat服务器中去
	@RequestMapping("file/saveOneFile")
	public String saveOneFile(@ModelAttribute OneFileDomain oneFileDomain, HttpServletRequest request, Model model) {
		// 0.获取上传文件的原始名称
		String originName = oneFileDomain.getMyFile().getOriginalFilename();
		// 1.获取文件保存的实际位置
		// E:\java_program_SSM\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ssm04
		String realPath = request.getServletContext().getRealPath("/") + "upload" + "\\" + originName;

		// 判断文件大小，是否为空文件
		if (oneFileDomain.getMyFile().getSize() <= 0) {
			logger.info("空文件！");
			model.addAttribute("fileError", "上传的是空文件！");
			return "oneFile";
		}
		// 判断文件是否是jpg、png、bmp格式图片
		if (!(originName.endsWith("jpg") || originName.endsWith("png") || originName.endsWith("bmp"))) {
			logger.info("上传的文件不是jpg、png、bmp格式图片！");
			model.addAttribute("fileError", "上传的文件不是jpg、png、bmp格式图片！");
			return "oneFile";
		}

		// 2.以realPath创建一个文件
		File file = new File(realPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			oneFileDomain.getMyFile().transferTo(file);
			logger.info("单个文件，上传成功！");
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "showOneFile";
	}

	// 多个文件上传处理，并保存到tomcat服务器中去
	@RequestMapping("file/saveMutipleFile")
	public String saveMutipleFile(@ModelAttribute MutipleFileDomain mutipleFileDomain, HttpServletRequest request,
			Model model) {

		// 设置上传文件的保存位置
		String realPath = request.getServletContext().getRealPath("/") + "upload";
		File fileDir = new File(realPath);
		if (fileDir.exists()) {
			fileDir.mkdirs();
		}

		List<MultipartFile> multipartFiles = mutipleFileDomain.getMyFiles();
		for (int i = 0; i < multipartFiles.size(); i++) {
			MultipartFile oneOfFile = multipartFiles.get(i);
			String fileName = oneOfFile.getOriginalFilename();
			File targetFile = new File(fileDir, fileName);

			if (oneOfFile.getSize() <= 0) {
				logger.info(fileName + "是个空文件！");
				model.addAttribute("fileError", fileName + "是个空文件！");
				return "mutipleFile";
			}

			// 判断文件是否是jpg、png、bmp格式图片
			if (!(fileName.endsWith("jpg") || fileName.endsWith("png") || fileName.endsWith("bmp"))) {
				logger.info(fileName + "上传的文件不是jpg、png、bmp格式图片！");
				model.addAttribute("fileError", fileName + "文件不是jpg、png、bmp格式图片！");
				return "mutipleFile";
			}

			try {
				oneOfFile.transferTo(targetFile);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		logger.info("多文件上传成功！");
		return "showMutipleFile";
	}

	// 文件的下载
	@RequestMapping(value = "/download")
	public void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//浏览器中传递过来的fileName编码是"ISO8859-1"，需要解码之后，在使用utf-8编码
		String fileName = request.getParameter("filename");
		fileName = new String(fileName.getBytes("ISO8859-1"), "utf-8");
		String realPath = request.getSession().getServletContext().getRealPath("/upload");
		String filePath = realPath + File.separator + fileName;
		
		//使用二进制流方式读取文件BufferedInputStream
		InputStream fis = null;
		byte[] buffer = null;
		try {
			
			//将文件内容以字节流方式读到buffer字节数组中去
			fis = new BufferedInputStream(new FileInputStream(filePath));
			buffer = new byte[fis.available()];
			fis.read(buffer);
			
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (Exception e) {
			}
		}
		
		response.setContentType("application/txt");
		response.addHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes(), "ISO8859-1"));
		response.addHeader("Content-Length", "" + buffer.length);
		
		//使用二进制流方式将文件输出到response.getOutputStream()
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		os.write(buffer, 0, buffer.length);
		os.flush();
		os.close();
	}

	/*
	 * public ResponseEntity<byte[]> download(HttpServletRequest request,
	 * 
	 * @RequestParam("filename") String filename, Model model)throws Exception {
	 * 
	 * //下载文件路径 String path =
	 * request.getServletContext().getRealPath("/upload/"); File file = new
	 * File(path + File.separator + filename); HttpHeaders headers = new
	 * HttpHeaders();
	 * 
	 * //下载显示的文件名，解决中文名称乱码问题 按 iso-8859-1进行解码 然后再按UTF-8进行编码 //String
	 * downloadFielName = new String(filename); String downloadFielName = new
	 * String(filename.getBytes("iso-8859-1"),"utf-8");
	 * System.out.println(downloadFielName); //通知浏览器以attachment（下载方式）打开图片
	 * headers.setContentDispositionFormData("attachment", downloadFielName);
	 * 
	 * //application/octet-stream ： 二进制流数据（最常见的文件下载）。
	 * headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	 * 
	 * return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
	 * headers, HttpStatus.CREATED); }
	 */

}
