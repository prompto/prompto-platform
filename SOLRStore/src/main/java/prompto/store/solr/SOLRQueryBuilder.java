package prompto.store.solr;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;

import prompto.store.AttributeInfo;
import prompto.store.IQueryBuilder;

public class SOLRQueryBuilder implements IQueryBuilder {

	SolrQuery query;
	SOLRFilterBuilder filter;
	
	public SOLRQueryBuilder() {
		query = new SolrQuery();
		query.setRows(Integer.MAX_VALUE);
	}
	
	@Override
	public SOLRQuery build() {
		if(filter!=null) {
			query.setQuery(filter.toSolrQuery());
			filter = null;
		} else if(query.getQuery()==null)
			query.setQuery("*:*");
		return new SOLRQuery(query);
	}

	@Override
	public <T> SOLRQueryBuilder verify(AttributeInfo info, MatchOp match, T fieldValue) {
		if(filter==null)
			filter = new SOLRFilterBuilder();
		SOLRAttributeInfo solrInfo = new SOLRAttributeInfo(info);
		filter.push(solrInfo, match, fieldValue);
		return this;
	}

	
	@Override
	public SOLRQueryBuilder and() {
		filter.and();
		return this;
	}

	@Override
	public SOLRQueryBuilder or() {
		filter.or();
		return this;
	}

	@Override
	public SOLRQueryBuilder not() {
		filter.not();
		return this;
	}

	@Override
	public SOLRQueryBuilder first(Long first) {
		if(first!=null)
			query.setStart(first.intValue() - 1);
		return this;
	}

	@Override
	public SOLRQueryBuilder last(Long end) {
		if(end!=null) {
			Integer start = query.getStart();
			if(start==null)
				start = 1;
			else
				start += 1; // was 0 based
			query.setRows((int)((end - start)+1));
		}
		return this;
	}

	public void setRows(int rows) {
		query.setRows(rows);
	}
	
	
	
	@Override
	public IQueryBuilder project(List<String> attributeNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SOLRQueryBuilder orderBy(AttributeInfo attribute, boolean descending) {
		SOLRAttributeInfo solrAttribute = new SOLRAttributeInfo(attribute);
		query.addSort(solrAttribute.getFieldNameForOrderBy(), descending ? ORDER.desc : ORDER.asc);
		return this;
	}


}
