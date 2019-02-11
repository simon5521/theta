(dtmdp
    (var l Int
        0
        3
        0
    )
    (dcommand act1 (= l 0)
        (dupdate 0.6
            (assign l 1)
        )
        (dupdate 0.4
            (assign l (+ l 2))
        )
    )
    (dcommand act2 (= l 1)
        (dupdate 0.2
            (assign l 3)
        )
        (dupdate 0.8
            (assign l 0)
        )
    )
    (dcommand act3 (= l 2)
        (dupdate 1
            (assign l 3)
        )
    )
)