package so.debateit.infrastructure.repository.elastic;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Map;

//@Repository
@RequiredArgsConstructor
public class ElasticSearchRepository {

    private final RestHighLevelClient client;


    //index
    public IndexResponse index(String indexName, String id, Map<String, Object> source) throws Exception{
        IndexRequest request = new IndexRequest(indexName).id(id).source(source);
        return client.index(request, RequestOptions.DEFAULT);
    }


    //Search
    public SearchResponse search(String indexName, SearchSourceBuilder searchSourceBuilder) throws Exception {
        SearchRequest searchRequest = getSearchRequest(indexName, searchSourceBuilder);
        return  client.search(searchRequest, RequestOptions.DEFAULT);
    }

    //update
    public UpdateResponse update(String indexName, String id, Map<String, Object> source) throws Exception{
        UpdateRequest request = new UpdateRequest(indexName, id).doc(source);
        return client.update(request, RequestOptions.DEFAULT);
    }

    //delete
    public DeleteResponse deleteById(String indexName, String id) throws Exception {
        DeleteRequest request = new DeleteRequest(indexName, id);
        return client.delete(request, RequestOptions.DEFAULT);

    }

    private SearchRequest getSearchRequest(String indexName, SearchSourceBuilder searchSourceBuilder) {
        SearchRequest searchRequest = new SearchRequest("posts");
        searchRequest.indices(indexName).source(searchSourceBuilder);
        return searchRequest;
    }
}
