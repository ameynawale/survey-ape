package surveyape.services;

import com.google.zxing.WriterException;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
@Service
public interface QRService {

    public byte[] generateQRCode(String text, int width, int height) throws WriterException,IOException;
    public ListenableFuture<byte[]> generateQRCodeAsync(String text, int width, int height) throws Exception;
    public void purgeCache();

}
