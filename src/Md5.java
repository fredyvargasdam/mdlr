
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class Md5 {

    /**
     * Devuelve encriptado el mensaje recibido
     *
     * @param mensaje a encriptar
     * @param tipo de encriptacion (Md5/SHA1)
     * @return
     */
    public static String getEncriptacion(String mensaje, String tipo) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance(tipo);
            md.update(mensaje.getBytes());
            byte[] db = md.digest();
            hash = DatatypeConverter.printHexBinary(db).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
        }
        return hash;
    }
    public static String getEncriptacionDesHash(String mensaje, String tipo) {
        String hash = "";
        try {
            MessageDigest md = MessageDigest.getInstance(tipo);
            md.update(mensaje.getBytes());
            byte[] db = md.digest();
            hash = DatatypeConverter.printHexBinary(db).toLowerCase();
        } catch (NoSuchAlgorithmException ex) {
        }
        return hash;
    }
    

    public static void main(String[] args) {
        // 

        String mensaje = "Esto es un texto plano";
        System.out.println(mensaje);
        System.out.println("SH1: " + Md5.getEncriptacion(mensaje, "SHA1"));
        System.out.println("SH1 deshasheada: " + Md5.getEncriptacionDesHash(mensaje, "SHA1"));
        System.out.println("MD5: " + Md5.getEncriptacion(mensaje, "MD5"));

    }
}
