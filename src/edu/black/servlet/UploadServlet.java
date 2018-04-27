package edu.black.servlet;

import com.jspsmart.upload.SmartUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UploadServlet",urlPatterns = "/upload")
public class UploadServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        SmartUpload smartUpload = new SmartUpload();
//        smartUpload.getload(getServletConfig(),request,response);
//        List<String> filenames  = smartUpload.getFilenames();
//        response.getWriter().print(filenames.get(filenames.size()-1));

        PrintWriter out = response.getWriter();
        try {
            //创建上传下载工具类对象
            SmartUpload su = new SmartUpload();
            // 上传初始化
            su.initialize(getServletConfig(), request, response);
            // 设定上传限制
            // 1)限制每个上传文件的最大长度。
            su.setMaxFileSize(1024 * 1024 * 10);
            // 2)限制总上传数据的长度。
            su.setTotalMaxFileSize(1024 * 1024 * 20);
            // 3)设定允许上传的文件（通过扩展名限制）,仅允许doc,txt文件。
            //su.setAllowedFilesList("doc,txt,exe");
            // 4)设定禁止上传的文件（通过扩展名限制）,禁止上传带有exe,bat,jsp,htm,html扩展名的文件和没有扩展名的文件。
            //su.setDeniedFilesList("bat,jsp,htm,html,,");
            // 上传文件
            su.upload();
            //获取文本框的值
            //String n = su.getRequest().getParameter("userName");
            //System.out.println(n);
            //获取上传文件的文件名
            //String fileName=su.getFiles().getFile(0).getFileName();
            //System.out.println(fileName);
            // 将上传文件全部保存到指定目录
            int count = su.save("/image");
            //4    在webroot目录中新建upload文件夹
            out.println(su.getFiles().getFile(count-1).getFileName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
