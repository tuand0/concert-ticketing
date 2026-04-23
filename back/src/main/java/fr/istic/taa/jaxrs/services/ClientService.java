package fr.istic.taa.jaxrs.services;

import fr.istic.taa.jaxrs.dao.ClientDao;
import fr.istic.taa.jaxrs.dao.generic.EntityManagerHelper;
import fr.istic.taa.jaxrs.domain.Client;
import fr.istic.taa.jaxrs.dto.ClientCreateDTO;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public class ClientService {

    private final ClientDao clientDao = new ClientDao();

    public Client findOne(Long id) {
        Client client = clientDao.findOne(id);
        if (client == null) {
            throw new NotFoundException("Client non trouvé");
        }
        return client;
    }

    public List<Client> findAll() {
        return clientDao.findAll();
    }

    public long create(ClientCreateDTO dto) {
        try {
            EntityManagerHelper.beginTransaction();

            validateCreateDTO(dto);

            Client client = new Client();
            client.setNom(dto.getNom().trim());
            client.setPrenom(dto.getPrenom().trim());
            client.setUserNom(dto.getUserNom().trim());
            client.setHashedPassword(dto.getHashedPassword().trim());
            client.setEmail(dto.getEmail().trim());
            client.setTelephone(dto.getTelephone() != null ? dto.getTelephone().trim() : null);

            clientDao.save(client);

            EntityManagerHelper.commit();
            return client.getId();

        } catch (RuntimeException e) {
            EntityManagerHelper.rollback();
            throw e;
        } finally {
            EntityManagerHelper.closeEntityManager();
        }
    }

    private void validateCreateDTO(ClientCreateDTO dto) {
        if (dto == null) {
            throw new BadRequestException("La requête est vide");
        }

        if (dto.getNom() == null || dto.getNom().isBlank()) {
            throw new BadRequestException("Le nom est obligatoire");
        }

        if (dto.getPrenom() == null || dto.getPrenom().isBlank()) {
            throw new BadRequestException("Le prénom est obligatoire");
        }

        if (dto.getUserNom() == null || dto.getUserNom().isBlank()) {
            throw new BadRequestException("Le userNom est obligatoire");
        }

        if (dto.getHashedPassword() == null || dto.getHashedPassword().isBlank()) {
            throw new BadRequestException("Le mot de passe hashé est obligatoire");
        }

        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new BadRequestException("L'email est obligatoire");
        }
    }
}