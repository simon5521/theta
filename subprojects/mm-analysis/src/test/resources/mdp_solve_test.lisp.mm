(dtmdp

    (var state Int
        0
        8
        8
    )


    (dcommand act0 (= state 8)
        (dupdate 0.5
            (assign state 0)
        )
        (dupdate 0.5
            (assign state 5)
        )
    )




    (dcommand act1 (= state 0)
        (dupdate 0.1
            (assign state 1)
        )
        (dupdate 0.9
            (assign state 4)
        )
    )
    (dcommand act2 (= state 0)
        (dupdate 0.9
            (assign state 1)
        )
        (dupdate 0.1
            (assign state 4)
        )
    )

    (dcommand act3 (= state 1)
        (dupdate 0.1
            (assign state 2)
        )
        (dupdate 0.9
            (assign state 4)
        )
    )
    (dcommand act4 (= state 1)
        (dupdate 0.9
            (assign state 2)
        )
        (dupdate 0.1
            (assign state 4)
        )
    )

    (dcommand act5 (= state 2)
        (dupdate 0.1
            (assign state 3)
        )
        (dupdate 0.9
            (assign state 4)
        )
    )
    (dcommand act6 (= state 2)
        (dupdate 0.9
            (assign state 3)
        )
        (dupdate 0.1
            (assign state 4)
        )
    )







    (dcommand act7 (= state 5)
        (dupdate 0.1
            (assign state 6)
        )
        (dupdate 0.9
            (assign state 4)
        )
    )
    (dcommand act8 (= state 5)
        (dupdate 0.9
            (assign state 6)
        )
        (dupdate 0.1
            (assign state 4)
        )
    )

    (dcommand act9 (= state 6)
        (dupdate 0.1
            (assign state 7)
        )
        (dupdate 0.9
            (assign state 4)
        )
    )
    (dcommand act10 (= state 6)
        (dupdate 0.9
            (assign state 7)
        )
        (dupdate 0.1
            (assign state 4)
        )
    )

    (dcommand act11 (= state 7)
        (dupdate 0.1
            (assign state 3)
        )
        (dupdate 0.9
            (assign
             state 4)
        )
    )
    (dcommand act12 (= state 7)
        (dupdate 0.9
            (assign state 3)
        )
        (dupdate 0.1
            (assign state 4)
        )
    )
)
