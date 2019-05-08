(mm
    (var q_num Int
        1
        100
        5
    )
    (var q_act Int
        0
        100
        0
    )

    (param lambda_free
        0.01
        100
    )

    (param lambda_alloc
        0.01
        100
    )

    (param alpha
        0.01
        100
    )

    (param beta
        0.01
        100
    )



    (command dec_q_num (and (> q_num q_act) (> q_num 1) (< q_num 100))
        (update lambda_free
            (assign q_num (- g_num 1))
        )
    )

    (command inc_q_num (and (> q_num 1) (< q_num 100))
        (update lambda_alloc
            (assign q_num (+ g_num 1))
        )
    )

    (command dec_q_num_full (and (> q_num q_act) (> q_num 1) (= q_num 100))
        (update lambda_free
            (assign q_num (- g_num 1))
        )
    )

    (command nop_q_num_full (and (> q_num 1) (= q_num 100))
        (update 1
            (assign q_num (+ g_num 0))
        )
    )

)