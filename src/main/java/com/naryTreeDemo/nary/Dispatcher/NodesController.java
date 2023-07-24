package com.naryTreeDemo.nary.Dispatcher;

import com.naryTreeDemo.nary.Entity.Nodes;
import com.naryTreeDemo.nary.Service.NodesService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NodesController {
    
    @Autowired
    private NodesService nodesService;
    
    //GET ENDPOINTS
    @GetMapping("/nodes")
    public ArrayList<Nodes> viewAll(){
        ArrayList<Nodes> auxList = new ArrayList<>(nodesService.viewAll());
        return auxList;      
    }
    
    @GetMapping("/nodes/{id}")
    public Nodes findById(@PathVariable("id") UUID id){
        return nodesService.findById(id);
    }
    
    //POST ENDPOINTS
    @PostMapping("/nodes")
    public ResponseEntity<?> insertNewNode(@RequestParam("value")String value, @RequestParam(required = false) UUID parentId){
        return nodesService.insert(value, parentId);
    }
}
