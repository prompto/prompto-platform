package prompto.store.datomic;

class OrderedCategory implements Comparable<OrderedCategory> {

	int order;
	String category;
	
	public OrderedCategory(String value) {
		String[] parts = value.split(":");
		this.order = Integer.parseInt(parts[0]);
		this.category = parts[1];
	}

	@Override
	public int compareTo(OrderedCategory other) {
		return Integer.compare(this.order, other.order);
	}
	
	public String category() {
		return category;
	}
	
	
}
