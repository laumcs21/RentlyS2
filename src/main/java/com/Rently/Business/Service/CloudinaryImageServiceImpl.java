package com.Rently.Business.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Implementación del ImageService que utiliza Cloudinary como proveedor.
 */
@Service
public class CloudinaryImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    public CloudinaryImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public Map upload(MultipartFile multipartFile) throws IOException {
        // Convierte el MultipartFile a un archivo temporal
        File file = convert(multipartFile);
        // Sube el archivo a Cloudinary y obtiene el resultado
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        // Elimina el archivo temporal después de la subida
        file.delete();
        return result;
    }

    @Override
    public Map delete(String publicId) throws IOException {
        // Elimina una imagen de Cloudinary usando su public_id
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }

    /**
     * Convierte un MultipartFile a un objeto File para que la API de Cloudinary pueda procesarlo.
     * @param multipartFile El archivo recibido en la petición.
     * @return Un objeto File.
     * @throws IOException Si ocurre un error en la conversión.
     */
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
