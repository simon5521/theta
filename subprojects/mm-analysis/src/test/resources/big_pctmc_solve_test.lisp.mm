(mm
    (param p1
        0.1
        10.0
    )
    (param p2
        0.1
        10.0
    )
    (param p3
        0.1
        10.0
    )
    (param p4
        0.1
        10.0
    )
    (param p5
        0.1
        10.0
    )
    (param p6
        0.1
        10.0
    )
    (var l1 Int
        0
        10
        0
    )
    (var l2 Int
        0
        10
        0
    )
    (var l3 Int
        0
        10
        0
    )
    (var l4 Int
        0
        10
        0
    )

    (command act1 (< l1 5)
        (update (+ p1 p2)
            (assign l1 (+ l1 1) )
        )
        (update (+ p3 p2)
            (assign l1 (+ l1 2) )
        )
        (update (+ p1 p4)
            (assign l1 (+ l1 3) )
        )
    )
    (command act2 (< l2 5)
        (update (+ p1 p2)
            (assign l2 (+ l2 1) )
        )
        (update (+ p3 p2)
            (assign l2 (+ l2 2) )
        )
        (update (+ p1 p4)
            (assign l2 (+ l2 3) )
        )
    )
    (command act3 (< l3 5)
        (update (+ p1 p2)
            (assign l3 (+ l3 1) )
        )
        (update (+ p3 p2)
            (assign l3 (+ l3 2) )
        )
        (update (+ p1 p4)
            (assign l3 (+ l3 3) )
        )
    )
    (command act4 (< l4 5)
        (update (+ p1 p2)
            (assign l4 (+ l4 1) )
        )
        (update (+ p3 p2)
            (assign l4 (+ l4 2) )
        )
        (update (+ p1 p4)
            (assign l4 (+ l4 3) )
        )
    )



    (command act5 (and (>= l1 5) (< l1 10))
        (update (+ p1 p2)
            (assign l1 (+ l1 1) )
        )
        (update (+ p3 p2)
            (assign l1 (+ l1 0) )
        )
    )
    (command act6 (and (>= l2 5) (< l2 10))
        (update (+ p1 p2)
            (assign l2 (+ l2 1) )
        )
        (update (+ p3 p2)
            (assign l2 (+ l2 0) )
        )
    )
    (command act7 (and (>= l3 5) (< l3 10))
        (update (+ p1 p2)
            (assign l3 (+ l3 1) )
        )
        (update (+ p3 p2)
            (assign l3 (+ l3 0) )
        )
    )
    (command act8 (and (>= l4 5) (< l4 10))
        (update (+ p1 p2)
            (assign l4 (+ l4 1) )
        )
        (update (+ p3 p2)
            (assign l4 (+ l4 0) )
        )
    )


)