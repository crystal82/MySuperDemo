package xhh.android.com.xhh;

import android.util.Log;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018-02-10.
 */

public class SampleUsage {

    /**
     * 图片信息获取metadata元数据信息
     * @param fileName 需要解析的文件
     * @return
     */
    public ImgInfoBean parseImgInfo (String fileName)
    {
        File file = new File(fileName);
        ImgInfoBean imgInfoBean = null;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            imgInfoBean = printImageTags(file, metadata);
        } catch (ImageProcessingException e) {
            System.err.println("error 1a: " + e);
        } catch (IOException e) {
            System.err.println("error 1b: " + e);
        }
        return imgInfoBean;
    }

    /**
     * 读取metadata里面的信息
     * @param sourceFile 源文件
     * @param metadata metadata元数据信息
     * @return
     */
    private ImgInfoBean printImageTags(File sourceFile, Metadata metadata)
    {
        ImgInfoBean imgInfoBean = new ImgInfoBean ();
        imgInfoBean.setImgSize(sourceFile.getTotalSpace());
        imgInfoBean.setImgName(sourceFile.getName());
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();
                String desc = tag.getDescription();
                Log.d("printImageTags", "tagName:" + tagName + "  desc:" + desc);
                if (tagName.equals("Image Height")) {
                    //图片高度
                    imgInfoBean.setImgHeight(desc);
                } else if (tagName.equals("Image Width")) {
                    //图片宽度
                    imgInfoBean.setImgWidth(desc);
                } else if (tagName.equals("Date/Time Original")) {
                    //拍摄时间
                    imgInfoBean.setDateTime(desc);
                } else if (tagName.equals("GPS Altitude")) {
                    //海拔
                    imgInfoBean.setAltitude(desc);
                } else if (tagName.equals("GPS Latitude")) {
                    //纬度
                    imgInfoBean.setLatitude(pointToLatlong(desc));
                } else if (tagName.equals("GPS Longitude")) {
                    //经度
                    imgInfoBean.setLongitude(pointToLatlong(desc));
                }
            }
            for (String error : directory.getErrors()){
                System.err.println("ERROR: " + error);
            }
        }
        return imgInfoBean;
    }

    /**
     * 经纬度转换  度分秒转换
     * @param point 坐标点
     * @return
     */
    public String pointToLatlong (String point ) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°")+1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'")+1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60 ;
        return duStr.toString();
    }
}
