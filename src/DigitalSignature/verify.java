package DigitalSignature;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Siddharth on 29-06-2015.
 */
public class verify {
    public static String filePath = "";

    public boolean verifyit(String filePath) {
        boolean verifies = false;
        try {
            //get public key
            File t = new File(filePath);
            FileInputStream f;
            try {
                f = new FileInputStream(t.getParent() + t.getName() + ".pKey");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Couldn't locate .pKey file ", "ALERT", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            byte[] encKey = new byte[f.available()];
            f.read(encKey);
            f.close();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(encKey);
            KeyFactory kf = KeyFactory.getInstance("DSA", "SUN");
            PublicKey pk = kf.generatePublic(spec);

            //get signature bits
            try {
                f = new FileInputStream(t.getParent() + t.getName() + ".sig");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Couldn't locate .sig file ", "ALERT", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            byte[] signature = new byte[f.available()];
            f.read(signature);
            f.close();

            //verify
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pk);
            byte[] checksum = createSha1(new File(filePath));
            sig.update(checksum);
            verifies = sig.verify(signature);

            System.out.println("signature verifies: " + verifies);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifies;
    }

    public static byte[] createSha1(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        InputStream f = new FileInputStream(file);
        int n = 0;
        byte[] buffer = new byte[8192];
        while (n != -1) {
            n = f.read(buffer);
            if (n > 0) {
                digest.update(buffer, 0, n);
            }
        }
        return digest.digest();
    }
}
