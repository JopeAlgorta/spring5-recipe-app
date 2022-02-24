package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author jpalg
 * Created at 2/23/2022
 */
public interface ImageService {
    void saveImage(Long recipeId, MultipartFile file);
}
