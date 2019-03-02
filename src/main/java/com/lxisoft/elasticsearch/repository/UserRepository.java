 package com.lxisoft.elasticsearch.repository;

import java.util.List;

import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.lxisoft.elasticsearch.model.User;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@Repository
public class UserRepository {

    @Value("${elasticsearch.index.name.one}")
    private String indexName;

    @Value("${elasticsearch.type.name.one}")
    private String userTypeName;

    @Autowired
    private ElasticsearchTemplate esTemplate;
    
    
    //GET ALL USERS
    public List<User> getAllUsers() {
        SearchQuery getAllQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQuery())
                .build();
        
        return esTemplate.queryForList(getAllQuery, User.class);
    }
    
  
    //SEARCH USERS BASED ON TEXT PHRASE
    public List<User> searchUsers(String searchTerm) {
	    SearchQuery searchQuery = new NativeSearchQueryBuilder()
	    		  .withQuery(
	    			org.elasticsearch.index.query.QueryBuilders
	    				.multiMatchQuery(searchTerm, "name", "bio", "father_name")
	    				.fuzziness(Fuzziness.TWO))
	    		  .build();
	    
        return esTemplate.queryForList(searchQuery, User.class);
    }
}