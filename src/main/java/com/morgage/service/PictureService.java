package com.morgage.service;

import com.morgage.model.Picture;
import com.morgage.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {
    private final PictureRepository pictureRepository;

    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> getAllPicturesByTransId(int transId, int status) {

        return pictureRepository.findAllByObjectIdAndStatus(transId, status);
    }

    public Picture savePictureOfTransaction(String picUrl, int transId, int status) {
        Picture picture = new Picture();
        picture.setPictureUrl(picUrl);
        picture.setObjectId(transId);
        return pictureRepository.saveAndFlush(picture);
    }
}
