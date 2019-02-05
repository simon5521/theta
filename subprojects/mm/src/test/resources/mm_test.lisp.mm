(pctmc
    (param p
        1.1
        2.1
    )
    (var l Int
        0
        3
        0
    )
    (command act1 (= l 0)
        (update (+ (* 3.0 p) 2.0)
            (assign l 1)
        )
        (update (+ (* 2.0 p) 5.0)
            (assign l (+ l 2))
        )
    )
    (command act2 (= l 1)
        (update (* 2.3 p)
            (assign l 3)
        )
        (update 8.0
            (assign l 0)
        )
    )
    (command act3 (= l 2)
        (update 2.8
            (assign l 3)
        )
    )
)