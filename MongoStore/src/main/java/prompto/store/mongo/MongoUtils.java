package prompto.store.mongo;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.bson.Document;

import prompto.utils.ResourceUtils;

public abstract class MongoUtils {

	public static Document readResource(String resource) throws IOException {
		return Document.parse(ResourceUtils.getResourceAsString(resource));
	}

	public static Collector<Map.Entry<String, Object>, Document, Document> toDocument() {
	
		return new Collector<Map.Entry<String, Object>, Document, Document>() {

			@Override
			public Supplier<Document> supplier() {
				return () -> new Document();
			}

			@Override
			public BiConsumer<Document, Map.Entry<String, Object>> accumulator() {
				return (doc, e) -> doc.put(e.getKey(), e.getValue());
			}

			@Override
			public BinaryOperator<Document> combiner() {
				return (left, right) -> {
					left.putAll(right);
					return left;
				};
			}

			@Override
			public Function<Document, Document> finisher() {
				return i -> i;
			}

			@Override
			public Set<Characteristics> characteristics() {
				return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
			}

		};
	}

}
