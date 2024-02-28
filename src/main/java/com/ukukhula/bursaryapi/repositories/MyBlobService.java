package com.ukukhula.bursaryapi.repositories;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.ukukhula.bursaryapi.AzureBlobProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class MyBlobService {
    private final AzureBlobProperties azureBlobProperties;

    private BlobContainerClient containerClient() {
        System.out.println(azureBlobProperties.getConnectionstring());
        BlobServiceClient serviceClient = new BlobServiceClientBuilder()
                .connectionString(azureBlobProperties.getConnectionstring()).buildClient();
        BlobContainerClient container = serviceClient.getBlobContainerClient(azureBlobProperties.getContainer());
        System.out.println(azureBlobProperties.getContainer());
        System.out.println("Does exists :" + container.exists());
        return container;
    }

    public String storeFile(String filename, InputStream content, long length) {
        BlobClient client = containerClient().getBlobClient(filename);
        if (client.exists()) {
            log.warn("The file was already located on azure");
        } else {
            client.upload(content, length);
        }

        System.out.println();
        return client.getBlobUrl();
    }

}
