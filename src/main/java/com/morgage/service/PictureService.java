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

    public List<Picture> findAllByObjectIdAndStatus(int objId, int status) {

        return pictureRepository.findAllByObjectIdAndStatus(objId, status);
    }

    public Picture savePicture(String picUrl, int objId, int status, String idCloud, String deleteHash) {
        Picture picture = new Picture();
        picture.setPictureUrl(picUrl);
        picture.setObjectId(objId);
        picture.setStatus(status);
        picture.setIdCloud(idCloud);
        picture.setDeleteHash(deleteHash);
        return pictureRepository.saveAndFlush(picture);
    }

    public Picture updatePicture(int picId, String picUrl, String idCloud, String deleteHash) {
        Picture picture = pictureRepository.findById(picId);
        if (picture != null) {
            picture.setPictureUrl(picUrl);
            picture.setIdCloud(idCloud);
            picture.setDeleteHash(deleteHash);
            return pictureRepository.saveAndFlush(picture);
        }
        return null;
    }

    public void deletePicture(int picId) {
        pictureRepository.deleteById(picId);
    }
}
