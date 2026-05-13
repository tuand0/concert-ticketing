package fr.istic.taa.jaxrs.mappers;

import fr.istic.taa.jaxrs.domain.Notification;
import fr.istic.taa.jaxrs.dto.NotificationDTO;

import java.util.List;

public class NotificationMapper {

    public NotificationDTO toDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();

        dto.setId(notification.getId());
        dto.setTitre(notification.getTitre());
        dto.setMessage(notification.getMessage());
        dto.setDateEnvoi(notification.getDateEnvoi());

        if (notification.getType() != null) {
            dto.setType(notification.getType().name());
        }

        return dto;
    }

    public List<NotificationDTO> toDTOList(List<Notification> notifications) {
        return notifications.stream()
                .map(this::toDTO)
                .toList();
    }
}