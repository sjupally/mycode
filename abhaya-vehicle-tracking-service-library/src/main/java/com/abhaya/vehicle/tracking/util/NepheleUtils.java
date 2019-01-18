/**
 * 
 */
package com.abhaya.vehicle.tracking.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.postgresql.util.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class NepheleUtils {

  public static final Pattern DOMAIN_PATTERN = Pattern.compile("(?=^.{4,253}$)(^((?!-)[a-zA-Z0-9-]{1,63}(?<!-)\\.)+[a-zA-Z]{2,63}$)");
  private static final String SECRET_KEY_STR = "encryptor key";
  //configured currency converter values
  private static final Map<String, Double> currencyMapper = new HashMap<String, Double>();

  {
    currencyMapper.put("USD", 1.000);
    currencyMapper.put("INR", 0.015);
    currencyMapper.put("AUD", 0.720);
    currencyMapper.put("EURO", 1.093);
    currencyMapper.put("HKD", 0.129);
    currencyMapper.put("GBP", 1.507);
  }

  private static int getNextIndex(Random rnd, int len, char[] pswd) {
    int index = rnd.nextInt(len);
    while (pswd[index = rnd.nextInt(len)] != 0) ;
    return index;
  }

 

  public String encrypt(String strToEncrypt) {
    log.debug("strToEncrypt...: " + strToEncrypt);
    String returnVal = null;
    try {
      if (!StringUtils.isEmpty(strToEncrypt)) {
        SecretKey secretKey = setKey(SECRET_KEY_STR);
        log.debug("in encrypt if...: " + secretKey);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        returnVal = Base64.encodeBytes(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      }

    } catch (Exception e) {
      log.error("Error while encrypting: " + e.toString());
    }
    return returnVal;
  }

  public String decrypt(String strToDecrypt) {
    String returnVal = null;
    try {
      if (null != strToDecrypt) {
        SecretKey secretKey = setKey(SECRET_KEY_STR);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        returnVal = new String(cipher.doFinal(Base64.decode(strToDecrypt)));
      }
    } catch (Exception e) {
      log.error("Error while decrypting: " + e.toString());
    }
    return returnVal;
  }

  private SecretKey setKey(String secretKeyStr) {
    SecretKey secretKey = null;
    byte[] key = null;
    MessageDigest sha = null;
    try {
      key = secretKeyStr.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16); // use only first 128 bit
      secretKey = new SecretKeySpec(key, "AES");

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return secretKey;
  }
  

  public String generateRandomPassword(int length) {
    String alphabet =
        new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    int n = alphabet.length();

    String result = new String();
    Random r = new Random();

    for (int i = 0; i < length; i++)
      result = result + alphabet.charAt(r.nextInt(n));

    return result;
  }

  public String generatePswd(int minLen) {

    String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    String NUM = "0123456789";
    String SPL_CHARS = "!@#$%&*_=-";

    int maxLen = 15;
    int noOfCAPSAlpha = 3;
    int noOfDigits = 3;
    int noOfSplChars = 2;

    if (minLen > maxLen)
      throw new IllegalArgumentException("Min. Length > Max. Length!");
    if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > minLen)
      throw new IllegalArgumentException
          ("Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
    Random rnd = new Random();
    int len = rnd.nextInt(maxLen - minLen + 1) + minLen;
    char[] pswd = new char[len];
    int index = 0;
    for (int i = 0; i < noOfCAPSAlpha; i++) {
      index = getNextIndex(rnd, len, pswd);
      pswd[index] = ALPHA_CAPS.charAt(rnd.nextInt(ALPHA_CAPS.length()));
    }
    for (int i = 0; i < noOfDigits; i++) {
      index = getNextIndex(rnd, len, pswd);
      pswd[index] = NUM.charAt(rnd.nextInt(NUM.length()));
    }
    for (int i = 0; i < noOfSplChars; i++) {
      index = getNextIndex(rnd, len, pswd);
      pswd[index] = SPL_CHARS.charAt(rnd.nextInt(SPL_CHARS.length()));
    }
    for (int i = 0; i < len; i++) {
      if (pswd[i] == 0) {
        pswd[i] = ALPHA.charAt(rnd.nextInt(ALPHA.length()));
      }
    }
    return new String(pswd);
  }

  //return conversion rate if from,to must not null and contains in map.
  public Double findExchangeRate(String from, String to) {

    if (from != null && !from.equals(" ") && to != null && !to.equals(" ")) {
      if (currencyMapper.containsKey(from) && currencyMapper.containsKey(to)) {
        return (Double) (currencyMapper.get(from) / currencyMapper.get(to));
      }
    }
    return null;
  }


}


