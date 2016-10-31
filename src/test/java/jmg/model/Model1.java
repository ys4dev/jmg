package jmg.model;

import lombok.Data;

import java.util.List;

/**
 * Created by sakura on 2016/10/29.
 */
@Data
public class Model1 {
    String name1;
    String name2;
    int i;
    double d;
    List<Child> children;
}
