main {
    x <- 0;
    y <- 1;

    while(y < 100) {
        print x;
        
        temp <- x + y;
        x <- y;
        y <- temp; 
    }

    return x;
}
