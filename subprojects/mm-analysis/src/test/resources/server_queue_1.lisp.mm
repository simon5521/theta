(mm

    (param mu
        0.01
        0.5
    )
    (param lambda
        0.2
        0.8
    )
    (param gamma
        0.2
        0.8
    )


    (var q Int
        0
        10
        0
    )

    (var s Int
        0
        1
        0
    )

    (var err Int
        0
        1
        0
    )

    (command DataInNoServ (and (< q 10) (= s 1))
            (update mu
                (assign q (+ q 1))
            )
            (update gamma
                (assign s 0)
            )
    )

    (command DataFullNoServ (and (= q 10) (= s 1))
            (update mu
                (assign q (+ q 1))
                (assign err 1)
            )
            (update gamma
                (assign s 0)
            )
    )

    (command DataInServ (and (> q 0) (and (< q 10) (= s 0)))
            (update mu
                (assign q (+ q 1))
            )
            (update lambda
                (assign q (- q 1))
                (assign s 1)
            )
    )

    (command DataNullServ (and (= q 0) (= s 0))
            (update mu
                (assign q (+ q 1))
            )
    )

    (command DataFullServ (and (= q 10) (= s 0))
            (update mu
                (assign q (+ q 1))
                (assign err 1)
            )
            (update lambda
                (assign q (- q 1))
                (assign s 1)
            )
    )

)