package collaboration.groupe.collaboration.services.collabServices;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    public String storeFile(MultipartFile file, Long userId);

    public byte[] retrieveFile(String filename, Long userId);
    public void deleteFile(String filename, Long userId);
}
