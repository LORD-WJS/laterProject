package com.wjs.service;

import com.wjs.entity.Counter;

public interface CounterService {
    public void addCounter(Counter counter);
    public void remove(Counter counter);
    public void modify(Counter counter);

}
