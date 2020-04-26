package com.hjs.system.api;

import org.mountcloud.graphql.GraphqlClient;
import org.mountcloud.graphql.request.query.DefaultGraphqlQuery;
import org.mountcloud.graphql.request.query.GraphqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/20 20:49
 * @Modified By:
 */
public class GraphQLApiUtils {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLApiUtils.class);

    public static final String GITHUB_GRAPHQL_URL = "https://api.github.com/graphql";

    public static final GraphqlQuery getViewer= new DefaultGraphqlQuery("getUser");

    public static final String MY_ACCESS_TOKEN = "ec0556f9d99a33b2019acb9b27a5edc9c7f49430";


//    public static GraphqlQuery getGraphQLQuery(Map<String, String> header, String queryName) {
//
//        graphqlClient.setHttpHeaders(header);
//        GraphqlQuery query = new DefaultGraphqlQuery(queryName);
//        return query;
//    }
}
