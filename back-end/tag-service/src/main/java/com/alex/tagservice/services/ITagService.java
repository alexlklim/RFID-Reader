package com.alex.tagservice.services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITagService {


    List<String> getAllTags();
    void addIdHexToList(String json);

    void clearAllTags();
    Boolean deleteIdHexFromList(String idHex);
    void sendNewTagToProductService(String idHex);
    List<String> getIdHexOfTags(String jsonString);
}
