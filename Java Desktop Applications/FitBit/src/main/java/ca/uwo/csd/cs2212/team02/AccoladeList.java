package ca.uwo.csd.cs2212.team02;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * AccoladeList loads and stores the list of accolades
 */

public class AccoladeList implements Serializable {

    private LinkedList<Accolade> fullList = new LinkedList<Accolade>();
    private DataType dataObjects[] = new DataType[6];
    private HealthMetaScore hms;
    private MainWindow main;
    private LinkedList<Accolade> newlyUnlocked = new LinkedList<Accolade>();
    private static final long serialVersionUID = 2L;
    private static final byte[] key = "MyDifficultPassw".getBytes();
    private static final String transformation = "AES";

    /**
     * Constructor, grabs created data objects
     *
     * @param mainWindow
     */
    public AccoladeList(MainWindow mainWindow) {
        this.main = mainWindow;
        dataObjects[0] = mainWindow.getDashboard().getDataSteps();
        dataObjects[1] = mainWindow.getDashboard().getDataDistance();
        dataObjects[2] = mainWindow.getDashboard().getDataFloors();
        dataObjects[3] = mainWindow.getDashboard().getDataCalories();
        dataObjects[4] = mainWindow.getDashboard().getDataSedentaryMins();
        dataObjects[5] = mainWindow.getDashboard().getDataActiveMins();
        hms = mainWindow.getDashboard().getDataHealthMeta();


        if (new File("src/main/resources/accolades.dat").exists()) {
            try {
                fullList = loadAccoladeList();

            } catch (Exception e) {
                pullFromTextFile();

            }
        } else {
            pullFromTextFile();
        }
    }

    /**
     * Loads saved accolade list
     *
     * @return Saved accolade list
     * @throws Exception if file is corrupt
     */
    private LinkedList<Accolade> loadAccoladeList() throws Exception {

        AccoladeConfig accList = (AccoladeConfig) decrypt(new ObjectInputStream(new FileInputStream("src/main/resources/accolades.dat")));
        return accList.geFullList();
    }


    /**
     * Decrypts previously encrypted file
     *
     * @param istream Input stream
     * @return Decrypted string in form of a sealed object
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static Object decrypt(InputStream istream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKeySpec sks = new SecretKeySpec(key, transformation);
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, sks);

        CipherInputStream cipherInputStream = new CipherInputStream(istream, cipher);
        ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
        SealedObject sealedObject;
        try {
            sealedObject = (SealedObject) inputStream.readObject();
            return sealedObject.getObject(cipher);
        } catch (ClassNotFoundException ex1) {
            ex1.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException ex2) {
            ex2.printStackTrace();
            return null;
        } catch (BadPaddingException ex3) {
            ex3.printStackTrace();
            return null;
        }

    }


    /**
     * Create accolade list if never previously run
     */
    private void pullFromTextFile() {
        try {
            Scanner scan = new Scanner(new File("src/main/resources/Accolades.txt"));
            String line = "";

            int counter = 0;
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                String[] split = line.split("\t");
                Accolade temp = new Accolade(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), split[3], split[4]);
                fullList.add(temp);
                counter++;
            }
            saveAccolades();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
        }
    }

    /**
     * Save accolades and encrypt to protect user's data
     *
     * @throws Exception
     */
    private void saveAccolades() throws Exception {
        encrypt(new AccoladeConfig(fullList), new ObjectOutputStream(new FileOutputStream("src/main/resources/accolades.dat")));
    }

    /**
     * Encrypt user's accolades
     *
     * @param object  Object to be encryped
     * @param ostream File to store the encrypted data
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    private static void encrypt(AccoladeConfig object, ObjectOutputStream ostream) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        try {
            // Length is 16 byte
            SecretKeySpec sks = new SecretKeySpec(key, transformation);

            // Create cipher
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            SealedObject sealedObject = new SealedObject(object, cipher);

            // Wrap the output stream
            CipherOutputStream cos = new CipherOutputStream(ostream, cipher);
            ObjectOutputStream outputStream = new ObjectOutputStream(cos);
            outputStream.writeObject(sealedObject);
            outputStream.close();
        } catch (IllegalBlockSizeException e) {

            System.out.println("exception for encrypt ");
        }
    }

    /**
     * Get list of all accolades
     *
     * @return list containing all accolades
     */
    public LinkedList<Accolade> getFullList() {
        return fullList;
    }

    /**
     * @return list containing only newly unlocked accolades
     */
    public LinkedList<Accolade> getNewlyUnlocked() {
        return newlyUnlocked;
    }

    /**
     * Check which accolades have been unlocked since last login
     */
    public void checkAll() {
        try {
            Iterator<Accolade> it = fullList.iterator();
            while (it.hasNext()) {
                Accolade temp = it.next();
                if (temp.isLocked()) {
                    switch (temp.getDailyorLifeT()) {
                        case 0: //daily

                            if (temp.getID() < 6) {

                                if (dataObjects[temp.getID()].getValue() >= temp.getThreshold()) {
                                    temp.setLocked(false);
                                    temp.setDateUnlocked(this.main.getDataDate());
                                    newlyUnlocked.add(temp);
                                }

                            } else {
                                if (hms.getScore() >= temp.getThreshold()) {
                                    temp.setLocked(false);
                                    temp.setDateUnlocked(this.main.getDataDate());
                                    newlyUnlocked.add(temp);
                                }
                            }

                            break;
                        case 1:
                            if (temp.getID() < 6) {
                                if (dataObjects[temp.getID()].getLifeTime() >= temp.getThreshold()) {
                                    temp.setLocked(false);
                                    temp.setDateUnlocked(this.main.getDataDate());
                                    newlyUnlocked.add(temp);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            saveAccolades();
        } catch (FileNotFoundException e) {

        } catch (Exception e) {

        }


    }
}
