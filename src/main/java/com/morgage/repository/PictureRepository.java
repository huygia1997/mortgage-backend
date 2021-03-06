package com.morgage.repository;

import com.morgage.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture,Integer> {
    List<Picture> findAllByObjectId(int transId);
    List<Picture> findAllByObjectIdAndStatus(int objId, int status);
    void deleteById(int picId);
    Picture findById(int picId);
}
