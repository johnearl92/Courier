package com.gcash.courier.rule;

import com.gcash.courier.delivery.model.Parcel;
import com.gcash.courier.exception.RuleException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class RuleEngine {

    private final List<Rule> rules;

    @Autowired
    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    @PostConstruct
    public void sortRulesByPriority() {
        rules.sort(Comparator.comparingInt(Rule::getPriority));
    }

    public BigDecimal applyRules(Parcel parcel) {
        for (Rule rule : rules) {
            if (rule.match(parcel)) {
                return rule.calculateCost(parcel);
            }
        }
        throw new RuleException("No matching rule found");
    }


}
