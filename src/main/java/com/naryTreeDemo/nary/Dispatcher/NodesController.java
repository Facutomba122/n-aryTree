package com.naryTreeDemo.nary.Dispatcher;

import com.naryTreeDemo.nary.Entity.Nodes;
import com.naryTreeDemo.nary.Service.NodesService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodes")
public class NodesController {
    
    @Autowired
    private NodesService nodesService;
    
    //GET ENDPOINTS
    @GetMapping("")
    public ArrayList<Nodes> viewAll(){
        ArrayList<Nodes> auxList = new ArrayList<>(nodesService.viewAll());
        return auxList;      
    }
    
    @GetMapping("/{id}")
    public Nodes findById(@PathVariable("id") UUID id){
        return nodesService.findById(id);
    }
    
    //POST ENDPOINTS
    @PostMapping("")
    public ResponseEntity<?> insertNewNode(@RequestParam("value")String value, @RequestParam(required = false) UUID parentId){
        return nodesService.insert(value, parentId);
    }
    
    //UPDATE ENDPOINTS
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNode(@RequestBody Nodes updatedNode, @PathVariable("id") UUID id){
        try {
            return nodesService.update(updatedNode, id);
            
        } catch (Exception e){
            throw e;
        }
    }
    
    //DELETE ENDPOINTS  
    @DeleteMapping("/fullDelete/{id}")
    public ResponseEntity<?> deleteNode(@PathVariable("id") UUID id){
        try {
            return nodesService.deleteNodes(id);
        } catch (Exception e){
            throw e;
        }
    }
    
    //Deletes a new one that is replaced by its first child
    @DeleteMapping("/partialDelete/{id}")
    public ResponseEntity<?> partialDeleteNodes(@PathVariable("id") UUID id){
        try {
            return nodesService.deletePartialNodes(id);
        } catch (Exception e){
            throw e;
        }
    }
}
