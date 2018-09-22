package utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import utils.file.img.ImageUploadTool;
import utils.uuid.UUIDTool;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @description: 文件处理
 * @author: Fearon
 * @create: 2018/6/7 11:34
 **/
public class FileTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTool.class);

    /**
     * 多文件数据流截取
     * @param request
     * @return
     */
    public static List<MultipartFile> getFiles(HttpServletRequest request){
        // 创建文件容器
        List<MultipartFile> files = new ArrayList<>();
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
        // 设置默认编码
        //multipartResolver.setDefaultEncoding("UTF-8");

        // 判断是否存在请求数据流
        if(multipartResolver.isMultipart(request)){
            // 将请求数据流进行分割
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            // 获取所有的文件名
            Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
            while(fileNames.hasNext()){
                files.add(multipartHttpServletRequest.getFile(fileNames.next()));
            }
        }

        return files;
    }

    /**
     * 文件上传（多文件）
     * @param request 请求数据
     * @return
     */
    public static Map<String, Object> uploadFils(HttpServletRequest request, String realPath) {
        String type;
        // 获取上传的文件对象
        List<MultipartFile> files = getFiles(request);
        if(null == files || files.size() == 0) {
            LOGGER.error("未获取到有效的文件数据流！");
            return null;
        }

        MultipartFile fileCache;
        StringBuilder path = new StringBuilder();
        Map<String, Object> all_path = new HashMap<>();
        int i=0, length=files.size();
        for(; i<length; i++){
            fileCache = files.get(i);
            // 获取文件类型
            String fullName = fileCache.getOriginalFilename();
            String fileName = fullName.substring(0, fullName.lastIndexOf('.'));
            type = fullName.substring(fullName.lastIndexOf('.'));
            if(!ImageUploadTool.limitImgFormat(type)) {
                LOGGER.error("不可上传此类型图片！");
                return null;
            }
            if(!ImageUploadTool.limitImgSize(fileCache.getSize())) {
                LOGGER.error("该图片过大！");
                return null;
            }

            try {
                path.append(UUIDTool.getUUID());
                path.append(type);
                realPath = request.getSession().getServletContext().getRealPath(realPath);
                if(isDirectory(realPath)) {
                    File distFile = new File(realPath + path.toString());
                    fileCache.transferTo(distFile);
                    all_path.put(fileName, path);
                }
            } catch (IOException e) {
                LOGGER.error("图片上传失败！", (Object[]) e.getStackTrace());
                return null;
            }
        }

        return all_path;
    }

    /**
     * 文件上传单文件
     * @param request 请求数据
     * @return
     */
    public static String uploadFile(HttpServletRequest request, String realPath) {
        String type;
        // 获取上传的文件对象
        List<MultipartFile> files = getFiles(request);
        if(null == files || files.size() == 0) {
            LOGGER.error("未获取到有效的文件数据流！");
            return null;
        }

        MultipartFile fileCache;
        StringBuilder path = new StringBuilder();
        StringBuilder all_path = new StringBuilder();
        fileCache = files.get(0);
        // 获取文件类型
        String fullName = fileCache.getOriginalFilename();
        type = fullName.substring(fullName.lastIndexOf('.'));
        if(!ImageUploadTool.limitImgFormat(type)) {
            LOGGER.error("不可上传此类型图片！");
            return null;
        }
        if(!ImageUploadTool.limitImgSize(fileCache.getSize())) {
            LOGGER.error("该图片过大！");
            return null;
        }

        try {
            path.append(UUIDTool.getUUID());
            path.append(type);
            all_path.append(realPath);
            realPath = request.getSession().getServletContext().getRealPath(realPath);
            isDirectory(realPath);
            File distFile = new File(realPath + path.toString());
            fileCache.transferTo(distFile);
            all_path.append(path);
        } catch (IOException e) {
            LOGGER.error("图片上传失败！", (Object[]) e.getStackTrace());
            return null;
        }

        return all_path.toString();
    }

    /**
     * 判断目标路径是否存在，若不存在则进行创建
     * @param customizePath 自定义路径
     */
    public static boolean isDirectory(String customizePath){
        return new File(customizePath).mkdirs();
    }
}
