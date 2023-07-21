package com.naryTreeDemo.nary.Service;

import com.naryTreeDemo.nary.Entity.Nodes;
import com.naryTreeDemo.nary.Persistence.NodesPersistence;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NodesService {
    
    @Autowired
    private NodesPersistence nodesPersistence;
    
    private final Nodes parentNode = new Nodes();
    
    
    //Insert new Node nary tree
    public ResponseEntity<?> insert(String value, UUID parentId){
        if (value == null){
            return new ResponseEntity("The value hasn't to be null", HttpStatus.BAD_REQUEST);
        }
        if (parentNode.getId() == null){
            nodesPersistence.save(parentNode);
        }
        Nodes newNode = new Nodes();
        newNode.setValue(value);


        //Without parent
        if (parentId == null){
            newNode.setFatherNodeId(parentNode.getId());
            nodesPersistence.save(newNode);
            parentNode.addChildrenNode(newNode.getId());
            nodesPersistence.save(parentNode);
            return new ResponseEntity("Node create succesfully", HttpStatus.ACCEPTED);
        }


        //With parent
        Optional<Nodes> responseParentNode = nodesPersistence.findById(parentId);
        if (responseParentNode.isPresent()){
            newNode.setFatherNodeId(parentId);
            nodesPersistence.save(newNode);
            responseParentNode.get().addChildrenNode(newNode.getId());
            return new ResponseEntity("Node create succesfully", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("Parent node does not exists", HttpStatus.BAD_REQUEST);
        }
    }
    
    public List<Nodes> viewAll(){
        return nodesPersistence.findAll();
    }
}
