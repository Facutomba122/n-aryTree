package com.naryTreeDemo.nary;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naryTreeDemo.nary.Entity.Nodes;
import java.util.List;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class NodeTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testCreate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/nodes")
                .param("value", "Manzana manzana"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Node create succesfully"));
        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/nodes"))
                .andReturn();
        
        result.getResponse();   
    }
    
    
}
