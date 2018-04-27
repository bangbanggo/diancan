package edu.black.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SmartUpload {
    private long sizeMax;
    private String uploadpath;
    private String allowExtNames;
    private List<String> filenames;
    public SmartUpload(){
        sizeMax = 1024 * 1024 * 2;
        uploadpath = "image/";
        allowExtNames = "jpg,gif,bmp,png";
        filenames = new ArrayList<String>();
    }
    public void getload(ServletConfig servletConfig, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");//防止中文名乱码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int sizeThreshold=1024*6; //缓存区大小
        String basePath = servletConfig.getServletContext().getRealPath("/"+uploadpath);//get file path
        System.out.println(basePath);
        File repository = new File(basePath); //缓存区目录
        if (repository.exists()!=true){
            repository.mkdir();
        }
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();//硬盘文件项目工厂
        diskFileItemFactory.setRepository(repository);//设置缓冲区
        diskFileItemFactory.setSizeThreshold(sizeThreshold);//设置缓冲区大小
        ServletFileUpload servletFileUpload=new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setSizeMax(sizeMax);

        List<FileItem> fileItems = null;
        try{
            fileItems = servletFileUpload.parseRequest(request);

            for(FileItem fileItem:fileItems){
                long size=0;
                String filePath = fileItem.getName();
                System.out.println(filePath);
                if(filePath==null || filePath.trim().length()==0)
                    continue;
                String fileName=filePath.substring(filePath.lastIndexOf(File.separator)+1);
                String extName=filePath.substring(filePath.lastIndexOf(".")+1);
                if(allowExtNames.indexOf(extName)!=-1)
                {
                    try {
                        fileItem.write(new File(basePath+File.separator+fileName));
                        filenames.add(fileName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    throw new FileUploadException("file type is not allowed");
                }
            }
        }catch(FileUploadBase.FileSizeLimitExceededException e){
            System.out.println("file size is not allowed");
        }catch(FileUploadException e1){
            e1.printStackTrace();
        }
    }

    public long getSizeMax() {
        return sizeMax;
    }

    public void setSizeMax(long sizeMax) {
        this.sizeMax = sizeMax;
    }

    public String getUploadpath() {
        return uploadpath;
    }

    public void setUploadpath(String uploadpath) {
        this.uploadpath = uploadpath;
    }

    public String getAllowExtNames() {
        return allowExtNames;
    }

    public void setAllowExtNames(String allowExtNames) {
        this.allowExtNames = allowExtNames;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
}
