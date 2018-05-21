package surveyape.controllers;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import surveyape.services.QRService;



    @EnableAsync
    @EnableCaching
    @EnableScheduling
    @RequestMapping("/QR")
    @CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = {"*"})
    @RestController
    public class QRController {

        public static final String QRCODE_ENDPOINT = "/qrcode";
        public static final long THIRTY_MINUTES = 1800000;

        @Autowired
        private QRService imageService;

        /*public static void main(String[] args) {
            SpringApplication.run(SpringExampleApp.class, args);
        }
*/

        @RequestMapping( value = "/qrcode", method= RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
        public ResponseEntity<byte[]> getQRCode(@RequestParam(value = "text", required = true) String text) {
            try {
                System.out.println("Get QRcode");
                return ResponseEntity.ok().cacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES))
                        .body(imageService.generateQRCodeAsync(text, 256, 256).get());
            } catch (Exception ex) {
                throw new InternalServerError("Error while generating QR code image.", ex);
            }
        }

        @Scheduled(fixedRate = THIRTY_MINUTES)
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping(value = QRCODE_ENDPOINT)
        public void deleteAllCachedImages() {
            imageService.purgeCache();
        }

        @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
        public class InternalServerError extends RuntimeException {

            private static final long serialVersionUID = 1L;

            public InternalServerError(final String message, final Throwable cause) {
                super(message);
            }

        }
    }


