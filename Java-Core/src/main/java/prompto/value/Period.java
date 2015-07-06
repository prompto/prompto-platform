package prompto.value;

import prompto.error.PromptoError;
import prompto.error.SyntaxError;
import prompto.runtime.Context;
import prompto.type.PeriodType;


public class Period extends BaseValue implements IMultiplyable
{
    public static final Period ZERO = new Period(0, 0, 0, 0, 0, 0, 0, 0);

    org.joda.time.Period value;
    
    public Period(int years, int months, int weeks, int days, int hours, int minutes, int seconds, int millis)
    {
    	super(PeriodType.instance());
        value = new org.joda.time.Period(years, months, weeks, days, hours, minutes, seconds, millis);
    }

    public Period(org.joda.time.Period value)
    {
    	super(PeriodType.instance());
    	this.value = value;
    }
    
    public org.joda.time.Period getValue() {
		return value;
	}


    @Override
    public IValue Add(Context context, IValue value) throws PromptoError
    {
        if (value instanceof Period)
            return this.plus((Period)value);
        else
            throw new SyntaxError("Illegal: Period + " + value.getClass().getSimpleName());
    }

    @Override
    public IValue Subtract(Context context, IValue value) throws PromptoError
    {
        if (value instanceof Period)
            return this.minus((Period)value);
        else
            throw new SyntaxError("Illegal: Period - " + value.getClass().getSimpleName());
    }

    @Override
    public IValue Multiply(Context context, IValue value) throws PromptoError
    {
        if (value instanceof Integer)
        {
            int count = (int)((Integer)value).IntegerValue();
            if (count < 0)
                throw new SyntaxError("Negative repeat count:" + count);
            if (count == 0)
                return Period.ZERO;
            if (count == 1)
                return this;
            return this.times(count);
        }
        else
            throw new SyntaxError("Illegal: Period * " + value.getClass().getSimpleName());
    }

    @Override
    public Object ConvertTo(Class<?> type)
    {
        return value; 
   }
 
        public Period minus(Period period)
        {
            return new Period( this.value.minus(period.value));
        }


   
        public Period plus(Period period)
        {
           return new Period(this.value.plus(period.value));
         }

        public Period times(int count)
        {
            return new Period(
                  this.value.getYears() * count,
                  this.value.getMonths() * count,
                  this.value.getWeeks() * count,
                  this.value.getDays() * count,
                  this.value.getHours() * count,
                  this.value.getMinutes() * count,
                  this.value.getSeconds() * count,
                  this.value.getMillis() * count);
        }
 
        @Override
        public String toString()
        {
           return value.toString();
        }
 
        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Period)
                return this.value.equals(((Period)obj).value);
            else
                return false;
        }

        @Override
        public int hashCode()
        {
            return value.hashCode();
        }


    }
