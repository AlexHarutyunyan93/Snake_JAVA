package com.codewithmosh.mic;

import com.codewithmosh.DirectionEnum;

public interface MicSchema {
    void micMoveToApple(int appleX,int appleY);
    void micMove(DirectionEnum direction);
    void checkBords();
    void changeMoveDirection();
    void micLookForApple(int appleX, int appleY);
}
