package br.com.globalcode.util;

import java.io.*;

public class Teclado {

    public static String le() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String texto = reader.readLine();
        return texto;
    }
}
