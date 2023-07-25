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
    
    //UPDATE FUNCTIONS
    
    public ResponseEntity<?> update(Nodes updatedNode, UUID id){
        if (id == null || updatedNode == null){
            return new ResponseEntity("El Id y/o el nodo a actualizar no deben ser nulos", HttpStatus.BAD_REQUEST);
        }
        
        Optional<Nodes> oldNode = nodesPersistence.findById(id);
        if (oldNode.isPresent()){
            if (updatedNode.getValue() != null){
                oldNode.get().setValue(updatedNode.getValue());
            }
            if (updatedNode.getFatherNodeId() != null){
                oldNode.get().setFatherNodeId(updatedNode.getFatherNodeId());
            }
            if (updatedNode.getChildrenNodes() != null){
                oldNode.get().setChildrenNodes(updatedNode.getChildrenNodes());
            }
            nodesPersistence.save(oldNode.get());
            return new ResponseEntity("node with Id = " + oldNode.get().getId() + " update succesfully", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity("The node doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }
    
    //CREATE FUNCIONTS
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
    
    //DELETE FUNCTIONS
    public ResponseEntity<?> deleteNodes(UUID id){
        try {
            Optional<Nodes> responseNodes = nodesPersistence.findById(id);
            if (responseNodes.isPresent() && id != PARENTNODE.getId()){
                List<UUID> auxList = responseNodes.get().getChildrenNodes();
                if (!auxList.isEmpty()){
                    for (UUID auxNode : auxList){
                        deleteNodes(auxNode);
                    }
                }
                nodesPersistence.deleteById(id);
                return new ResponseEntity("Node and all children's has been deleted successfully", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity("The node doesn't exist or is root node", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            throw e;
        }
    }
    
    public ResponseEntity<?> deletePartialNodes(UUID id){
        try {
            Optional<Nodes> responseNodes = nodesPersistence.findById(id);
            if (responseNodes.isPresent() && id != PARENTNODE.getId()){
                List<UUID> auxList = responseNodes.get().getChildrenNodes();
                Nodes newReplacementNode = nodesPersistence.findById(auxList.get(0)).get();
                Nodes parentNode = nodesPersistence.findById(responseNodes.get().getFatherNodeId()).get();
                
                auxList.remove(newReplacementNode.getId());
                for (UUID auxId : auxList){
                    newReplacementNode.addChildrenNode(auxId);
                }
                newReplacementNode.setFatherNodeId(parentNode.getId());
                parentNode.getChildrenNodes().remove(id);
                parentNode.addChildrenNode(newReplacementNode.getId());
                nodesPersistence.delete(responseNodes.get());
                nodesPersistence.save(newReplacementNode);
                nodesPersistence.save(parentNode);
                return new ResponseEntity("The partial delete has been succesfully", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity("The node doesn't exist or is root node", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            throw e;
        }
    }
}
