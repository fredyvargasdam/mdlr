
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <b>Función de una sola vía (o función hash)</b> <br/>
 * <br/>
 * 
 * Dado que es <u>imposible</u> obtener el texto original a partir de un hash,
 * SHA es útil para guardar passwords en BBDD. Guardamos el hash, no el
 * password, de forma que cada vez que tengamos que autenticar a un usuario
 * primero calculamos el hash del password que nos llega con el hash de BBDD.
 * Si coinciden, genial. Si no, es que la clave es incorrecta.
 *
 */
public class EjemploSHA {

    /**
     * Aplica SHA al texto pasado por parámetro
     * 
     * @param texto
     */
    public void cifrarTexto(String texto) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            byte dataBytes[] = texto.getBytes(); // Texto a bytes
            messageDigest.update(dataBytes);
            byte resumen[] = messageDigest.digest(); // Se calcula el resumen

            System.out.println("Mensaje original: " + texto);
            System.out.println("Número de Bytes: " + messageDigest.getDigestLength());
            System.out.println("Algoritmo usado: " + messageDigest.getAlgorithm());
            System.out.println("Resumen del Mensaje: " + new String(resumen));
            System.out.println("Mensaje en Hexadecimal: " + Hexadecimal(resumen));
            System.out.println("Proveedor: " + messageDigest.getProvider().toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // Convierte Array de Bytes en hexadecimal
    static String Hexadecimal(byte[] resumen) {
        String HEX = "";
        for (int i = 0; i < resumen.length; i++) {
            String h = Integer.toHexString(resumen[i] & 0xFF);
            if (h.length() == 1)
                    HEX += "0";
            HEX += h;
        }
        return HEX.toUpperCase();
    }

    public static void main(String[] args) {
        EjemploSHA ejemploSHA = new EjemploSHA();
        ejemploSHA.cifrarTexto("Mensaje super secreto");
    }
}