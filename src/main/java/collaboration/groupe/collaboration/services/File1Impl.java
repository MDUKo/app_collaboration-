package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.services.collabServices.StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class File1Impl implements StorageService {

    @Value("${storage.path}")
    private  String storagepath;

// j'ai essayé deux approche mais pour cette classe ca ne marche pas
    @Override
    public String storeFile(MultipartFile file, Long userId) {
        try{
            String filename= UUID.randomUUID().toString()+ "_" + file.getOriginalFilename();
            File fileToSave=new File(storagepath + "user.home" +userId + "fichiersPhoto" +filename);
            fileToSave.mkdirs();
            file.transferTo(fileToSave);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.",e);
        }
    }

    @Override
    public byte[] retrieveFile(String filename, Long userId) {
        try {
            Path filePath= Paths.get(storagepath,userId.toString());
            if (Files.exists(filePath)){
                return Files.readAllBytes(filePath);
            }else {
                throw new FileNotFoundException("File not found:"+filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve file.",e);
        }
    }

    @Override
    public void deleteFile(String filename, Long userId) {
        try{
            Path filePath= Paths.get(storagepath, filename.toString());
            if (Files.exists(filePath)){
                Files.delete(filePath);
            }else {
                throw new FileNotFoundException("File not found:"+filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to delete file",e);
        }

    }
}
