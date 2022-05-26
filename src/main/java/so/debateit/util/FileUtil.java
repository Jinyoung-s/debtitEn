/**
 * The FileUtil class implements the feature for making thumbnail image of post
 * and listing the post
 *
 * @author  Jinyoung So
 * @version 1.0
 * @since   5/15/2022
 */
package so.debateit.util;


import org.apache.commons.net.util.Base64;
import org.springframework.stereotype.Component;
import so.debateit.config.UserNotiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {

    public String fileMove(String fileStringData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
        String path = DebateEnvironment.uploadPath + current_date;
        String data = fileStringData.split(",")[1];
        String imgType =fileStringData.split(";")[0].split("/")[1];
        if("jpeg".equals(imgType)) {
            imgType = "jpg";
        }

        String fileName =  Long.toString(System.nanoTime()) + '.' + imgType;
        String fileUrl = path+ "/" + fileName;
        File file = new File(path);

        if(!file.exists()) {
            file.mkdir();
        }

        try {
            //byte[] imageBytes = DatatypeConverter.parseBase64Binary(data);

            byte[] binary = Base64.decodeBase64(data);
            FileOutputStream fos = null;
            fos = new FileOutputStream(fileUrl);
            fos.write ( binary , 0 , binary.length );
            fos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw  new UserNotiException("error.unknownServerError");
        }

        return current_date + "/" + fileName;
    }

    public String makeThumbNail(String oPath, String fileStringData) {
        File oFile = new File(oPath);

        int index = oPath.lastIndexOf(".");
        String ext = oPath.substring(index + 1);

        String tPath = oFile.getParent() + File.separator + "t-" + oFile.getName();
        File tFile = new File(tPath);
        String data = fileStringData.split(",")[1];

        try {
            BufferedImage oImage = ImageIO.read(oFile);
            int tWidth = 100;
            int tHeight = 100;

            BufferedImage tImage = new BufferedImage(tWidth, tHeight, BufferedImage.TYPE_3BYTE_BGR); // 썸네일이미지
            Graphics2D graphic = tImage.createGraphics();
            Image image = oImage.getScaledInstance(tWidth, tHeight, Image.SCALE_SMOOTH);
            graphic.drawImage(image, 0, 0, tWidth, tHeight, null);
            graphic.dispose();

            ImageIO.write(tImage, ext, tFile);
        } catch (IOException e) {
            return null;
        } catch (Exception ex) {
            return null;
        }

        return tPath;
    }
}
