package fr.istic.taa.jaxrs.services;

import fr.istic.taa.jaxrs.dao.NotificationDao;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.domain.Concert;
import fr.istic.taa.jaxrs.domain.Notification;
import fr.istic.taa.jaxrs.domain.Utilisateur;
import fr.istic.taa.jaxrs.domain.enums.TypeNotificationEnum;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationService {

    private final NotificationDao notificationDao = new NotificationDao();

    public List<Notification> findByUserId(Long userId) {
        try {
            EntityManagerHelper.beginTransaction();

            List<Notification> notifications = notificationDao.findByUserId(userId);

            EntityManagerHelper.commit();
            return notifications;

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    public void notifyUsers(
            List<? extends Utilisateur> utilisateurs,
            String titre,
            String message,
            TypeNotificationEnum type
    ) {
        if (utilisateurs == null || utilisateurs.isEmpty()) {
            return;
        }

        Notification notification = new Notification();
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setType(type);
        notification.setDateEnvoi(LocalDateTime.now());

        notificationDao.save(notification);

        for (Utilisateur utilisateur : utilisateurs) {
            utilisateur.addNotification(notification);
        }
    }

    public void notifyUser(
            Utilisateur utilisateur,
            String titre,
            String message,
            TypeNotificationEnum type
    ) {
        if (utilisateur == null) {
            return;
        }

        notifyUsers(List.of(utilisateur), titre, message, type);
    }

    public void notifyAdminsNewDraftConcert(
            List<? extends Utilisateur> admins,
            Concert concert
    ) {
        notifyUsers(
                admins,
                "New draft concert",
                "A new concert is waiting for validation: " + concert.getTitre(),
                TypeNotificationEnum.NEW_DRAFT_CONCERT
        );
    }

    public void notifyOrganisateurConcertConfirmed(
            Utilisateur organisateur,
            Concert concert
    ) {
        notifyUser(
                organisateur,
                "Concert confirmed",
                "Your concert has been published: " + concert.getTitre(),
                TypeNotificationEnum.CONFIRMATION
        );
    }

    public void notifyOrganisateurConcertCancelled(
            Utilisateur organisateur,
            Concert concert
    ) {
        notifyUser(
                organisateur,
                "Concert cancelled",
                "Your concert has been cancelled: " + concert.getTitre(),
                TypeNotificationEnum.ANNULATION
        );
    }

    public void notifyClientsNewPublishedConcert(
            List<? extends Utilisateur> clients,
            Concert concert
    ) {
        notifyUsers(
                clients,
                "New published concert",
                "A new concert is available: " + concert.getTitre(),
                TypeNotificationEnum.NEW_PUBLISHED_CONCERT
        );
    }

    public void notifyClientsConcertCancelled(
            List<Client> clients,
            Concert concert
    ) {
        notifyUsers(
                clients,
                "Ticket annulé",
                "Votre ticket pour le concert \"" + concert.getTitre() + "\" a été annulé car le concert a été annulé.",
                TypeNotificationEnum.ANNULATION
        );
    }
}