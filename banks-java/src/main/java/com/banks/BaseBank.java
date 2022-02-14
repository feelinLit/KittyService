package com.banks;

import com.accounts.BaseAccount;
import com.clients.DefaultClient;

import java.util.*;

public abstract class BaseBank implements Bank {
    private final String name;
    protected final HashMap<DefaultClient, List<BaseAccount>> accounts = new HashMap<>();
    protected final BankConditions conditions;

    public BaseBank(String name, BankConditions conditions) {
        this.name = name;
        this.conditions = conditions;
    }

    public String getName() {
        return name;
    }

    public Map<DefaultClient, List<BaseAccount>> getAccounts() {
        return Collections.unmodifiableMap(accounts);
    }

    public BankConditions getConditions() {
        return conditions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseBank baseBank = (BaseBank) o;
        return name.equals(baseBank.name);
    }

    @Override
    public int hashCode() {return Objects.hash(name); }
}
