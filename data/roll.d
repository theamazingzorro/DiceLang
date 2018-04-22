size <- 12;

main {
    var <- d(size);
    count <- 0;
    while(var != 1) {
        foo(var);
        var <- d(size);
        count <- count + 1;
    }
    return count;
}

func foo(x) {
    print x + 1;
}