package com.naryTreeDemo.nary.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Nodes {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    
    private String value;
    private UUID fatherNodeId;
    private String childrenNodes;

    public Nodes(String value, UUID fatherNodeId, String childrenNodes) {
        this.value = value;
        this.fatherNodeId = fatherNodeId;
        this.childrenNodes = childrenNodes;
    }

    public Nodes() {
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UUID getFatherNodeId() {
        return fatherNodeId;
    }

    public void setFatherNodeId(UUID fatherNodeId) {
        this.fatherNodeId = fatherNodeId;
    }

    public ArrayList<UUID> getChildrenNodes() {
        try {
            if (this.childrenNodes == null){
                return new ArrayList<UUID>();
            }
            String auxString = "";
            ArrayList<UUID> auxList = new ArrayList<>();
            for (int i=0; i<this.childrenNodes.length(); i++){
                if (childrenNodes.charAt(i) == ';'){
                    auxList.add(UUID.fromString(auxString));
                    auxString = "";
                } else {
                    auxString = auxString + childrenNodes.charAt(i);
                }
            }
            return auxList;
        } catch (Exception e){
            throw e;
        }
    }

    public void setChildrenNodes(List<UUID> childrenNodes) {
        try {
            String auxString = "";
            for (UUID childrenNode : childrenNodes) {
                auxString = auxString + childrenNode + ";";
            }
            this.childrenNodes = auxString;
        } catch (Exception e){
            throw e;
        }
    }
    
    public void addChildrenNode(UUID childrenNodeId){
        try {
            List<UUID> auxList;
            if (this.childrenNodes == null){
                auxList = new ArrayList<>();
            } else {
                auxList = this.getChildrenNodes();
            }
            auxList.add(childrenNodeId);
            this.setChildrenNodes(auxList);
        } catch (Exception e){
            throw e;
        }
    }

    public UUID getId() {
        return id;
    }
    
}
