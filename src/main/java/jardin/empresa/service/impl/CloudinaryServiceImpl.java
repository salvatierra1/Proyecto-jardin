package jardin.empresa.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jardin.empresa.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dpgoimsdy",
                "api_key", "271333998824868",
                "api_secret", "WaGAuKlskYWyx8nu_Pv6FXFNVHY"
        ));
    }

    @Override
    public Map<String, Object> upload(MultipartFile multipartFile) throws IOException {
        Map<String, Object> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());

        String secureUrl = (String) uploadResult.get("secure_url");
        System.out.println("Secure URL generated: " + secureUrl); // Verificación

        // Reemplaza http:// por https:// en la URL antes de guardarla
        String httpsUrl = secureUrl.replace("http://", "https://");

        // Ahora puedes usar "httpsUrl" para guardar en la base de datos

        return uploadResult;
    }

    @Override
    public Map<String, Object> delete(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }
}
