package com.alex.tagservice.controller;

import com.alex.tagservice.services.impl.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rfid")
public class TagController {
    private final TagService iTagService;
    @PostMapping("/tags")
    public void getTagsInformation(@RequestBody String tags) {
        log.warn("tag_was_read");
        iTagService.addIdHexToList(tags);
    }
    @PostMapping("/management")
    public void getManagementInformation(@RequestBody String json) {
//        log.warn("management");
    }

    @GetMapping("/all")
    public List<String> getAllTags(){
        return iTagService.getAllTags();
    }

    @GetMapping("/delete/{idHex}")
    public ResponseEntity<?> clearAllTags(@PathVariable("idHex") String idHex){
        if (iTagService.deleteIdHexFromList(idHex)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/clear")
    public ResponseEntity<?> clearAllTags(){
        iTagService.clearAllTags();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
