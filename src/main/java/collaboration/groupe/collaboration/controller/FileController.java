package collaboration.groupe.collaboration.controller;

import collaboration.groupe.collaboration.entities.File;
import collaboration.groupe.collaboration.services.File1Impl;
import collaboration.groupe.collaboration.services.FileImpl;
import collaboration.groupe.collaboration.services.StorageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping(path = "/file")
public class FileController {

    private final FileImpl fileImpl;
    private StorageImpl storage;
//    private final

    public FileController(FileImpl fileImpl,StorageImpl storage) {
        this.fileImpl = fileImpl;
        this.storage=storage;

    }

    @RequestMapping("/file")
    public String fileString(){
        return"html/fileHtml";
    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("userId") Long userId) throws IOException {
        fileImpl.uploadFile(file, userId);
        return"html/fileHtml";
    }
//    @GetMapping(path = "/download/{fileId}")
//    public ResponseEntity<byte[]> downlaodFile(@PathVariable("fileId") Long fileId, @RequestParam("userId") Long userId) throws AccessDeniedException {
//        return fileImpl.downloadFile(fileId, userId);
//    }
    @GetMapping(path = "/download/{fileId}/{userId}")
    public ResponseEntity<byte[]> downlaodFile(@PathVariable("fileId") Long fileId, @PathVariable("userId") Long userId) throws AccessDeniedException {
        return fileImpl.downloadFile(fileId, userId);
    }

    @DeleteMapping(path = "/delete/{fieldId}/{userId}")
    public String deleteFile(@PathVariable("fieldId") Long fileId, @PathVariable("userId") Long userId){
        fileImpl.deleteFile(fileId, userId);
        return "html/fileHtml";
    }

    @PostMapping(path = "/write")
    public void writeToFile(@RequestBody File file){
        storage.writeToFile(file.getName(),file.getContentType());
    }


}
