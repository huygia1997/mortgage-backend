package com.morgage.repository;

import com.morgage.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByReceiverIdOrderByCreateTimeDesc(int receiverId);
    List<Notification> findAllByReceiverIdAndStatusOrderByCreateTimeDesc(int receiverId, int status);

    Notification findById(int id);
}
