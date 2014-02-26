package com.siscacao.util;

import com.siscacao.bean.AppBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FileUtils;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploadController")
public class FileUploadController {

    private String destination_tmp = "C:\\tmp\\";
    // private String destinationApp; 
    private AppBean baseURL;

    public FileUploadController() {

        this.baseURL = new AppBean();
    }

    public void uploadToTmpUserFolder(FileUploadEvent event, String folderName) {
        try {
            copyFileTmpLocalFolder(event.getFile().getFileName(), event.getFile().getInputstream(), folderName);
            FacesMessage msg = new FacesMessage("Completado! ", event.getFile().getFileName() + " guardado.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void uploadToApplicationUserFolder(String folderName) {

        ExternalContext extContext = FacesContext.getCurrentInstance().getExternalContext();
        File folder = new File(extContext.getRealPath("resources/" + "/user/" + folderName));
        System.out.println("FOLDER PATH: "+folder.getAbsolutePath());
        folder.mkdir();
        try {
            //copia del archivo
            FileUtils.copyDirectory(new File(destination_tmp + "\\" + folderName), folder);
            //elimina carpeta tmp
            FileUtils.deleteDirectory(new File(destination_tmp + "\\" + folderName));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error copiando las imagenes en el servido", ""));
        }


    }

    public void copyFileTmpLocalFolder(String fileName, InputStream in, String userFolderName) {
        try {
            // write the inputStream to a FileOutputStream
            File folder = new File(destination_tmp + "\\" + userFolderName);
            folder.mkdirs();
            OutputStream out = new FileOutputStream(new File(destination_tmp + "\\" + userFolderName + "\\" + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ResourceBundle.getBundle("/resources/Bundle").getString("Generic_file_error"), ""));

        }
    }

    public void copyFile(String fileName, InputStream in) {
        try {
            // write the inputStream to a FileOutputStream
            File folder = new File(destination_tmp + "\\" + fileName);
            folder.mkdirs();
            OutputStream out = new FileOutputStream(new File(destination_tmp + "\\" + fileName + "\\" + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, ResourceBundle.getBundle("/resources/Bundle").getString("Generic_file_error"), ""));

        }
    }
}
