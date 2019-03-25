package com.morgage.service;

import com.morgage.common.Const;
import com.morgage.model.Notification;
import com.morgage.model.data.NotificationData;
import com.morgage.repository.NotificationRepository;
import com.morgage.utils.Util;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(String message, int type, int receiverId, Integer objectId) {
        Notification notification = new Notification();
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        notification.setCreateTime(timeStamp);
        notification.setMessage(message);
        notification.setReceiverId(receiverId);
        notification.setType(type);
        notification.setStatus(Const.NOTIFICATION_STATUS.NOT_SEEN.value);
        if (objectId != null) {
            notification.setObjectId(objectId);
        }
        return notificationRepository.saveAndFlush(notification);
    }

    public List<NotificationData> getAllNotification(int receiverId) {
        List<Notification> list = notificationRepository.findAllByReceiverIdOrderByCreateTimeDesc(receiverId);
        List<NotificationData> rs = new ArrayList<>();
        for (Notification notification : list) {
            NotificationData data = getNotificationDataByNotification(notification);
            rs.add(data);
        }
        return rs;
    }
    public List<NotificationData> getNewAllNotification(int receiverId, int status) {
        List<Notification> list = notificationRepository.findAllByReceiverIdAndStatusOrderByCreateTimeDesc(receiverId,status);
        List<NotificationData> rs = new ArrayList<>();
        for (Notification notification : list) {
            NotificationData data = getNotificationDataByNotification(notification);
            rs.add(data);
        }
        return rs;
    }

    private NotificationData getNotificationDataByNotification(Notification notification) {
        NotificationData rs = new NotificationData();
        rs.setMessage(notification.getMessage());
        rs.setObjectId(notification.getObjectId());
        rs.setDateCreate(Util.getTimeAgo(notification.getCreateTime().toString()));
        rs.setStatus(notification.getStatus());
        rs.setType(notification.getType());
        return rs;
    }

    public void changeNotificationStatus(int notificationId) {
        Notification notification = notificationRepository.findById(notificationId);
        notification.setStatus(Const.NOTIFICATION_STATUS.SEEN.value);
        notificationRepository.save(notification);
    }

}
