package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.services.collabServices.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import static java.io.FileOutputStream.*;


@Service
public class StorageImpl implements StorageService {

    @Value("${storage.path}")
    private  String storagepath;

    
    //sauvergarder le fichier
    @Override
    public String storeFile(MultipartFile file, Long userId) {

        try{
            String filename= UUID.randomUUID().toString()+ "_"+ file.getOriginalFilename();

            java.io.File fileToSave= new java.io.File(storagepath + "user.home"+ userId + "fichiersPhoto"+ filename);
//            java.io.File fileToSave= Files.write(Paths.get(System.getProperty("user.home")+"/fichiersPhoto/"+filename),file.getBytes()).toFile();
            fileToSave.mkdirs();
            file.transferTo(fileToSave);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file ",e);
        }
    }

    //Recuperation du fichier
    @Override
    public byte[] retrieveFile(String filename, Long userId) {
        try{
            Path filePath = Paths.get(System.getProperty("user.home")+"/fichiersPhoto/"+filename);
            if(Files.exists(filePath) && Files.isRegularFile(filePath)){
                return Files.readAllBytes(filePath);
            }else{
                throw new FileNotFoundException("File not found:" + filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to retrieve file."+e.getMessage());
        }
    }
    
    public void WriteToFile(String filename, String contentType) throws IOException {
        Path path=Paths.get(filename);
            try (OutputStream  writer =  Files.newOutputStream(path, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
        BufferedWriter writers=new BufferedWriter(new OutputStreamWriter(writer))){
            writers.write(contentType);

            } catch (IOException e) {
                System.out.println("Erreur lors de l'ecriture sur le fichier " + filename + ":" + e.getLocalizedMessage());

            }
        }
    public void createFile(String filePath, String content){
        try{
            File file=new File(filePath);
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.close();
            System.out.println("Le fichier"+filePath+ "crée avec succès");
        }catch (IOException e){
            System.out.println("Erreur lors de la creation du fichier" +filePath+":"+e.getMessage());
        }
    }
    //methode de Gemeni
    public void writeToFile(String filename, String content ) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home") + "/fichiersPhoto/" + filename);
            if (Files.exists(filePath)) {
                Files.write(filePath, content.getBytes());
            } else {
                throw new FileNotFoundException("File not found:" + filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write on the  file.", e);
        }
    }
   
    //methode geminire
    public void closeFile(String filename){
            try {
                Path filePath = Paths.get(System.getProperty("user.home") + "/fichiersPhoto/" + filename);
                if (Files.exists(filePath)) {
                    Files.isReadable((Path) new FileInputStream(filePath.toFile()));
                } else {
                    throw new FileNotFoundException("File not found:" + filename);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to write on the  file.", e);
            }
        }

    //methode poe
        public void closefile(InputStream fileinputStream){
        try{
            fileinputStream.close();
            System.out.println("Fichier femé avec succès.");
        }catch (IOException e){
            System.out.println(" Erreur lors de la fermeture du Fichier:"+e.getMessage());

        }
        }



    @Override
    public void deleteFile(String filename, Long userId) {
        try{
            Path filePath=Paths.get(System.getProperty("user.home")+"/fichiersPhoto/"+filename);
            if(Files.exists(filePath)){
                Files.delete(filePath);
            }else{
                throw new FileNotFoundException("File not found:" + filename);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file.",e);
        }
        }

}
