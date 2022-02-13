package com.common;

public class Range
{
    private int start;
    private int end;

    public Range(int low, int start){
        this.start = low;
        this.end = start;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean contains(int number){
        return (number >= start && number <= end);
    }

    public boolean contains(double number){
        return (number >= start && number <= end);
    }

    public boolean intersects(Range anotherRange) { return this.start < anotherRange.end && anotherRange.start < end; }

    @Override
    public String toString() {
        return "Range{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}