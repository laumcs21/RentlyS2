package com.Rently.Business.Service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

/**
 * Interfaz para el servicio de gestión de imágenes.
 * Abstrae la implementación del proveedor de almacenamiento de imágenes (Cloudinary).
 */
public interface ImageService {

    /**
     * Sube una imagen al servicio externo.
     *
     * @param file El archivo de imagen a subir, recibido en una petición HTTP.
     * @return Un mapa con los detalles de la imagen subida (ej. URL, public_id).
     * @throws IOException Si ocurre un error durante la subida.
     */
    Map upload(MultipartFile file) throws IOException;

    /**
     * Elimina una imagen del servicio externo usando su ID público.
     *
     * @param publicId El ID público de la imagen a eliminar.
     * @return Un mapa con el resultado de la operación de borrado.
     * @throws IOException Si ocurre un error durante el borrado.
     */
    Map delete(String publicId) throws IOException;
}
