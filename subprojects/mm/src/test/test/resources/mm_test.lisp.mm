(pctmc
    (var l BoundedReal
        0
        3
        0
    )
    (command act1 l==0
        (update 3*p+2 (
            (assign l 1)
        )
        (update 2*p+5 (
            (assign l l+2)
        )
    )
    (command act2 l==1
        (update 2.3*p (
            (assign l 3)
        )
        (update 8 (
            (assign l 0)
        )
    )
    (command act3 l==2
        (update 2.8 (
            (assign l 3)
        )
    )
)