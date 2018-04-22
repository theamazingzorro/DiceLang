size <- 20;

main {
    count <- 0;
    sum <- 0;
    while(count < 100) {
        x <- max(d(size), d(size));
        y <- d(size);
        
        sum <- sum + x - y;
        count <- count + 1;
    }
    
    return sum / 100;
}

func max(x, y) {
    if(x > y) {
        return x;
    }
    return y;
}