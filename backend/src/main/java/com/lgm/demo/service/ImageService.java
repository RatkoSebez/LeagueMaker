package com.lgm.demo.service;

import com.lgm.demo.model.User;
import com.lgm.demo.model.enumeration.ESex;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public interface ImageService{
    static String getRandomProfileImagePath(User user){
        String path = "src/main/resources/images/";
        Random random = new Random();
        if(user.getSex() == ESex.MALE)
            path += "man/" + (random.nextInt(4) + 1) + ".png";
        else
            path += "woman/" + (random.nextInt(4) + 1) + ".png";
        return path;
    }

    static String getBase64Image(String path) throws IOException{
        if(path == null)
            return null;
        File image = new File(path);
        byte[] imageContent = Files.readAllBytes(image.toPath());
        return Base64.getEncoder().encodeToString(imageContent);
    }
}
