package com.codewithmosh.mic;

import com.codewithmosh.DirectionEnum;
import com.codewithmosh.food.Food;
import java.util.List;


public interface MicSchema {
    void micMoveToFood(int x, int y);
    void micLookForFood(List<Food> foods);
    void micMove(DirectionEnum direction);
    void checkBords();
    void changeMoveDirection();
}
