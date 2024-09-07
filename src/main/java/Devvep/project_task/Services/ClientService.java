package Devvep.project_task.Services;


import Devvep.project_task.Entity.Client;
import Devvep.project_task.Repo.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client addClient(String clientNumber, String name, List<MultipartFile> images) throws Exception {
        if (clientRepository.existsByClientNumber(clientNumber)) {
            throw new Exception("Client number already exists!");
        }

        List<byte[]> imageData = uploadImages(images);
        Client client = new Client(clientNumber, name, imageData);

        return clientRepository.save(client);
    }

    public Optional<Client> getClientByNumber(String clientNumber) {
        return clientRepository.findByClientNumber(clientNumber);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public void deleteClientByNumber(String clientNumber) {
        clientRepository.deleteByClientNumber(clientNumber);
    }

    // Convert MultipartFile to byte array for BLOB storage
    private List<byte[]> uploadImages(List<MultipartFile> images) throws IOException {
        List<byte[]> imageDataList = new ArrayList<>();
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                byte[] imageData = image.getBytes();
                imageDataList.add(imageData);
            }
        }
        return imageDataList;
    }
}