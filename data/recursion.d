main {
    return factorial(3);
}

func factorial(x) {
    if(x<=1) {
        return 1;
    } else {
        return x * factorial(x - 1);
    }
}