/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdlr.Client;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.DatatypeConverter;
import mdlr.modelo.Usuario;

/**
 * Jersey REST client generated for REST resource:UsuarioFacadeREST
 * [prueba.entity.usuario]<br>
 * USAGE:
 * <pre>
 *        UsuarioClient client = new UsuarioClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class UsuarioClient {

    private static WebTarget webTarget;
    private static PublicKey publicKey;
    private static Cipher rsa;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/PruebaClaves/webresources";

    public UsuarioClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("usuario");
    }

    public void edit(Object requestEntity, String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public static <T> T find(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

 
    public static <T> T usuarioByLogin(Class<T> responseType, String login, String pass) throws ClientErrorException, ContraseniaIncorrectaException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("usuarioByLogin/{0}/{1}", new Object[]{login, pass}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void create(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void remove(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    public void close() {
        client.close();
    }

    private static PublicKey loadPublicKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        return keyFromBytes;
    }

    public static void main(String[] args) throws Exception {
        String pass = "abcd*1234";

        rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        publicKey = loadPublicKey("publickey.dat");
        rsa.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encriptado = rsa.doFinal(pass.getBytes(StandardCharsets.UTF_8));
        String sn = DatatypeConverter.printBase64Binary(encriptado);
        Usuario uno = new Usuario();
        uno.setLogin("admin");
        uno.setPassword(sn);
        UsuarioClient u = new UsuarioClient();
        Usuario uR = new Usuario();
        if(!"hola".equals("hola")){
            System.out.println("NO");
        }
            
        //   uR.setLogin("Marta");
        // uR.setId_usuario(1);

        //      System.out.print(new String(encriptado));
        uR = UsuarioClient.usuarioByLogin(Usuario.class, "admin", sn);
        //  uR=u.find(Usuario.class, "1");
        System.out.println(uR.getLogin() + "  " + uR.getPassword());
        
        
    }

}
