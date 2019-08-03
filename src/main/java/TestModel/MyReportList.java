package TestModel;

import java.util.ArrayList;
import java.util.List;

public class MyReportList extends ArrayList<MyFeature>{

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

}
