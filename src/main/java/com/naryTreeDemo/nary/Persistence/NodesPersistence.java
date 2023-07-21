package com.naryTreeDemo.nary.Persistence;

import com.naryTreeDemo.nary.Entity.Nodes;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NodesPersistence extends JpaRepository<Nodes, UUID>{
    
    @Query("SELECT n FROM Nodes n WHERE n.value = 'parent'")
    public Optional<Nodes> findParent();
}
