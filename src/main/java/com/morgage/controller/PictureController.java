package com.morgage.controller;

import com.morgage.model.Picture;
import com.morgage.service.PictureService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Configuration
@PropertySource(value = "classpath:messages.properties", encoding = "UTF-8")
@EnableConfigurationProperties
@Controller
public class PictureController {
    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/xoa-anh", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePic(@RequestParam("picId") Integer picId) {
        try {
            // chua lam
            pictureService.deletePicture(picId);
            return new ResponseEntity<Boolean>(true, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/chinh-sua-anh", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePic(@RequestParam("picId") Integer picId, @RequestParam(value = "picUrl", required = false) String picUrl,
                                       @RequestParam(value = "idCloud", required = false) String idCloud, @RequestParam(value = "deleteHash", required = false) String deleteHash) {
        try {
            Picture picture = pictureService.updatePicture(picId, picUrl, idCloud, deleteHash);
            return new ResponseEntity<Picture>(picture, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
