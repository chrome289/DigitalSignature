package DigitalSignature;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.*;

/**
 * Created by Siddharth on 29-06-2015.
 */
public class sign {
    public static String filePath;

    public String signit(String temp) {

        filePath = temp;
        //generate checksum using SHA1
        byte[] checksum = new byte[20];
        try {
            checksum = createSha1(new File(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }


        temp = Base64.encode(checksum);
        System.out.println("Checksum base64 encoded " + temp);
        for (byte aChecksum : checksum)
            System.out.print(aChecksum);

        //encrypt using DSA
        byte[] signature;
        try {
            signature= encrypt(checksum);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Unable to write to file \nCheck write permissions", "ALERT", JOptionPane.WARNING_MESSAGE);
            return " File Signing failed";
        }
        if(signature==null)
            return " File Signing failed";

        temp = Base64.encode(signature);
        System.out.println();
        System.out.println("Signature base64 encoded " + temp);
        for (byte aSignature : signature)
            System.out.print(aSignature);
        return temp;
    }

    private static byte[] encrypt(byte[] checksum) {

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);
            KeyPair kp = keyGen.generateKeyPair();
            PrivateKey prKey = kp.getPrivate();
            PublicKey puKey = kp.getPublic();
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initSign(prKey);
            sig.update(checksum);
            byte[] signature = sig.sign();

            File t = new File(filePath);
            FileOutputStream f = new FileOutputStream(new File(t.getParent() + t.getName() + ".sig"));
            f.write(signature);
            f.close();
            f = new FileOutputStream(new File(t.getParent() + t.getName() + ".pKey"));
            byte[] temp = puKey.getEncoded();
            f.write(temp);
            f.close();
            return signature;


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to read/write file \nCheck read/write permissions", "ALERT", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    public static byte[] createSha1(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        InputStream fis = new FileInputStream(file);
        int n = 0;
        byte[] buffer = new byte[8192];
        while (n != -1) {
            n = fis.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }
        return digest.digest();
    }
}