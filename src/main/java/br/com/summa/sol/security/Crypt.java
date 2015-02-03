/*
 *  Copyright 2012 by Summa Technologies do Brasil.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package br.com.summa.sol.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

/**
 * Convenience class for encrypting passwords using SHA-256, salted with username.
 *
 * @author Einar Saukas
 */
public final class Crypt {

    /**
     * Prevents instantiation
     */
    private Crypt() {
    }

    /**
     * Encrypt password using SHA-256, salted with username
     */
    public static String encrypt(String username, String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();

        digest.update(username.getBytes("UTF-8"));

        byte[] result = digest.digest(password.getBytes("UTF-8"));

        for (int i = 0; i < 5; i++) {
            digest.reset();
            result = digest.digest(result);
        }

        return DatatypeConverter.printBase64Binary(result);
    }
}
