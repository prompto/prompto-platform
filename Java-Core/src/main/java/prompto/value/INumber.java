package prompto.value;

public interface INumber extends IValue, Comparable<INumber>
{
    long IntegerValue();
    double DecimalValue();
}
