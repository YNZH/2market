package com.gjf.utils;

import org.junit.Test;

import static com.gjf.utils.PasswordHash.createHash;
import static com.gjf.utils.PasswordHash.validatePassword;

/**
 * @Author: GJF
 * @Date : 2018/04/30
 * Time   : 23:38
 */
public class PasswordHashTest {

    /**
     * Tests the basic functionality of the PasswordHash class
     */
    @Test
    public void test(){
        try {
            // Print out 10 hashes
            for (int i = 0; i < 10; i++) {
                System.out.println(createHash("p\r\nassw0Rd!"));
            }

            // Test password validation
            boolean failure = false;
            System.out.println("Running tests...");
            for (int i = 0; i < 100; i++) {
                String password = "" + i;
                String hash = createHash(password);
                String secondHash = createHash(password);
                if (hash.equals(secondHash)) {
                    System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
                    failure = true;
                }
                String wrongPassword = "" + (i + 1);
                if (validatePassword(wrongPassword, hash)) {
                    System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
                    failure = true;
                }
                if (!validatePassword(password, hash)) {
                    System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
                    failure = true;
                }
            }
            if (failure) {
                System.out.println("TESTS FAILED!");
            } else {
                System.out.println("TESTS PASSED!");
            }
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
    }
}
