package prompto.store.datomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import prompto.intrinsic.PromptoBinary;
import prompto.intrinsic.PromptoDate;
import prompto.intrinsic.PromptoDateTime;
import prompto.intrinsic.PromptoTime;
import prompto.store.AttributeInfo;
import prompto.store.IQuery;
import prompto.store.IQueryBuilder;

public class DatomicQueryBuilder implements IQueryBuilder {

	Integer params = 0;
	List<Object> inputs = new ArrayList<>();
	Queue<IPredicate> predicates = new LinkedList<>();
	
	public DatomicQueryBuilder() {
		inputs.add(null); // placeholder for db
	}
	
	public String addInput(Object input) {
		String id = "v" + inputs.size();
		if(input instanceof PromptoDate)
			input = ((PromptoDate)input).toJavaTime();
		else if(input instanceof PromptoTime)
			input = ((PromptoTime)input).getNativeMillisOfDay();
		else if(input instanceof PromptoDateTime)
			input = new Date(((PromptoDateTime)input).toJavaTime());
		else if(input instanceof PromptoBinary)
			; // TODO PromptoBinary value = toBytes((PromptoBinary)value);
		inputs.add(input);
		return id;
	}
	
	public String addParam() {
		return "p" + (++params);
	}
	
	static interface IPredicate {

		void build(StringBuilder sb, boolean inOr);
	}
	
	static class SingleMatchPredicate implements IPredicate {
		
		public String clause;

		public SingleMatchPredicate(String clause) {
			this.clause = clause;
		}
		
		@Override
		public void build(StringBuilder sb, boolean inOr) {
			sb.append(clause);
		}

	}
	
	static class MultiMatchPredicate implements IPredicate {
		
		public List<String> clauses;

		public MultiMatchPredicate(String ... clauses) {
			this.clauses = Arrays.asList(clauses);
		}
		
		@Override
		public void build(StringBuilder sb, boolean inOr) {
			clauses.forEach(sb::append);
		}

	}
	
	static class AndPredicate implements IPredicate {

		public List<IPredicate> predicates;
		
		public AndPredicate(IPredicate ... predicates) {
			this.predicates = Arrays.asList(predicates);
		}
		
		@Override
		public void build(StringBuilder sb, boolean inOr) {
			if(inOr)
				sb.append("(and ");
			predicates.forEach(p->p.build(sb, false));
			if(inOr)
				sb.append(')');
		}
	}

	static class OrPredicate implements IPredicate {

		public List<IPredicate> predicates;
		
		public OrPredicate(IPredicate ... predicates) {
			this(Arrays.asList(predicates));
		}
		
		public OrPredicate(List<IPredicate> predicates) {
			this.predicates = predicates;
		}

		@Override
		public void build(StringBuilder sb, boolean inOr) {
			sb.append("(or ");
			predicates.forEach(p->p.build(sb, true));
			sb.append(')');
		}
	}
	
	static class OrJoinPredicate implements IPredicate {

		public List<String> inputs;
		public List<IPredicate> predicates;
		
		public OrJoinPredicate(List<String> inputs, IPredicate ... predicates) {
			this(inputs, Arrays.asList(predicates));
		}
		
		public OrJoinPredicate(List<String> inputs, List<IPredicate> predicates) {
			this.inputs = inputs;
			this.predicates = predicates;
		}

		@Override
		public void build(StringBuilder sb, boolean inOr) {
			sb.append("(or-join [");
			inputs.forEach(i->{sb.append('?'); sb.append(i); sb.append(' ');});
			sb.append("] ");
			predicates.forEach(p->p.build(sb, true));
			sb.append(')');
		}
	}

	static class NotPredicate implements IPredicate {

		public IPredicate clause;
		
		public NotPredicate(IPredicate clause) {
			this.clause = clause;
		}
		
		@Override
		public void build(StringBuilder sb, boolean inOr) {
			throw new UnsupportedOperationException(); // TODO
		}
	}

	
	@Override
	public IQueryBuilder verify(AttributeInfo info, MatchOp match, Object fieldValue) {
		IPredicate predicate;
		switch(match) {
			case CONTAINS:
				predicate = info.isCollection() ?
						new SingleMatchPredicate("[?e :" + info.getName() + " ?" + addInput(fieldValue) + "]") :
						new MultiMatchPredicate(
							"[?e :" + info.getName() + " ?" + info.getName() + "]",
							"[(.contains ^String ?" + info.getName() + " ?" + addInput(fieldValue) + ")]");
				break;
			case EQUALS:
			case IN:
				predicate = new SingleMatchPredicate("[?e :" + info.getName() + " ?" + addInput(fieldValue) + "]");
				break;
			case ROUGHLY:
				predicate = new MultiMatchPredicate(
						"[?e :" + info.getName() + " ?" + info.getName() + "]",
						"[(.equalsIgnoreCase ^String ?" + info.getName() + " ?" + addInput(fieldValue) + ")]");
				break;
			case GREATER:
				predicate = new MultiMatchPredicate(
						"[?e :" + info.getName() + " ?" + info.getName() + "]",
						"[(> ?" + info.getName() + " ?" + addInput(fieldValue) + ")]"
						);
				break;
			case LESSER:
				predicate = new MultiMatchPredicate(
						"[?e :" + info.getName() + " ?" + info.getName() + "]",
						"[(< ?" + info.getName() + " ?" + addInput(fieldValue) + ")]"
						);
				break;
			default:
				throw new UnsupportedOperationException(match.name());
		}
		predicates.offer(predicate);
		return this;
	}

	@Override
	public IQueryBuilder and() {
		IPredicate p1 = predicates.poll();
		IPredicate p2 = predicates.poll();
		AndPredicate predicate = new AndPredicate(p1, p2);
		predicates.offer(predicate);
		return this;
	}

	@Override
	public IQueryBuilder or() {
		IPredicate p1 = predicates.poll();
		IPredicate p2 = predicates.poll();
		OrPredicate predicate = new OrPredicate(p1, p2);
		predicates.offer(predicate);
		return this;
	}

	@Override
	public IQueryBuilder not() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder first(Long first) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder last(Long last) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQueryBuilder orderBy(AttributeInfo attribute, boolean descending) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public IQueryBuilder project(List<String> attributeNames) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IQuery build() {
		// TODO use Map instead of String to avoid reparsing by Datomic
		StringBuilder sb = new StringBuilder("[:find ?e :in $ ");
		for(int i=2;i<=inputs.size();i++) {
			if(inputs.get(i-1) instanceof Collection) {
				sb.append("[?v");
				sb.append(i-1);
				sb.append(" ...]");
			} else {
				sb.append("?v");
				sb.append(i-1);
				sb.append(' ');
			}
		}
		sb.append(":where ");
		IPredicate predicate = predicates.poll();
		predicate.build(sb, false);
		sb.append(']');
		String query = sb.toString(); 
		return new DatomicQuery(query, inputs);
	}

}
