package Devvep.project_task.Entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String clientNumber;

    private String name;

    // Store multiple images as BLOBs
    @ElementCollection
    @CollectionTable(name = "client_images", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private List<byte[]> images;  // Store images as a list of byte arrays

    public Client() {}

    public Client(String clientNumber, String name, List<byte[]> images) {
        this.clientNumber = clientNumber;
        this.name = name;
        this.images = images;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}