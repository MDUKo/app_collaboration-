package collaboration.groupe.collaboration.services;

import collaboration.groupe.collaboration.entities.File;
import collaboration.groupe.collaboration.entities.Utilisateur;
import collaboration.groupe.collaboration.repository.FileRepository;
import collaboration.groupe.collaboration.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Arrays;
import java.util.Optional;

@Service
public class FileImpl  {

    private final FileRepository fileRepository;
    private final UtilisateurRepository utilisateurRepository;

//    @Value("${storage.path}")
    private  StorageImpl storage;

    public FileImpl(FileRepository fileRepository ,UtilisateurRepository utilisateurRepository,StorageImpl storage ) {
        this.fileRepository = fileRepository;
        this.utilisateurRepository=utilisateurRepository;
        this.storage=storage;


    }

//c'est la methode la plus recente
//    public File uploadFile(MultipartFile file, Long userId) throws IOException {
//        Optional<Utilisateur> utilisateur= utilisateurRepository.findById(userId);
//        File fileMedata=new File();
//        if(utilisateur.isEmpty()) {
//            throw new UserPrincipalNotFoundException("L'utilisateur avec l'ID " + userId + " n'existe pas dans la base de données.");
//        }
//        Utilisateur user = utilisateur.get();
//            String fileName = storage.storeFile(file, user.getIdUtilisateur());
//            //celle-ci enregistre dans la bd
//        //String filePath = String.valueOf(Paths.get(System.getProperty("user.home") + "fichiersPhoto" + fileName));
//        String filePath = String.valueOf(Paths.get(System.getProperty("user.home") + "fichiersPhoto" + fileName, fileMedata + Arrays.toString(file.getBytes())));
////        String filePath = String.valueOf(Files.write(Paths.get(System.getProperty("user.home") + "/fichiersPhoto" + fileMedata), file.getBytes()));
//            fileMedata.setContentType(file.getContentType());
//        fileMedata.setName(file.getOriginalFilename());
//            fileMedata.setSize(file.getSize());
//            fileMedata.setName(filePath);
//            fileMedata.setUserId(user.getIdUtilisateur());
//            fileMedata = fileRepository.save(fileMedata);
//        return fileMedata;
//    }

    //Upload un fichier
    public File uploadFile(MultipartFile file, Long userId) throws IOException {
        Optional<Utilisateur> utilisateur= utilisateurRepository.findById(userId);
        if(utilisateur.isEmpty()) {
            throw new UserPrincipalNotFoundException("L'utilisateur avec l'ID " + userId + " n'existe pas dans la base de données.");
        }
        Utilisateur user = utilisateur.get();
        String fileName = storage.storeFile(file,  user.getIdUtilisateur());
        File fileMedata = new File();
        fileMedata.setName(file.getOriginalFilename());

        // Créer un répertoire temporaire personnalisé
        Path tempDir = Files.createTempDirectory("uploaded-files-");

        // Écrire le fichier dans le répertoire temporaire personnalisé
        Files.write(tempDir.resolve(file.getOriginalFilename()), file.getBytes());

        // Créer le répertoire "/fichiersPhoto/" s'il n'existe pas
        Path photoDir = Paths.get(System.getProperty("user.home"), "fichiersPhoto");
        if (!Files.exists(photoDir)) {
            Files.createDirectory(photoDir);
        }

        // Déplacer le fichier du répertoire temporaire vers le répertoire final
        Files.move(tempDir.resolve(file.getOriginalFilename()), photoDir.resolve(file.getOriginalFilename()));

        fileMedata.setContentType(file.getContentType());
        fileMedata.setSize(file.getSize());
        fileMedata.setUserId( user.getIdUtilisateur());
        fileMedata = fileRepository.save(fileMedata);
        return fileMedata;
    }



//    Elle marche pas tres bien
//    public File uploadFile(MultipartFile file, Long userId) throws IOException {
//        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(userId);
//        if (utilisateur.isEmpty()) {
//            throw new UserPrincipalNotFoundException("L'utilisateur avec l'ID " + userId + " n'existe pas dans la base de données.");
//        }
//        Utilisateur user = utilisateur.get();
//
//        String fileName = storage.storeFile(file, user.getIdUtilisateur());
//        String filePath = Paths.get(System.getProperty("user.home"), "fichiersPhoto", fileName).toString();
//
//        File fileMedata = new File();
//        // Limiter la longueur du nom de fichier à 255 caractères (par exemple)
//        fileMedata.setName(file.getOriginalFilename().substring(0, Math.min(file.getOriginalFilename().length(), 255)));
//        fileMedata.setContentType(file.getContentType());
//        fileMedata.setSize(file.getSize());
//        fileMedata.setUserId(user.getIdUtilisateur());
//        fileMedata.setName(filePath);
//        return fileRepository.save(fileMedata);
//    }
//    private final FileRepository fileRepository;
//
//
//    private final StorageImpl storage;
//
//    public FileImpl(FileRepository fileRepository, StorageImpl storage) {
//        this.fileRepository = fileRepository;
//        this.storage = storage;
//    }

//    public File uploadFile(MultipartFile file, Long userId) throws IOException {
//        String fileName= storage.storeFile(file, userId);
//        File fileMedata=new File();
//        fileMedata.setName(file.getOriginalFilename());
//        Files.write(Paths.get(System.getProperty("user.home")+"/fichiersPhoto/"+fileMedata),file.getBytes());
//        fileMedata.setContentType(file.getContentType());
//        fileMedata.setSize(file.getSize());
//        fileMedata.setUserId(userId);
//        fileMedata=fileRepository.save(fileMedata);
//        return fileMedata;
//    }
//    public File uploadFile(MultipartFile file, Long userId) throws IOException {
//        String fileName = storage.storeFile(file, userId);
//        File fileMedata = new File();
//        fileMedata.setName(file.getOriginalFilename());
//
//        // Créer le répertoire "/fichiersPhoto/" s'il n'existe pas
//        Path photoDir = Paths.get(System.getProperty("user.home"), "fichiersPhoto");
//        if (!Files.exists(photoDir)) {
//            Files.createDirectory(photoDir);
//        }

//
//        // Écrire le fichier dans le répertoire "/fichiersPhoto/"
//        Files.write(photoDir.resolve(fileMedata.getName()), file.getBytes());
//
//        fileMedata.setContentType(file.getContentType());
//        fileMedata.setSize(file.getSize());
//        fileMedata.setUserId(userId);
//        fileMedata = fileRepository.save(fileMedata);
//        return fileMedata;
//    }

    // Telecharger une fichier
    public ResponseEntity <byte[]> downloadFile(Long fileId, Long userId) throws AccessDeniedException {
        File fileMedata =fileRepository.findById(fileId).orElseThrow(null);
        if(!fileMedata.getUserId().equals(userId)){
            throw new AccessDeniedException("You do not have have permission to access this file");
        }
        byte[] fileData = storage.retrieveFile(fileMedata.getName(), (userId));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileMedata.getContentType()))
                .contentLength(fileData.length)
                .body(fileData);
    }

    //Upprimer un fichier
    public void deleteFile(Long fileId, Long userId){
        File fileMedata =fileRepository.findById(fileId).orElseThrow();
            if(
                    !fileMedata.getUserId().equals(userId)
            ){
                throw  new RuntimeException("You do not have permission to delete this file");
            }
            storage.deleteFile(fileMedata.getName(), (userId));
            fileRepository.delete(fileMedata);
    }


}
