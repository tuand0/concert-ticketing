package fr.istic.taa.jaxrs.dao;

import fr.istic.taa.jaxrs.dao.generic.AbstractJpaDao;
import fr.istic.taa.jaxrs.domain.Notification;
import fr.istic.taa.jaxrs.domain.Utilisateur;

import java.util.List;

public class NotificationDao extends AbstractJpaDao<Long, Notification> {

    public NotificationDao() {
        super(Notification.class);
    }

    public List<Notification> findByUserId(Long userId) {
        return getEntityManager()
                .createQuery("""
                    SELECT DISTINCT n
                    FROM Notification n
                    JOIN n.utilisateurs u
                    WHERE u.id = :userId
                    ORDER BY n.dateEnvoi DESC
                """, Notification.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public void save(Notification notification) {
        getEntityManager().persist(notification);
    }
}
