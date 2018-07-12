import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static spark.Spark.get;
import static spark.Spark.port;

public class Server{
    private int n;
    private LoadingCache<Integer, ArrayList<Integer>> cache;
    public Server() {
        cache = CacheBuilder.newBuilder().expireAfterWrite(20, TimeUnit.SECONDS)
                .expireAfterAccess(10, TimeUnit.SECONDS).build(new CacheLoader<Integer, ArrayList<Integer>>() {
                public ArrayList<Integer> load(Integer n) throws Exception {
                return numbersPrime(n);
            }
        });

        port(8080);
        get("/prime", (request, resonse) -> {
            n = Integer.parseInt(request.queryParams("n"));
            return numbersPrime(n);
        });
    }

    public static ArrayList<Integer> numbersPrime(int n) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < n; i++) {
            if (isPrimeNumber(i)) {
                list.add(i);
            }
        }
        return list;

    }

    public static boolean isPrimeNumber(int n) {
        if (n < 2) {
            return false;
        }
            for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}

