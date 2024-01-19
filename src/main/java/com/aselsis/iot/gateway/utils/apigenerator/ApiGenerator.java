package com.aselsis.iot.gateway.utils.apigenerator;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class ApiGenerator {
   public static String generateApiKey(int lenghtParam){
        int lenght = lenghtParam;
        String apikey;
        char[] chars = {'a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U', 'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};

        Random random = new Random();
        StringBuilder apikeyBuilder = new StringBuilder();

        for (int i = 0; i < lenght; i++) {
            int randomi = random.nextInt(chars.length);
            int randomj = random.nextInt(10);
            int randomb = random.nextInt(100);

            if (randomb%3==0){
                char randomChar = chars[randomj];
                apikeyBuilder.append(randomChar);
            }
            else if(randomb%5==0){
                char randomChar = Character.forDigit(randomj, 10); ;
                apikeyBuilder.append(randomChar);
            }
            else{
                char randomChar = chars[randomi];
                apikeyBuilder.append(randomChar);
            }
        }
        apikey = apikeyBuilder.toString();
        return apikey;
    }
}
