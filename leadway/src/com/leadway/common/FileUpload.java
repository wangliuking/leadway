package com.leadway.common;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
/**
 * 文件上传公共操作类
 * @author Administrator
 *
 */
public class FileUpload {
	/**
	 * 文件上传公共方法
	 * @param file
	 * @param session
	 * @return
	 * @throws IOException
	 */
	public static String imgsUpload(MultipartFile file, HttpSession session,String savePath)
			throws IOException {
		//获取文件在服务器的存储路径
		String path = session.getServletContext().getRealPath(savePath);
		//获取上传文件的名称
		String fileName = file.getOriginalFilename();
		//进行文件存储
		file.transferTo(new File(path,fileName));
		return savePath+fileName;
	}
}
