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
    
    private Nodes PARENTNODE = null;
    
    
    //Insert new Node nary tree
    public ResponseEntity<?> insert(String value, UUID parentId){
        //Validation
        if (value == null){
            return new ResponseEntity("The value hasn't to be null", HttpStatus.BAD_REQUEST);
        }
        if (nodesPersistence.findAll().isEmpty()){
            PARENTNODE = new Nodes("parent", null, null);
            nodesPersistence.save(PARENTNODE);
        }
        
        Nodes newNode = new Nodes();
        newNode.setValue(value);

        //Without parent
        if (parentId == null){
            newNode.setFatherNodeId(PARENTNODE.getId());
            nodesPersistence.save(newNode);
            PARENTNODE.addChildrenNode(newNode.getId());
            nodesPersistence.save(PARENTNODE);
            return new ResponseEntity("Node create succesfully", HttpStatus.ACCEPTED);
        }


        //With parent
        Optional<Nodes> responseParentNode = nodesPersistence.findById(parentId);
        if (responseParentNode.isPresent()){
            newNode.setFatherNodeId(parentId);
            nodesPersistence.save(newNode);
            responseParentNode.get().addChildrenNode(newNode.getId());
            nodesPersistence.save(responseParentNode.get());
            return new ResponseEntity("Node create succesfully", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("Parent node does not exists", HttpStatus.BAD_REQUEST);
        }
    }
    
    
    //CONSULT FUNCTIONS
    public List<Nodes> viewAll(){
        return nodesPersistence.findAll();
    }
    
    public Nodes findById(UUID id){
        try {
            Optional<Nodes> responseNodes = nodesPersistence.findById(id);
            if (responseNodes.isPresent()){
                return responseNodes.get();
            } else {
                return new Nodes();
            }
        
        } catch(Exception e) {
            throw e; 
        }
    }
}
